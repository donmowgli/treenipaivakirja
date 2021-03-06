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
  // K??ytt??liittym????n suoraa liittyv?? koodi loppuu t??h??n
    
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
           
           merkintaLista.setOnMouseClicked(e ->{ if (e.getClickCount() == 1) naytaTreenit(merkintaLista.getSelectedObject().getTrid(), false); });
           merkintaLista.setOnMouseClicked(e -> { if (e.getClickCount() == 3) muokkaaMerkintaa();});
           
           treeniLista.setOnMouseClicked(e ->{ if (e.getClickCount() == 1) naytaHarjoitteet(treeniLista.getSelectedObject().getTrid(), false); });
           treeniLista.setOnMouseClicked(e -> { if (e.getClickCount() == 3) muokkaaTreenia();});
           
           harjoiteLista.setOnMouseClicked(e ->{ if (e.getClickCount() == 1) naytaSarjat(harjoiteLista.getSelectedObject().getHarid(), false); });
           harjoiteLista.setOnMouseClicked(e -> { if (e.getClickCount() == 3) muokkaaHarjoitetta();});
           
           sarjaLista.setOnMouseClicked(e ->{ if (e.getClickCount() == 2) kloonaaValittu(sarjaLista.getObject(sarjaLista.getRowNr())); naytaSarjat(sarjaLista.getObject(sarjaLista.getRowNr()).getHarid(), false); });
           sarjaLista.setOnMouseClicked(e -> { if (e.getClickCount() == 3) muokkaaSarjaa();});
           
           hakuehto.add("Merkint??", new Treeni());
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
     * Muokataan merkint??j?? merkinn??n lis????misen controllerissa
     */
    protected void muokkaaMerkintaa() {
        muokataanko = true;
        muokattava = merkintaLista.getSelectedObject();
        LisaaMerkintaGUIController.avaa(null);
        muokataanko = false;
        naytaMerkinnat();
    }
    
    /**
     * Muokataan harjoitetta harjoitteen lis????misen k??ytt??liittym??st??
     */
    protected void muokkaaTreenia() {
        muokataanko = true;
        muokattava = treeniLista.getSelectedObject();
        LisaaTreeniGUIController.avaa(null);
        muokataanko = false;
        naytaTreenit(0, false);
    }
    
    /**
     * Muokataan harjoitetta harjoitteen lis????misen k??ytt??liittym??st??
     */
    protected void muokkaaHarjoitetta() {
        muokataanko = true;
        muokattava = harjoiteLista.getSelectedObject();
        LisaaHarjoiteGUIController.avaa(null, 0);
        muokataanko = false;
        naytaHarjoitteet(0, false);
    }
    
    /**
     * Muokataan sarjaa sarjan lis????misen k??ytt??liittym??st??
     */
    protected void muokkaaSarjaa() {
        muokataanko = true;
        muokattava = sarjaLista.getObject(sarjaLista.getRowNr());
        LisaaSarjaGUIController.avaa(null, 0);
        muokataanko = false;
        naytaSarjat(0, false);
    }
    
    /**
     * Merkint??jen n??ytt??minen k??ytt??liittym??ss??
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
     * Treenien n??ytt??minen k??ytt??liittym??ss??
     * @param id Treenin id joka tuodaan valitulta pvm-oliolta.
     * @param naytaKaikki boolean-arvo, n??ytet????nk?? kaikki treenit listassa
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
     * Harjoitteiden n??ytt??minen k??ytt??liittym??ss??
     * @param id Treenin id, jonka treenit halutaan n??ytt????
     * @param naytaKaikki boolean-arvo, n??ytet????nk?? kaikki harjoitteet listassa
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
     * Sarjojen n??ytt??minen k??ytt??liittym??ss??
     * @param id Harjoitteen id, jonka sarjat halutaan n??ytt????
     * @param naytaKaikki boolean-arvo, n??ytet????nk?? kaikki olemassaolevat sarjat
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaSarjat(int id, boolean naytaKaikki) throws NullPointerException{
        sarjaLista.clear();
        String[] headings = {"Ty??paino", "Toistot", "Toteutuneet"};
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
        Sarja klooni = sarja.clone();
        klooni.rekisteroi();
        paivakirja.lisaa(klooni);
    }
    
 /**
 * Haetaan hakuehdon mukaisesti oliot tietoineen n??yt??lle 
 */
@SuppressWarnings("unchecked")
    protected void hae() {
        boolean pvm = false;
        if(hakuehto.getSelectedText().equals("Merkint??")) {pvm = true;}
        ArrayList<Muokattava> tulokset = (ArrayList<Muokattava>) paivakirja.haku(hakuehto.getSelectedObject(), hakulauseke.getText(), pvm);
        if(tulokset.isEmpty() || hakulauseke.getText() == null) {naytaMerkinnat(); return;}
        else if(tulokset.get(0).getClass() == Harjoite.class) {harjoiteLista.clear(); for(Muokattava harjoite : tulokset) {harjoiteLista.add(((Harjoite) harjoite).getNimi(), (Harjoite) harjoite);}}
        else if(tulokset.get(0).getClass() == Treeni.class && pvm == false)  {treeniLista.clear(); for(Muokattava treeni : tulokset) {treeniLista.add(((Treeni) treeni).getNimi(), (Treeni) treeni);}}
        else if(tulokset.get(0).getClass() == Treeni.class && pvm == true)  {merkintaLista.clear(); for(Muokattava treeni : tulokset) {merkintaLista.add(((Treeni) treeni).pvmToString(), (Treeni) treeni);}}
    }

    /**
     * Ohjelmaa avatessa avaa tiedostosta ladatut tiedot k??ytt??liittym????n oletuslokaatiosta. Valmius useammillekin tiedostoille.
     * @throws SailoException jos tiedostosta lukemisessa ongelmia
     */
    private void avaa() throws SailoException{
        try {
            paivakirja.lueTiedostosta();
        } catch (Exception e) {
            Dialogs.showMessageDialog("Tiedostoa ei viel?? tallennettu - ei voida viel?? lukea." + e.getMessage());
            e.printStackTrace();
        }
        naytaMerkinnat();
    }
    
    /**
     * Kaiken tiedon tallentaminen erillisin?? tiedostoina.
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
     * @param kohde kohde, mist?? poistetaan
     */
    @SuppressWarnings("all")
    public void poista(Muokattava poistettava, Class<?> kohde) {
        muokattava = poistettava;
        Muutettava <Muokattava>muutettava = new Muutettava<Muokattava>(poistettava, kohde);
        PoistoGUIController.avaa(null, muutettava);
    }
    
    /**
     * N??ytet????n ohjelman suunnitelma erillisess?? selaimessa.
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