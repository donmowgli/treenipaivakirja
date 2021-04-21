package fxTreenipvk;

import java.io.IOException;
import java.net.URL;

import Treenipvk.Paivakirja;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class PoistoGUIController {
    
    private Paivakirja paivakirja;
    private Muutettava <Muokattava> muutettava;
    private static Stage stage = new Stage();
    
    @FXML private Text tiedot = new Text();
    
    @SuppressWarnings("all")
    public void alusta(Paivakirja paivakirja, Muutettava<Muokattava> mk) {
        this.paivakirja = paivakirja;
        muutettava = mk;
        tiedot.setText(muutettava.getMuokattava().getTiedot());
    }
    
    @FXML
    private void handleEi() {
        stage.close();
    }
    
    @FXML
    private void handleKylla() throws RuntimeException {
        paivakirja.poista(muutettava.getKohde(), muutettava.getMuokattava());
        stage.close();
    }

    /**
     * Poistetaan haluttu olio päiväkirjasta
     * @param modalityStage modaalisuus, mikä halutaan asettaa
     * @param paivakirja Päiväkirja-olio, mistä poistetaan
     * @param mk muutettava tietorakenne, josta halutaan poistaa ja mitä poistetaan
     * @return palauttaa muokatun Päiväkirja-olion
     */
    @SuppressWarnings("all")
    public static Paivakirja avaa(Stage modalityStage, Paivakirja paivakirja, Muutettava<Muokattava> mk) {
        try {
            URL url = PoistoGUIController.class.getResource("PoistoView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            PoistoGUIController ctrl = (PoistoGUIController)loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Poisto");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            
            ctrl.alusta(paivakirja, mk);
            stage.showAndWait();
            stage = new Stage();
            return paivakirja;
        } catch (IOException e) {
            e.printStackTrace();
            return paivakirja;
        }
    }
}
