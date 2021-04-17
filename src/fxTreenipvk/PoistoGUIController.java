package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kanta.*;
/**
 * @author Akseli Jaara
 * @version 8 Apr 2021
 *
 */
public class PoistoGUIController implements Initializable {
    
    private static Stage stage = new Stage();
    private static Muutettava <Muokattava> muutettava;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tiedot.setText(TreenipvkGUIController.muokattava.getTiedot());
    }
    
    @FXML private Text tiedot = new Text();
    
    @FXML
    private void handleEi() {
        stage.close();
    }
    
    @FXML
    private void handleKylla() {
        TreenipvkGUIController.paivakirja.poista(muutettava.getKohde(), muutettava.getMuokattava());
        stage.close();
    }


    @SuppressWarnings("all")
    public static void avaa(Stage modalityStage, Muutettava<Muokattava> mk) {
        try {
            URL url = LisaaMerkintaGUIController.class.getResource("PoistoView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Poisto");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            
            muutettava = mk;
            
            stage.showAndWait();
            stage = new Stage();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
