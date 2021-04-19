package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Treenipvk.SailoException;
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
import kanta.Tarkistus;

/**
 * Controller-luokka merkinnän lisäämiselle
 * @author Akseli Jaara
 * @version 22 Mar 2021
 *
 */
public class LisaaMerkintaGUIController implements Initializable{
    private Treeni treeni = new Treeni();
    private Tarkistus tarkistus = new Tarkistus();
    private static Stage stage = new Stage();
    
    @FXML private TextField pvm;
    @FXML private ListChooser<Treeni> treenit;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        treenit.clear();
        nayta();
        if(TreenipvkGUIController.muokataanko == true) {asetaValittu();}
    }
    
    /**
     * Handle OK-painikkeelle
     */
    @FXML
    private void handleOK() {
        try {
            String tarkistettu = tarkistus.tarkistaPvm(pvm.getText());
            if (tarkistettu != null) { Dialogs.showMessageDialog(tarkistettu); return; }
            if(TreenipvkGUIController.muokataanko == true) {muokkaa(); stage.close(); return;}
            String[] arvot = pvm.getText().split("\\.");
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
     * Asetetaan muokkausta varten valinta chooserille
     */
    private void asetaValittu() {
        for(int i = 0; i < treenit.getObjects().size(); i++) {
            if(treenit.getObjects().get(i).getId() == TreenipvkGUIController.muokattava.getId()) {
                treenit.setSelectedIndex(i);
                this.pvm.setText(treenit.getObjects().get(i).pvmToString());
                return;
            }
        }
    }
    
    private void muokkaa() throws SailoException {
        String[] arvot = pvm.getText().split("\\.");
        LocalDate annettu = LocalDate.of(Integer.parseInt(arvot[2]), Integer.parseInt(arvot[1]), Integer.parseInt(arvot[0]));
        Treeni uusi = TreenipvkGUIController.paivakirja.getTreenit().getTreeni(TreenipvkGUIController.muokattava.getId());
        uusi.setPvm(annettu);
        TreenipvkGUIController.paivakirja.poista(TreenipvkGUIController.paivakirja.getTreenit().getTreeni(TreenipvkGUIController.muokattava.getId()));
        TreenipvkGUIController.paivakirja.lisaa(uusi);
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
