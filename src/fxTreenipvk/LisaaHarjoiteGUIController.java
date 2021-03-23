package fxTreenipvk;

import java.io.IOException;
import java.net.URL;

import Treenipvk.Harjoite;
import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller-luokka harjoitteen lisäämiselle
 * TODO bugin korjaaminen, jossa stage.hide() ei toimi.
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaHarjoiteGUIController {
    private Harjoite harjoite;
    private static Stage stage;
    
    @FXML private TextField nimi;
    @FXML private TextField lkm;
    
    /**
     * Controllerin muodostaja
     */
    public LisaaHarjoiteGUIController() {
        this.harjoite = new Harjoite();
        LisaaHarjoiteGUIController.stage = new Stage();
    }
    
    /**
     * Handle-funktio OK-napin painallukselle
     */
    @FXML
    private void handleOK() {
        try {
            harjoite.setNimi(nimi.getText());
            harjoite.setSarlkm(Integer.parseInt(lkm.getText()));
            stage.hide();
        }catch (NumberFormatException e) {
            Dialogs.showMessageDialog("Sarjojen lukumäärä tulee olla numeroina!");
        }
    }
    
    /**
     * Palautetaan lisätty Harjoite-olio
     * @return Controllerin Harjoite-olio
     */
    private Harjoite getResult() {
        return harjoite;
    }
    
    /**
     * Avataan Harjoite-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @return palauttaa lisätyn Harjoite-olion.
     */
    public Harjoite lisaa(Stage modalityStage) {
        try {
            URL url = LisaaHarjoiteGUIController.class.getResource("LisaaHarjoiteView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            final LisaaHarjoiteGUIController dialogCtrl = (LisaaHarjoiteGUIController)loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Harjoite");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            
            stage.showAndWait();
            stage = new Stage();
            return dialogCtrl.getResult();
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
