package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Akseli Jaara
 * @version 8 Apr 2021
 *
 */
public class PoistoGUIController implements Initializable {
    private static Object  poistettava;
    private static Stage stage;
    
    @FXML private Text tiedot;
    
    //@SuppressWarnings("unchecked")
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //tiedot.setText(poistettava.getTiedot());
    }
    
    @FXML
    private void handleEi() {
        stage.close();
    }
    
    private void handleKylla() {
        try {
            //poistettava.poista();
        } catch (Exception e) { //NoSuchMethodException
            Dialogs.showMessageDialog("Ongelma poistamisessa!");
            stage.close();
        }
    }

    /**
     * Avataan Sarja-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param haluttu olio, joka halutaan poistaa
     */
    public static void avaa(Stage modalityStage, Object haluttu) {
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
            poistettava = haluttu;
            stage.showAndWait();
            stage = new Stage();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
