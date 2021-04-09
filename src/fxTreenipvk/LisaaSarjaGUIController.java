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
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaSarjaGUIController {
    private static int id = 0;
    private Sarja sarja = new Sarja();
    private static Stage stage = new Stage();
    
    @FXML private TextField tyopaino;
    @FXML private TextField toistot;
    @FXML private TextField toteutuneet;
    
    /**
     * Handle-funktio OK-napin painallukselle
     * TODO Milloin rekisteröidään ja milloin asetetaan harjoitteen id?
     */
    @FXML
    private void handleOK() {
        try {
            sarja.setTyopaino(Integer.parseInt(tyopaino.getText()));
            sarja.setToistot(Integer.parseInt(toistot.getText()));
            sarja.setToteutuneet(Integer.parseInt(toteutuneet.getText()));
            sarja.setHarid(id);
            sarja.rekisteroi();
            stage.close();
            TreenipvkGUIController.paivakirja.getSarjat().lisaaSarja(sarja);
        }catch (NumberFormatException e) {
            Dialogs.showMessageDialog("Tiedot tulee olla numeroina!");
        }
    }
    
    /**
     * Avataan Sarja-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param harid harjoituksen id, joka sarjalle halutaan asettaa.
     */
    public static void avaa(Stage modalityStage, int harid) {
        try {
            URL url = LisaaSarjaGUIController.class.getResource("LisaaSarjaView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Sarja");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            id = harid;
            stage.showAndWait();
            stage = new Stage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
