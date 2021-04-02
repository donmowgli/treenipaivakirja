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
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaHarjoiteGUIController {
    private static int id = 0;
    private Harjoite harjoite = new Harjoite();
    private static Stage stage = new Stage();
    
    @FXML private TextField nimi;
    @FXML private TextField lkm;
    
    /**
     * Handle-funktio OK-napin painallukselle
     */
    @FXML
    private void handleOK() {
        try {
            harjoite.setNimi(nimi.getText());
            harjoite.setSarlkm(Integer.parseInt(lkm.getText()));
            harjoite.setTrid(id);
            harjoite.rekisteroi();
            TreenipvkGUIController.paivakirja.getHarjoitteet().lisaaHarjoite(harjoite);
            stage.hide();
        }catch (NumberFormatException e) {
            Dialogs.showMessageDialog("Sarjojen lukumäärä tulee olla numeroina!");
        }
    }
    
    /**
     * Avataan Harjoite-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param trid Treenin id, jolle sarja halutaan asettaa
     */
    public static void avaa(Stage modalityStage, int trid) {
        try {
            URL url = LisaaHarjoiteGUIController.class.getResource("LisaaHarjoiteView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Harjoite");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            id = trid;
            stage.showAndWait();
            stage = new Stage();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
