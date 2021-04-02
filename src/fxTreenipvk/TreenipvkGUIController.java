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
import fi.jyu.mit.fxgui.StringGrid;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Treenipvk.Sarja;
import Treenipvk.Harjoite;
import Treenipvk.Treeni;
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
    
    @FXML private StringGrid<Sarja> sarjaLista;
    @FXML private ListChooser<Harjoite> harjoiteLista;
    @FXML private ListChooser<Treeni> treeniLista;
    @FXML private ListChooser<Treeni> merkintaLista;

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
    
    /**
     * TODO: poistamisen lisääminen
     */
    @FXML
    private void handlePoistaMerkinta() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("PoistoView.fxml"), "Merkinta", null, "");
    }
    
    /**
     * TODO: poistamisen lisääminen
     */
    @FXML
    private void handlePoistaTreeni() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("PoistoView.fxml"), "Treeni", null, "");
    }
    
    /**
     * TODO: poistamisen lisääminen
     */
    @FXML
    private void handlePoistaHarjoite() {
        ModalController.showModal(TreenipvkGUIController.class.getResource("PoistoView.fxml"), "Harjoite", null, "");
    }
    
    /**
     * TODO: poistamisen lisääminen
     */
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
    
    /**
     * Protected Päiväkirja-olio, jota käsitellään kaikissa controllereissa
     */
    protected static Paivakirja paivakirja = new Paivakirja();
    
    /**
     * Alustetaan harrastelistan kuuntelija
     * TODO Gridperkeleen chooseri?
     * TODO hiiren kaksoisklikkauksen avaamiset
     * TODO korjaa valitun kloonaaminen
     */
    protected void alusta() {
        try{
            paivakirja.alustaTiedostoNimet();
            
           merkintaLista.clear();
           treeniLista.clear();
           harjoiteLista.clear();
           sarjaLista.clear();
            
           merkintaLista.addSelectionListener(e -> naytaTreenit(0));
           treeniLista.addSelectionListener(e -> naytaHarjoitteet(0));
           harjoiteLista.addSelectionListener(e -> naytaSarjat(0));
           
           merkintaLista.setOnMouseClicked(e ->{ if (e.getClickCount() > 1) naytaTreenit(merkintaLista.getSelectedObject().getTrid()); });
           treeniLista.setOnMouseClicked(e ->{ if (e.getClickCount() > 1) naytaHarjoitteet(treeniLista.getSelectedObject().getTrid()); });
           harjoiteLista.setOnMouseClicked(e ->{ if (e.getClickCount() > 1) naytaSarjat(harjoiteLista.getSelectedObject().getHarid()); });
           sarjaLista.setOnMouseClicked(e ->{ if (e.getClickCount() > 1) kloonaaValittu(sarjaLista.getObject(sarjaLista.getRowNr())); naytaSarjat(sarjaLista.getObject(sarjaLista.getRowNr()).getHarid()); });
           
           alustaValinnat();
           
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Alustetaan valinnat choosereille
     * TODO Gridperkeleen chooserin alustus
     * TODO toimiiko tällaisenaan ja voiko yleistää?
     */
    protected void alustaValinnat() {
        merkintaLista.setSelectedIndex(0);
        treeniLista.setSelectedIndex(0);
        harjoiteLista.setSelectedIndex(0);
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan
     * TODO palauttaa oletusolion jos suljetaan, korjaa
     * TODO valinnan alustus
     */
    protected void uusiMerkinta() {
        try {
            LisaaMerkintaGUIController.avaa(null);
            naytaMerkinnat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan 
     * TODO palauttaa oletusolion jos suljetaan, korjaa
     * TODO valinnan alustus
     */
    protected void uusiTreeni() {
        LisaaTreeniGUIController.avaa(null);
        try {
            treeniLista.setSelectedIndex(paivakirja.getTreenit().getTreeniLkm());
            naytaTreenit(merkintaLista.getSelectedObject().getTrid());
        } catch(Exception e) {
            naytaTreenit(0);
        }
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan
     * TODO palauttaa oletusolion jos suljetaan, korjaa
     * TODO valinnan alustus
     */
    protected void uusiHarjoite() {
        try {
            LisaaHarjoiteGUIController.avaa(null, treeniLista.getSelectedObject().getTrid());
            harjoiteLista.setSelectedIndex(paivakirja.getHarjoitteet().getHarjoitteetLkm());
            naytaHarjoitteet(treeniLista.getSelectedObject().getTrid());
        } catch (Exception e) {
            LisaaHarjoiteGUIController.avaa(null, 0);
            naytaHarjoitteet(0);
        }
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan.
     * TODO palauttaa oletusolion jos suljetaan, korjaa
     * valinnan alustus
     */
    protected void uusiSarja() {
        try {
            LisaaSarjaGUIController.avaa(null, harjoiteLista.getSelectedObject().getHarid());
            naytaSarjat(harjoiteLista.getSelectedObject().getHarid());
        } catch (Exception e) {
            LisaaSarjaGUIController.avaa(null, 0);
            naytaSarjat(0);
        }
    }
    
    /**
     * Merkintöjen näyttäminen käyttöliittymässä
     * TODO booleanilla näytetäänkö kaikki
     */
    protected void naytaMerkinnat() {
        merkintaLista.clear();
        for(Treeni trn : paivakirja.getTreenit().getTreenit()) {
            if (trn.getPvm() != null) {
                merkintaLista.add(trn.getPvm().toString(), trn);
            }
        }
        
        try {
            naytaTreenit(merkintaLista.getSelectedObject().getTrid());
        } catch (Exception e) {
            naytaTreenit(0);
        }
    }
    
    /**
     * Treenien näyttäminen käyttöliittymässä
     * TODO booleanilla näytetäänkö kaikki
     * @param id Treenin id joka tuodaan valitulta pvm-oliolta.
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaTreenit(int id) throws NullPointerException{
        treeniLista.clear();
        for(Treeni trn : paivakirja.getTreenit().getTreenit()) {
            if (id == trn.getTrid()) {
                treeniLista.add(trn.getNimi(), trn);
            } else if (trn.getTrid() == 0) {
                treeniLista.add(trn.getNimi(), trn);
            }
        }
        
        try {
            naytaHarjoitteet(treeniLista.getSelectedObject().getTrid());
        } catch (Exception e) {
            naytaHarjoitteet(0);
        }
    }
    
    /**
     * Harjoitteiden näyttäminen käyttöliittymässä
     * TODO booleanilla näytetäänkö kaikki
     * @param id Treenin id, jonka treenit halutaan näyttää
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaHarjoitteet(int id) throws NullPointerException{
        harjoiteLista.clear();
        for(Harjoite har : paivakirja.getHarjoitteet()) {
            if (id == har.getTrid()) {
                harjoiteLista.add(har);
            } else if (har.getHarid() == 0) {
                harjoiteLista.add(har.getNimi(), har);
            }
        }
        try {
            naytaSarjat(harjoiteLista.getSelectedObject().getHarid());
        } catch (Exception e) {
            naytaSarjat(0);
        }
    }
    
    /**
     * Sarjojen näyttäminen käyttöliittymässä
     * TODO booleanilla näytetäänkö kaikki
     * @param id Harjoitteen id, jonka sarjat halutaan näyttää
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaSarjat(int id) throws NullPointerException{
        sarjaLista.clear();
        String[] headings = {"Työpaino", "Toistot", "Toteutuneet"};
        sarjaLista.initTable(headings);
        for(Sarja sarja : paivakirja.getSarjat()) {
            if (id == sarja.getHarid()) {
                String tp = String.valueOf(sarja.getTyopaino());
                String toistot = String.valueOf(sarja.getToistot());
                String toteutuneet = String.valueOf(sarja.getToteutuneet());
                sarjaLista.add(sarja, tp, toistot, toteutuneet);
            } else {
                String tp = String.valueOf(sarja.getTyopaino());
                String toistot = String.valueOf(sarja.getToistot());
                String toteutuneet = String.valueOf(sarja.getToteutuneet());
                sarjaLista.add(sarja, tp, toistot, toteutuneet);
            }
        }
    }
    
    /**
     * @param sarja Sarja-olio, josta halutaan asettaa klooni listaan
     */
    protected void kloonaaValittu(Sarja sarja) {
        paivakirja.lisaa(sarja.clone());
    }

    /**
     * Ohjelmaa avatessa avaa tiedostosta ladatut tiedot käyttöliittymään oletuslokaatiosta. Valmius useammillekin tiedostoille.
     * TODO tiedostojen näyttäminen lukemisen jälkeen, lukemisen alustaminen - pitää olla olemassaolevat tiedostot, josta voidaan lukea! Lisäksi tiedostonimet voidaan alustaa erillisessä metodissa. Sama pätee tallenna-funktiolle. HUOM alustus tällä hetkellä päiväkirjan luetiedostosta-funktiossa, josta tulee siirtää pois yleistetympään muotoon.
     * @throws SailoException jos tiedostosta lukemisessa ongelmia
     */
    private void avaa() throws SailoException{
        try {
            paivakirja.lueTiedostosta();
        } catch (Exception e) {
            Dialogs.showMessageDialog("Tiedostoa ei vielä tallennettu - ei voida vielä lukea." + e.getMessage());
            e.printStackTrace();
        }
        naytaMerkinnat();
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