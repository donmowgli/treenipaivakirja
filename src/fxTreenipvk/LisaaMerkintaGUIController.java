package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import Treenipvk.Paivakirja;
import Treenipvk.SailoException;
import Treenipvk.Treeni;
import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller-luokka merkinnän lisäämiselle
 * @author Akseli Jaara
 * @version 22 Mar 2021
 *
 */
public class LisaaMerkintaGUIController {
    private Paivakirja paivakirja;
    private Treeni treeni;
    private static Stage stage;
    
    private TextField pvm;
    private ListView<Treeni> treenit;
    
    /**
     * Controllerin muodostaja
     */
    public LisaaMerkintaGUIController() {
        this.paivakirja = new Paivakirja();
        this.treeni = new Treeni();
        this.treeni.setPvm(LocalDate.now());
        LisaaMerkintaGUIController.stage =new Stage();
    }
    
    /**
     * Handle OK-painikkeelle
     * TODO Chooser jolla valitaan haluttu treeni
     */
    @FXML
    private void handleOK() throws SailoException {
      //TÄHÄN chooseri jolla tallennetaan haluttu treeni
        try {
            String[] arvot = pvm.getText().split("//.");
            LocalDate annettu = LocalDate.of(Integer.parseInt(arvot[0]), Integer.parseInt(arvot[1]), Integer.parseInt(arvot[2]));
            this.treeni.setPvm(annettu);
        } catch(Exception e) {
            Dialogs.showMessageDialog("Anna päivämäärä halutussa muodossa!" + e.getMessage());
        }
        paivakirja.getTreenit().lisaaTreeni(this.treeni);
        stage.hide();
    }
    
    /**
     * Naytetään treenien oletusmuodot (joilla ei merkintää, eli ei omaa päivämäärää)
     */
    private void nayta() {
        try {
            for(int i = 0; i < paivakirja.getTreenit().getTreenit().length; i++) {
                if(paivakirja.getTreenit().getTreenit()[i].getPvm() == null) {
                    treenit.getItems().add(paivakirja.getTreenit().getTreenit()[i]);
                }
            }
            treenit.refresh();
        } catch (NullPointerException e) {
            Dialogs.showMessageDialog("Lisää ensin treenejä, jotta voit lisätä merkinnän!");
            stage.close();
        }
    }
    
    /**
     * Palautetaan lisätty Treeni-olio
     * @return Controllerin Treeni-olio
     */
    private Paivakirja getResult() {
        return this.paivakirja;
    }
    
    /**
     * Avataan Sarja-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param pvk Päiväkirja-olio, jota halutaan muokata
     * @return palauttaa lisätyn sarja-olion.
     */
    public Paivakirja avaa(Stage modalityStage, Paivakirja pvk) {
        try {
            URL url = LisaaMerkintaGUIController.class.getResource("LisaaMerkintaView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            final LisaaMerkintaGUIController dialogCtrl = (LisaaMerkintaGUIController)loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Merkintä");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            paivakirja = pvk;
            nayta();
            stage.showAndWait();
            stage = new Stage();
            return dialogCtrl.getResult();
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
