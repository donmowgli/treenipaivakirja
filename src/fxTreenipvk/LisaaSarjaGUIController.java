package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import Treenipvk.Sarja;
import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller-luokka sarjan lisäämiselle
 * TODO bugin korjaaminen, jossa stage.hide() ei toimi.
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaSarjaGUIController {
    private Sarja sarja;
    private Stage stage;
    
    @FXML private TextField tyopaino;
    @FXML private TextField toistot;
    @FXML private TextField toteutuneet;
    
    /**
     * Controllerin muodostaja
     */
    public LisaaSarjaGUIController() {
        this.sarja = new Sarja();
        this.stage = new Stage();
    }
    
    /**
     * Handle-funktio OK-napin painallukselle
     */
    @FXML
    private void handleOK() {
        try {
            sarja.setTyopaino(Integer.parseInt(tyopaino.getText()));
            sarja.setToistot(Integer.parseInt(toistot.getText()));
            sarja.setToteutuneet(Integer.parseInt(toteutuneet.getText()));
            stage.close();
        }catch (NumberFormatException e) {
            Dialogs.showMessageDialog("Tiedot tulee olla numeroina!");
        }
    }
    
    /**
     * Palautetaan lisätty Sarja-olio
     * @return Controllerin Sarja-olio
     */
    private Sarja getResult() {
        return sarja;
    }
    
    /**
     * Avataan Sarja-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @return palauttaa lisätyn sarja-olion.
     */
    public Sarja avaa(Stage modalityStage) {
        try {
            URL url = LisaaSarjaGUIController.class.getResource("LisaaSarjaView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            final LisaaSarjaGUIController dialogCtrl = (LisaaSarjaGUIController)loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Sarja");
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
