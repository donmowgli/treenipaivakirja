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
    
    private Paivakirja paivakirja = new Paivakirja();
    private String harjoitteenKohdalla;
    
    
    /**
     * Alustetaan harrastelistan kuuntelija
     */
    protected void alusta() {
        try{
            chooserHarjoitteet.clear();
            chooserHarjoitteet.addSelectionListener(e -> naytaSarjat());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Näytetään kaikki sarjat listassa valitun harjoituksen mukaan. Väliaikainen
     */
    protected void naytaSarjat() {
        harjoitteenKohdalla = chooserHarjoitteet.getSelectedObject();
        sarjaLista.getItems().clear();
        
        if (harjoitteenKohdalla == null) return;
        ObservableList<String> data = FXCollections.observableArrayList();
        for(Sarja sarja : paivakirja.getSarjat()) {
            if(sarja.getHarid() == paivakirja.getHarjoitteet().getHarjoite(harjoitteenKohdalla).getHarid()) {
                data.add(sarja.toString());
            }
        }
        sarjaLista.setItems(data);
    }
    
    /**
     * Näytetään kaikki harjoitteet listassa. Väliaikainen
     */
    protected void naytaHarjoitteet() {
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            for(Harjoite harjoite : paivakirja.getHarjoitteet()) {
                data.add(harjoite.getNimi());
                if(data.size() == paivakirja.getSarjoja()) {
                    break;
                }
            }
            harjoiteLista.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Hakee harjoitteen tiedot listaan
     * @param id harjoitteen numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int id) {
        chooserHarjoitteet.clear();

        int index = 0;
        for (int i = 0; i < paivakirja.getHarjoitteita(); i++) {
            Harjoite harjoite = paivakirja.getHarjoite(i);
            if (harjoite.getHarid() == id) index = i;
            chooserHarjoitteet.add(harjoite.getNimi());
        }
        chooserHarjoitteet.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
    }

    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan 
     */
    protected void uusiHarjoite() {
        try {
            Harjoite harjoite = new Harjoite();
            harjoite.setNimi("Penkki");
            harjoite.rekisteroi();
            
            Random rand = new Random();
            
            Sarja sarja1 = new Sarja(rand.nextInt(80), rand.nextInt(10));
            Sarja sarja2 = new Sarja(rand.nextInt(80), rand.nextInt(10));
            Sarja sarja3 = new Sarja(rand.nextInt(80), rand.nextInt(10));
            
            sarja1.rekisteroi();
            sarja2.rekisteroi();
            sarja3.rekisteroi();
            
            sarja1.setHarid(harjoite.getHarid());
            sarja2.setHarid(harjoite.getHarid());
            sarja3.setHarid(harjoite.getHarid());
            
            paivakirja.getSarjat().lisaaSarja(sarja1);
            paivakirja.getSarjat().lisaaSarja(sarja2);
            paivakirja.getSarjat().lisaaSarja(sarja3);
            
            paivakirja.lisaa(harjoite);
            
            naytaHarjoitteet();
            
            hae(harjoite.getHarid());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
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