package fxTreenipvk;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import kanta.Muokattava;
import kanta.Muutettava;
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
    
    @FXML private TextField hakulauseke;
    @FXML private ComboBoxChooser<Muokattava> hakuehto;
    
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
        hae();
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
        poista(merkintaLista.getSelectedObject(), paivakirja.getTreenit().getClass());
        naytaMerkinnat();
    }
    
    @FXML
    private void handlePoistaTreeni() {
        poista(treeniLista.getSelectedObject(), paivakirja.getTreenit().getClass());
        naytaTreenit(0, false);
    }
    
    @FXML
    private void handlePoistaHarjoite() {
        poista(harjoiteLista.getSelectedObject(), paivakirja.getHarjoitteet().getClass());
        naytaHarjoitteet(0, false);
    }
    
    @FXML
    private void handlePoistaSarja() {
        poista(sarjaLista.getObject(0), paivakirja.getSarjat().getClass());
        naytaSarjat(0, false);
    }
    
    @FXML
    private void handleApua() {
        avustus();
    }
    
  //=========================================================================================== 
  // Käyttöliittymään suoraa liittyvä koodi loppuu tähän
    
    @SuppressWarnings("javadoc")
    protected static Paivakirja paivakirja = new Paivakirja();
    @SuppressWarnings("javadoc")
    protected static Muokattava muokattava;
    @SuppressWarnings("javadoc")
    protected static boolean muokataanko;
    
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
           
           merkintaLista.addSelectionListener(e -> naytaTreenit(merkintaLista.getSelectedObject().getId(), false));
           merkintaLista.setOnMouseClicked(e -> { if (e.getClickCount() == 2) muokkaaMerkintaa();});
           
           treeniLista.addSelectionListener(e -> naytaHarjoitteet(treeniLista.getSelectedObject().getId(), false));
           treeniLista.setOnMouseClicked(e -> { if (e.getClickCount() == 2) muokkaaTreenia();});
           
           harjoiteLista.addSelectionListener(e -> naytaSarjat(harjoiteLista.getSelectedObject().getId(), false));
           harjoiteLista.setOnMouseClicked(e -> { if (e.getClickCount() == 2) muokkaaHarjoitetta();});
           
           sarjaLista.setOnMouseClicked(e ->{ if (e.getClickCount() == 3) kloonaaValittu(sarjaLista.getObject(sarjaLista.getRowNr())); naytaSarjat(sarjaLista.getObject(sarjaLista.getRowNr()).getHarid(), false); });
           sarjaLista.setOnMouseClicked(e -> { if (e.getClickCount() == 2) muokkaaSarjaa();});
           
           hakuehto.add("Merkintä", new Treeni());
           hakuehto.add("Treeni", new Treeni());
           hakuehto.add("Harjoite", new Harjoite());
           
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
        if (paivakirja.getTreenit().getTreenit().isEmpty()) {Dialogs.showMessageDialog("Lisää ensin treenejä, joille voit tehdä merkintöjä!"); return;}
        LisaaMerkintaGUIController.avaa(null);
        naytaMerkinnat();
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan 
     * TODO palauttaa oletusolion jos suljetaan, korjaa
     */
    protected void uusiTreeni() {
        if (paivakirja.getHarjoitteet().getHarjoitteet().isEmpty()) {Dialogs.showMessageDialog("Lisää ensin harjoitteita merkittäväksi treeniin!"); return;}
        LisaaTreeniGUIController.avaa(null);
        treeniLista.setSelectedIndex(paivakirja.getTreenit().getTreeniLkm());
        naytaTreenit(muokattava.getViite(), true);
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan
     * TODO palauttaa oletusolion jos suljetaan, korjaa
     */
    protected void uusiHarjoite() {
        try {
            LisaaHarjoiteGUIController.avaa(null);
            harjoiteLista.setSelectedIndex(paivakirja.getHarjoitteet().getHarjoitteetLkm());
            naytaHarjoitteet(muokattava.getViite(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan.
     * TODO palauttaa oletusolion jos suljetaan, korjaa
     */
    protected void uusiSarja() {
        if (harjoiteLista.getSelectedObject() == null) {Dialogs.showMessageDialog("Valitse tai lisää ensin harjoite, johon lisätä sarja!"); return;}
        LisaaSarjaGUIController.avaa(null, harjoiteLista.getSelectedObject().getHarid());
        naytaSarjat(muokattava.getViite(), true);
    }
    
    /**
     * Muokataan merkintöjä merkinnän lisäämisen controllerissa
     */
    protected void muokkaaMerkintaa() {
        muokataanko = true;
        muokattava = merkintaLista.getSelectedObject();
        LisaaMerkintaGUIController.avaa(null);
        muokataanko = false;
        naytaMerkinnat();
    }
    
    /**
     * Muokataan harjoitetta harjoitteen lisäämisen käyttöliittymästä
     */
    protected void muokkaaTreenia() {
        muokataanko = true;
        muokattava = treeniLista.getSelectedObject();
        LisaaTreeniGUIController.avaa(null);
        muokataanko = false;
        naytaTreenit(muokattava.getViite(), false);
    }
    
    /**
     * Muokataan harjoitetta harjoitteen lisäämisen käyttöliittymästä
     */
    protected void muokkaaHarjoitetta() {
        muokataanko = true;
        muokattava = harjoiteLista.getSelectedObject();
        LisaaHarjoiteGUIController.avaa(null);
        muokataanko = false;
        naytaHarjoitteet(muokattava.getViite(), false);
    }
    
    /**
     * Muokataan sarjaa sarjan lisäämisen käyttöliittymästä
     */
    protected void muokkaaSarjaa() {
        muokataanko = true;
        muokattava = sarjaLista.getObject(sarjaLista.getRowNr());
        LisaaSarjaGUIController.avaa(null, 0);
        muokataanko = false;
        naytaSarjat(muokattava.getViite(), false);
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
        muokattava = merkintaLista.getObjects().get(0);
        naytaTreenit(muokattava.getId(), true);
    }
    
    /**
     * Treenien näyttäminen käyttöliittymässä
     * @param id Treenin id joka tuodaan valitulta pvm-oliolta.
     * @param naytaKanta boolean-arvo, näytetäänkö kantatreenit listassa
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaTreenit(int id, boolean naytaKanta) throws NullPointerException{
        treeniLista.clear();
        for(Treeni trn : paivakirja.getTreenit().getTreenit()) {
            if (trn.getId() == id && trn.getKanta() ==false && naytaKanta == false) { treeniLista.add(trn.getNimi(), trn); continue;}
            else if (trn.getKanta() == true && naytaKanta == true){ treeniLista.add(trn.getNimi(), trn);}
        }
        muokattava = treeniLista.getObjects().get(0);
        naytaHarjoitteet(muokattava.getId(), true);
    }
    
    /**
     * Harjoitteiden näyttäminen käyttöliittymässä
     * @param id Treenin id, jonka treenit halutaan näyttää
     * @param naytaKanta totuusarvo, halutaanko nähdä myös kantaoliot
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaHarjoitteet(int id, boolean naytaKanta) throws NullPointerException{
        harjoiteLista.clear();
        for(Harjoite har : paivakirja.getHarjoitteet()) {
            if (har.getTrid() == id && har.getKanta() == false && naytaKanta == false) { harjoiteLista.add(har.getNimi(), har); continue;}
            else if (har.getKanta() == true && naytaKanta == true) harjoiteLista.add(har.getNimi(), har);
        }
        muokattava = harjoiteLista.getObjects().get(0);
        naytaSarjat(muokattava.getId(), true);
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
            if (sarja.getHarid() == id && naytaKaikki == false) {
                String tp = String.valueOf(sarja.getTyopaino());
                String toistot = String.valueOf(sarja.getToistot());
                String toteutuneet = String.valueOf(sarja.getToteutuneet());
                sarjaLista.add(sarja, tp, toistot, toteutuneet);
                continue;
            } else if (naytaKaikki == true){
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
        Sarja klooni = sarja.clone();
        klooni.rekisteroi();
        paivakirja.lisaa(klooni);
    }
    
 /**
 * Haetaan hakuehdon mukaisesti oliot tietoineen näytölle 
 */
@SuppressWarnings("unchecked")
    protected void hae() {
        boolean pvm = false;
        if(hakuehto.getSelectedText().equals("Merkintä")) {pvm = true;}
        ArrayList<Muokattava> tulokset = (ArrayList<Muokattava>) paivakirja.haku(hakuehto.getSelectedObject(), hakulauseke.getText(), pvm);
        if(tulokset.isEmpty() || hakulauseke.getText() == null) {naytaMerkinnat(); return;}
        else if(tulokset.get(0).getClass() == Harjoite.class) {harjoiteLista.clear(); for(Muokattava harjoite : tulokset) {harjoiteLista.add(((Harjoite) harjoite).getNimi(), (Harjoite) harjoite);}}
        else if(tulokset.get(0).getClass() == Treeni.class && pvm == false)  {treeniLista.clear(); for(Muokattava treeni : tulokset) {treeniLista.add(((Treeni) treeni).getNimi(), (Treeni) treeni);}}
        else if(tulokset.get(0).getClass() == Treeni.class && pvm == true)  {merkintaLista.clear(); for(Muokattava treeni : tulokset) {merkintaLista.add(((Treeni) treeni).pvmToString(), (Treeni) treeni);}}
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
     * @param poistettava poistettava olio
     * @param kohde kohde, mistä poistetaan
     */
    @SuppressWarnings("all")
    public void poista(Muokattava poistettava, Class<?> kohde) {
        muokattava = poistettava;
        Muutettava <Muokattava>muutettava = new Muutettava<Muokattava>(poistettava, kohde);
        PoistoGUIController.avaa(null, muutettava);
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