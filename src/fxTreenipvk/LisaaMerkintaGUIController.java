package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Treenipvk.Treeni;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller-luokka merkinnän lisäämiselle
 * @author Akseli Jaara
 * @version 22 Mar 2021
 *
 */
public class LisaaMerkintaGUIController implements Initializable{
    private Treeni treeni = new Treeni();
    private static Stage stage = new Stage();
    
    @FXML private TextField pvm;
    @FXML private ListChooser<Treeni> treenit;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        treenit.clear();
        nayta();
    }
    
    /**
     * Handle OK-painikkeelle
     */
    @FXML
    private void handleOK() {
        try {
            String[] arvot = pvm.getText().split("\\.");
            System.out.println(arvot[0]);
            System.out.println(arvot[1]);
            System.out.println(arvot[2]);
            LocalDate annettu = LocalDate.of(Integer.parseInt(arvot[2]), Integer.parseInt(arvot[1]), Integer.parseInt(arvot[0]));
            this.treeni = treenit.getSelectedObject().clone();
            this.treeni.setPvm(annettu);
            this.treeni.rekisteroi();
        } catch(Exception e) {
            Dialogs.showMessageDialog("Tarkista päivämäärän muoto ja oikeellisuus!");
        }
        TreenipvkGUIController.paivakirja.getTreenit().lisaaTreeni(this.treeni);
        stage.hide();
    }
    
    /**
     * Naytetään treenien oletusmuodot (joilla ei merkintää, eli ei omaa päivämäärää)
     */
    private void nayta() {
        try {
            for(Treeni trn : TreenipvkGUIController.paivakirja.getTreenit().getTreenit()) {
                if(trn.getPvm() == null) {
                    treenit.add(trn.getNimi(), trn);
                }
            }
        } catch (NullPointerException e) {
            Dialogs.showMessageDialog("Lisää ensin treenejä, jotta voit lisätä merkinnän!");
        }
    }
    
    /**
     * Avataan Sarja-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     */
    public static void avaa(Stage modalityStage) {
        try {
            URL url = LisaaMerkintaGUIController.class.getResource("LisaaMerkintaView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Merkintä");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            stage.showAndWait();
            stage = new Stage();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
