package fxTreenipvk;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private void handleAvaa() throws SailoException {
        avaa();
    }
    
    @FXML
    private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    @FXML
    private void handleLisaaMerkinta() {
        uusiMerkinta();
    }
    
    @FXML
    private void handleLisaaTreeni() {
        uusiTreeni();
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
     * TODO tiedostonimien alustaminen tähän lataamista ja tallentamista varten. Alustettu nyt Paivakirja-luokassa, sieltä poistoon ja tähän! (Tai kutsulla toinen metodi siellä, jossa alustetaan tiedostonimet jokaiselle tiedostolle).
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
     * TODO Näyttäminen aina lisäämisen jälkeen
     * TODO rekisteröinnin lisääminen tilanteissa jolloin se tarvitaan
     */
    protected void uusiMerkinta() {
        try {
            LisaaMerkintaGUIController cntrl = new LisaaMerkintaGUIController();
            paivakirja = cntrl.avaa(null, paivakirja);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan 
     * TODO Näyttäminen aina lisäämisen jälkeen
     * TODO rekisteröinnin lisääminen tilanteissa jolloin se tarvitaan
     */
    protected void uusiTreeni() {
        LisaaTreeniGUIController cntrl = new LisaaTreeniGUIController();
        paivakirja = cntrl.avaa(null, paivakirja);
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan
     * TODO Näyttäminen aina lisäämisen jälkeen
     * TODO rekisteröinnin lisääminen tilanteissa jolloin se tarvitaan
     */
    protected void uusiHarjoite() {
        LisaaHarjoiteGUIController cntrl = new LisaaHarjoiteGUIController();
        paivakirja.getHarjoitteet().lisaaHarjoite(cntrl.lisaa(null));
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan.
     * TODO Näyttäminen aina lisäämisen jälkeen
     * TODO rekisteröinnin lisääminen tilanteissa jolloin se tarvitaan
     */
    protected void uusiSarja() {
        LisaaSarjaGUIController cntrl = new LisaaSarjaGUIController();
        paivakirja.getSarjat().lisaaSarja(cntrl.avaa(null));
    }

    /**
     * Ohjelmaa avatessa avaa tiedostosta ladatut tiedot käyttöliittymään oletuslokaatiosta. Valmius useammillekin tiedostoille.
     * TODO tiedostojen näyttäminen lukemisen jälkeen, lukemisen alustaminen - pitää olla olemassaolevat tiedostot, josta voidaan lukea! Lisäksi tiedostonimet voidaan alustaa erillisessä metodissa. Sama pätee tallenna-funktiolle. HUOM alustus tällä hetkellä päiväkirjan luetiedostosta-funktiossa, josta tulee siirtää pois yleistetympään muotoon.
     * @throws SailoException jos tiedostosta lukemisessa ongelmia
     */
    public void avaa() throws SailoException{
        try {
            paivakirja.lueTiedostosta();
        } catch (Exception e) {
            Dialogs.showMessageDialog("Tiedostoa ei vielä tallennettu - ei voida vielä lukea." + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Kaiken tiedon tallentaminen erillisinä tiedostoina.
     * TODO HUOM alustus tällä hetkellä päiväkirjan luetiedostosta-funktiossa, josta tulee siirtää pois yleistetympään muotoon.
     */
    private void tallenna() {
        try {
            paivakirja.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia!" + e.getMessage());
            e.printStackTrace();
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