package fxTreenipvk;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * @author Akseli Jaara
 * @version 2.2.2021
 *
 */
public class TreenipvkGUIController implements Initializable  {
    
    //@FXML private TextField hakuehto;
    //kentät tähän
    //@FXML private Label labelVirhe;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }
    
    @FXML
    private void handleHakuehto() {
        Dialogs.showMessageDialog("Eipä toimi hakeminen vielä");
    }
    
    @FXML
    private void handleTallenna() {
        tallenna();
    }
    
    @FXML
    private void handleAvaa() {
        avaa();
    }
    
    @FXML
    private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    @FXML
    private void handleLisaaMerkinta() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("LisaaMerkintaView.fxml"), "Merkintä", null, "");
    }
    
    @FXML
    private void handleLisaaTreeni() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("LisaaTreeniView.fxml"), "Treeni", null, "");
    }
    
    @FXML
    private void handleLisaaHarjoite() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("LisaaHarjoiteView.fxml"), "Harjoite", null, "");
    }
    
    @FXML
    private void handleLisaaSarja() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("LisaaSarjaView.fxml"), "Sarja", null, "");
    }
    
    @FXML
    private void handlePoistaMerkinta() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("PoistoView.fxml"), "Merkinta", null, "");
    }
    
    @FXML
    private void handlePoistaTreeni() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("PoistoView.fxml"), "Treeni", null, "");
    }
    
    @FXML
    private void handlePoistaHarjoite() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("PoistoView.fxml"), "Harjoiite", null, "");
    }
    
    @FXML
    private void handlePoistaSarja() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("PoistoView.fxml"), "Sarja", null, "");
    }
    
    @FXML
    private void handleApua() {
        avustus();
    }
    
  //=========================================================================================== 
  // Käyttöliittymään suoraa liittyvä koodi loppuu tähän
    
    private void avaa() {
        Dialogs.showMessageDialog("Avaaminen ei toimi vielä");
    }
    
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennus, ei toimi vielä");
    }
    
    /**
     * Näytetään ohjelman suunnitelma erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
            try {
                URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2021k/ht/akjojaar");
                desktop.browse(uri);
            } catch (URISyntaxException e) {
                return;
            } catch (IOException e) {
                return;
            }
    
        }
}