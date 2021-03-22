package fxTreenipvk;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Treenipvk.Sarja;
import Treenipvk.Sarjat;
import Treenipvk.Harjoite;
import Treenipvk.Harjoitteet;
import Treenipvk.Treeni;
import Treenipvk.Treenit;
import Treenipvk.Paivakirja;
import Treenipvk.SailoException;

/**
 * @author Akseli Jaara
 * @version 2.2.2021
 *
 */
public class TreenipvkGUIController implements Initializable  {
    
    @FXML private TextField hakuehto;
    @FXML private Label labelVirhe;
    @FXML private ListView<String> sarjaLista;
    @FXML private ListView<String> harjoiteLista;
    @FXML private ListChooser<String> chooserHarjoitteet = new ListChooser<String>();

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
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
        uusiHarjoite();
    }
    
    @FXML
    private void handleLisaaSarja() {
        uusiSarja();
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
        ModalController.showModal(TreenipvkGUIController.class.getResource("PoistoView.fxml"), "Harjoite", null, "");
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
    
    private Paivakirja paivakirja = new Paivakirja();
    
    /**
     * Alustetaan harrastelistan kuuntelija
     */
    protected void alusta() {
        try{
            chooserHarjoitteet.clear();
            //chooserHarjoitteet.addSelectionListener(e -> naytaSarjat());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan 
     */
    protected void uusiMerkinta() {
        //Tähän modaali ja muut tarpeelliset
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan 
     */
    protected void uusiTreeni() {
        //Tähän modaali ja muut tarpeelliset
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan 
     */
    protected void uusiHarjoite() {
        paivakirja.getHarjoitteet().lisaaHarjoite(LisaaHarjoiteGUIController.lisaa(null));
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan 
     */
    protected void uusiSarja() {
        paivakirja.getSarjat().lisaaSarja(LisaaSarjaGUIController.lisaa(null));
    }

    /**
     * Ohjelmaa avatessa avaa tiedostosta ladatut tiedot käyttöliittymään
     * TODO: Onko tarpeellinen ollenkaan jos ei ominaisuutta eri treeniohjelmille?
     */
    public void avaa(){
        //onko tarpeellinen?
    }
    
    private void tallenna() {
        try {
            paivakirja.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia!" + e.getMessage());
        }
        
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