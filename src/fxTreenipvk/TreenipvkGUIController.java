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
     */
    protected void alusta() {
        try{
            paivakirja.alustaTiedostoNimet();
            
           merkintaLista.clear();
           treeniLista.clear();
           harjoiteLista.clear();
           sarjaLista.clear();
           
           merkintaLista.setOnMouseClicked(e ->{ if (e.getClickCount() > 1) naytaTreenit(merkintaLista.getSelectedObject().getTrid(), false); });
           treeniLista.setOnMouseClicked(e ->{ if (e.getClickCount() > 1) naytaHarjoitteet(treeniLista.getSelectedObject().getTrid(), false); });
           harjoiteLista.setOnMouseClicked(e ->{ if (e.getClickCount() > 1) naytaSarjat(harjoiteLista.getSelectedObject().getHarid(), false); });
           sarjaLista.setOnMouseClicked(e ->{ if (e.getClickCount() > 1) kloonaaValittu(sarjaLista.getObject(sarjaLista.getRowNr())); naytaSarjat(sarjaLista.getObject(sarjaLista.getRowNr()).getHarid(), false); });
           
           alustaValinnat();
           
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Alustetaan valinnat choosereille
     */
    protected void alustaValinnat() {
        merkintaLista.setSelectedIndex(0);
        treeniLista.setSelectedIndex(0);
        harjoiteLista.setSelectedIndex(0);
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan
     * TODO palauttaa oletusolion jos suljetaan, korjaa
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
     */
    protected void uusiTreeni() {
        LisaaTreeniGUIController.avaa(null);
        try {
            treeniLista.setSelectedIndex(paivakirja.getTreenit().getTreeniLkm());
            naytaTreenit(merkintaLista.getSelectedObject().getTrid(), true);
        } catch(Exception e) {
            naytaTreenit(0, true);
        }
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan
     * TODO palauttaa oletusolion jos suljetaan, korjaa
     */
    protected void uusiHarjoite() {
        try {
            LisaaHarjoiteGUIController.avaa(null, treeniLista.getSelectedObject().getTrid());
            harjoiteLista.setSelectedIndex(paivakirja.getHarjoitteet().getHarjoitteetLkm());
            naytaHarjoitteet(treeniLista.getSelectedObject().getTrid(), true);
        } catch (Exception e) {
            LisaaHarjoiteGUIController.avaa(null, 0);
            naytaHarjoitteet(0, true);
        }
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan.
     * TODO palauttaa oletusolion jos suljetaan, korjaa
     */
    protected void uusiSarja() {
        try {
            LisaaSarjaGUIController.avaa(null, harjoiteLista.getSelectedObject().getHarid());
            naytaSarjat(harjoiteLista.getSelectedObject().getHarid(), true);
        } catch (Exception e) {
            LisaaSarjaGUIController.avaa(null, 0);
            naytaSarjat(0, true);
        }
    }
    
    /**
     * Merkintöjen näyttäminen käyttöliittymässä
     */
    protected void naytaMerkinnat() {
        merkintaLista.clear();
        for(Treeni trn : paivakirja.getTreenit().getTreenit()) {
            if (trn.getPvm() != null) {
                merkintaLista.add(trn.pvmToString(), trn);
            }
        }
        
        try {
            naytaTreenit(merkintaLista.getSelectedObject().getTrid(), false);
        } catch (Exception e) {
            naytaTreenit(0, true);
        }
    }
    
    /**
     * Treenien näyttäminen käyttöliittymässä
     * @param id Treenin id joka tuodaan valitulta pvm-oliolta.
     * @param naytaKaikki boolean-arvo, näytetäänkö kaikki treenit listassa
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaTreenit(int id, boolean naytaKaikki) throws NullPointerException{
        treeniLista.clear();
        for(Treeni trn : paivakirja.getTreenit().getTreenit()) {
            if (id == trn.getTrid() && naytaKaikki == false) {
                treeniLista.add(trn.getNimi(), trn);
            } else {
                treeniLista.add(trn.getNimi(), trn);
            }
        }
        
        try {
            naytaHarjoitteet(treeniLista.getSelectedObject().getTrid(), false);
        } catch (Exception e) {
            naytaHarjoitteet(0, true);
        }
    }
    
    /**
     * Harjoitteiden näyttäminen käyttöliittymässä
     * @param id Treenin id, jonka treenit halutaan näyttää
     * @param naytaKaikki boolean-arvo, näytetäänkö kaikki harjoitteet listassa
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaHarjoitteet(int id, boolean naytaKaikki) throws NullPointerException{
        harjoiteLista.clear();
        for(Harjoite har : paivakirja.getHarjoitteet()) {
            if (id == har.getTrid() && naytaKaikki == false) {
                harjoiteLista.add(har.getNimi(), har);
            } else {
                harjoiteLista.add(har.getNimi(), har);
            }
        }
        try {
            naytaSarjat(harjoiteLista.getSelectedObject().getHarid(), false);
        } catch (Exception e) {
            naytaSarjat(0, true);
        }
    }
    
    /**
     * Sarjojen näyttäminen käyttöliittymässä
     * @param id Harjoitteen id, jonka sarjat halutaan näyttää
     * @param naytaKaikki boolean-arvo, näytetäänkö kaikki olemassaolevat sarjat
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaSarjat(int id, boolean naytaKaikki) throws NullPointerException{
        sarjaLista.clear();
        String[] headings = {"Työpaino", "Toistot", "Toteutuneet"};
        sarjaLista.initTable(headings);
        for(Sarja sarja : paivakirja.getSarjat()) {
            if (id == sarja.getHarid() && naytaKaikki == false) {
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
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2021k/ht/akjojaar"));
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
    
        }
}