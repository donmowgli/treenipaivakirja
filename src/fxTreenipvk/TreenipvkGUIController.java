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
    private void handleTallenna() throws SailoException {
        tallenna();
    }
    
    @FXML
    private void handleAvaa() throws SailoException {
        avaa();
    }
    
    @FXML
    private void handleLopeta() throws SailoException {
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
        poista(merkintaLista.getSelectedObject(), Treenit.class);
    }
    
    @FXML
    private void handlePoistaTreeni() {
        poista(treeniLista.getSelectedObject(), Treenit.class);
    }
    
    @FXML
    private void handlePoistaHarjoite() {
        poista(harjoiteLista.getSelectedObject(), Harjoitteet.class);
    }
    
    @FXML
    private void handlePoistaSarja() {
        poista(sarjaLista.getObject(sarjaLista.getRowNr()), Sarjat.class);
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
     */
    protected void uusiMerkinta() {
        if (paivakirja.getTreenit().isEmpty()) {Dialogs.showMessageDialog("Lisää ensin treenejä, joille voit tehdä merkintöjä!"); return;}
        paivakirja = LisaaMerkintaGUIController.avaa(null, paivakirja, null);
        naytaMerkinnat();
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan
     */
    protected void uusiTreeni() {
        if (paivakirja.getHarjoitteet().isEmpty()) {Dialogs.showMessageDialog("Lisää ensin harjoitteita merkittäväksi treeniin!"); return;}
        paivakirja = LisaaTreeniGUIController.avaa(null, paivakirja, null);
        if(paivakirja.getTreenit().isEmpty()) return;
        treeniLista.setSelectedIndex(0);
        Muokattava muokattava = paivakirja.getTreenit().get(paivakirja.getTreeneja() -1);
        naytaTreenit(muokattava.getViite(), true);
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan
     */
    protected void uusiHarjoite() {
        paivakirja = LisaaHarjoiteGUIController.avaa(null, paivakirja, null);
        if(paivakirja.getHarjoitteet().isEmpty()) return;
        harjoiteLista.setSelectedIndex(0);
        Muokattava muokattava = paivakirja.getHarjoitteet().get(paivakirja.getHarjoitteita() - 1);
        naytaHarjoitteet(muokattava.getViite(), true);
    }
    
    /**
     * Luo uuden harjoitteen jota aletaan editoimaan.
     */
    protected void uusiSarja() {
        if (harjoiteLista.getSelectedObject() == null) {Dialogs.showMessageDialog("Valitse tai lisää ensin harjoite, johon lisätä sarja!"); return;}
        paivakirja = LisaaSarjaGUIController.avaa(null, paivakirja, null,  harjoiteLista.getSelectedObject().getHarid());
        if(paivakirja.getSarjat().isEmpty()) return;
        Muokattava muokattava = paivakirja.getSarjat().get(paivakirja.getSarjoja() - 1);
        naytaSarjat(muokattava.getViite(), true);
    }
    
    /**
     * Muokataan merkintöjä merkinnän lisäämisen controllerissa
     */
    protected void muokkaaMerkintaa() {
        if (merkintaLista.getSelectedObject() == null) {return; }
        Muokattava muokattava = merkintaLista.getSelectedObject();
        paivakirja = LisaaMerkintaGUIController.avaa(null, paivakirja, muokattava);
        naytaMerkinnat();
    }
    
    /**
     * Muokataan harjoitetta harjoitteen lisäämisen käyttöliittymästä
     */
    protected void muokkaaTreenia() {
        if (treeniLista.getSelectedObject() == null) {return; }
        Muokattava muokattava = treeniLista.getSelectedObject();
        paivakirja = LisaaTreeniGUIController.avaa(null, paivakirja, muokattava);
        naytaTreenit(muokattava.getViite(), false);
    }
    
    /**
     * Muokataan harjoitetta harjoitteen lisäämisen käyttöliittymästä
     */
    protected void muokkaaHarjoitetta() {
        if (harjoiteLista.getSelectedObject() == null) {return;}
        Muokattava muokattava = harjoiteLista.getSelectedObject();
        paivakirja = LisaaHarjoiteGUIController.avaa(null, paivakirja, muokattava);
        naytaHarjoitteet(muokattava.getViite(), false);
    }
    
    /**
     * Muokataan sarjaa sarjan lisäämisen käyttöliittymästä
     */
    protected void muokkaaSarjaa() {
        if (sarjaLista.getObject(sarjaLista.getRowNr()) == null) {return; }
        Muokattava muokattava = sarjaLista.getObject(sarjaLista.getRowNr());
        paivakirja = LisaaSarjaGUIController.avaa(null, paivakirja, muokattava, 0);
        naytaSarjat(muokattava.getViite(), false);
    }
    
    /**
     * Merkintöjen näyttäminen käyttöliittymässä
     */
    protected void naytaMerkinnat() {
        merkintaLista.clear();
        if(paivakirja.getTreenit().isEmpty()) { naytaHarjoitteet(0, false);}
        for(Treeni trn : paivakirja.getTreenit()) {
            if (trn.getPvm() != null) {
                merkintaLista.add(trn.pvmToString(), trn);
            }
        }
        if (merkintaLista.getObjects().get(0) != null) {naytaTreenit(merkintaLista.getObjects().get(0).getId(), false);}
        else naytaTreenit(0, false);
    }
    
    /**
     * Treenien näyttäminen käyttöliittymässä
     * @param id Treenin id joka tuodaan valitulta pvm-oliolta.
     * @param naytaKanta boolean-arvo, näytetäänkö kantatreenit listassa
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaTreenit(int id, boolean naytaKanta) {
        treeniLista.clear();
        if(paivakirja.getTreenit().isEmpty()) { naytaHarjoitteet(0, false); return;}
        for(Treeni trn : paivakirja.getTreenit()) {
            if (trn.getId() == id && trn.getKanta() ==false && naytaKanta == false) { treeniLista.add(trn.getNimi(), trn); continue;}
            else if (trn.getKanta() == true && naytaKanta == true){ treeniLista.add(trn.getNimi(), trn);}
        }
        if (treeniLista.getObjects().get(0) != null) {naytaHarjoitteet(treeniLista.getObjects().get(0).getId(), false);}
        else naytaHarjoitteet(0, false);
    }
    
    /**
     * Harjoitteiden näyttäminen käyttöliittymässä
     * @param id Treenin id, jonka treenit halutaan näyttää
     * @param naytaKanta totuusarvo, halutaanko nähdä myös kantaoliot
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaHarjoitteet(int id, boolean naytaKanta) {
        harjoiteLista.clear();
        if(paivakirja.getHarjoitteet().isEmpty()) { naytaSarjat(0, false); return;}
        for(Harjoite har : paivakirja.getHarjoitteet()) {
            if (har.getTrid() == id && har.getKanta() == false && naytaKanta == false) { harjoiteLista.add(har.getNimi(), har); continue;}
            else if (har.getKanta() == true && naytaKanta == true) harjoiteLista.add(har.getNimi(), har);
        }
        if (harjoiteLista.getObjects().get(0) != null) {naytaSarjat(harjoiteLista.getObjects().get(0).getId(), false);}
        else naytaSarjat(0, false);
    }
    
    /**
     * Sarjojen näyttäminen käyttöliittymässä
     * @param id Harjoitteen id, jonka sarjat halutaan näyttää
     * @param naytaKaikki boolean-arvo, näytetäänkö kaikki olemassaolevat sarjat
     * @throws NullPointerException jos valitsijan palauttava olio null
     */
    protected void naytaSarjat(int id, boolean naytaKaikki) {
        sarjaLista.clear();
        String[] headings = {"Työpaino", "Toistot", "Toteutuneet"};
        sarjaLista.initTable(headings);
        if(paivakirja.getSarjat().isEmpty()) {return;}
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
        try {
            boolean pvm = false;
            if(hakuehto.getSelectedObject() == null) {return;}
            if(hakuehto.getSelectedText().equals("Merkintä")) {pvm = true;}
            ArrayList<Muokattava> tulokset = (ArrayList<Muokattava>) paivakirja.haku(hakuehto.getSelectedObject(), hakulauseke.getText(), pvm);
            if(tulokset.isEmpty() || hakulauseke.getText() == null) {naytaMerkinnat(); return;}
            else if(tulokset.get(0).getClass() == Harjoite.class) {harjoiteLista.clear(); for(Muokattava harjoite : tulokset) {harjoiteLista.add(((Harjoite) harjoite).getNimi(), (Harjoite) harjoite);}}
            else if(tulokset.get(0).getClass() == Treeni.class && pvm == false)  {treeniLista.clear(); for(Muokattava treeni : tulokset) {treeniLista.add(((Treeni) treeni).getNimi(), (Treeni) treeni);}}
            else if(tulokset.get(0).getClass() == Treeni.class && pvm == true)  {merkintaLista.clear(); for(Muokattava treeni : tulokset) {merkintaLista.add(((Treeni) treeni).pvmToString(), (Treeni) treeni);}}
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Ohjelmaa avatessa avaa tiedostosta ladatut tiedot käyttöliittymään oletuslokaatiosta. Valmius useammillekin tiedostoille.
     * @throws SailoException jos tiedostosta lukemisessa ongelmia
     */
    private void avaa() throws SailoException{
        try {
            paivakirja.lueTiedostosta();
            naytaMerkinnat();
        } catch (Exception e) {
            return;
        }
    }
    
    /**
     * Kaiken tiedon tallentaminen erillisinä tiedostoina.
     * @throws SailoException 
     */
    private void tallenna() throws SailoException {
        if (paivakirja.getTreenit().isEmpty() && paivakirja.getHarjoitteet().isEmpty() && paivakirja.getSarjat().isEmpty()) {
            Dialogs.showMessageDialog("Älä tallenna tyhjää - voit menettää tärkeitä tietoja!");
        }
        paivakirja.tallenna();
    }
    
    /**
     * @param poistettava poistettava olio
     * @param kohde kohde, mistä poistetaan
     */
    @SuppressWarnings("all")
    public void poista(Muokattava poistettava, Class<?> kohde) {
        if(poistettava == null) {return;}
        Muutettava <Muokattava>muutettava = new Muutettava<Muokattava>(poistettava, kohde);
        paivakirja = PoistoGUIController.avaa(null, paivakirja, muutettava);
        if (poistettava.getClass() == Treeni.class) {naytaMerkinnat();}
        if (poistettava.getClass() == Treeni.class) {naytaTreenit(poistettava.getViite(), false);}
        if (poistettava.getClass() == Harjoite.class) {naytaHarjoitteet(poistettava.getViite(), false);}
        if (poistettava.getClass() == Sarja.class) {naytaSarjat(poistettava.getViite(), false);}
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