package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import Treenipvk.Harjoite;
import Treenipvk.Paivakirja;
import Treenipvk.SailoException;
import Treenipvk.Treeni;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kanta.Muokattava;
import kanta.Tarkistus;

/**
 * Controller-luokka Treenin lisäämiselle
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaTreeniGUIController {
    
    private Paivakirja paivakirja;
    private Muokattava muokattava;
    private Treeni treeni = new Treeni();
    private Tarkistus tarkistus = new Tarkistus();
    private static Stage stage = new Stage();
    
    @FXML private TextField nimi;
    @FXML private ListChooser<Harjoite> harjoitteet;
    @FXML private ListChooser<Harjoite> lisattava;
    
    @SuppressWarnings("all")
    public void alusta(Paivakirja paivakirja, Muokattava muokattava) {
        this.paivakirja = paivakirja;
        this.muokattava = muokattava;
        harjoitteet.clear();
        harjoitteet.setOnMouseClicked(e ->{ if (e.getClickCount() > 1) lisaaListaan(harjoitteet.getSelectedObject()); });
        lisattava.clear();
        naytaValittavat();
        if (muokattava != null) {naytaLisattavat();}
    }
    
    private void lisaaListaan(Harjoite harjoite) {
        lisattava.add(harjoite.getNimi(), harjoite);
    }
    
    /**
     * Handle-funktio OK-napin painallukselle
     * @throws SailoException 
     */
    @FXML
    private void handleOK() throws SailoException {
        String tarkistettu = tarkistus.tarkista(nimi.getText(), null);
        if (tarkistettu != null) { Dialogs.showMessageDialog(tarkistettu); return; }
        if (this.lisattava.getObjects().isEmpty()) {Dialogs.showMessageDialog("Lisää ainakin yksi lisättävä harjoite!"); return;}
        if (muokattava != null) { muokkaa(); stage.close(); return;}
        this.treeni.setNimi(nimi.getText());
        this.treeni.setKanta(true);
        this.treeni.rekisteroi();
        for(Harjoite harjoite : lisattava.getObjects()) {
            Harjoite klooni = harjoite.clone();
            klooni.rekisteroi();
            klooni.setTrid(treeni.getTrid());
            paivakirja.kopioi(harjoite, klooni.getId());
            paivakirja.lisaa(klooni);
        }
        paivakirja.lisaa(this.treeni);
        stage.hide();
    }
    
    /**
     * Handle-funktio Tyhjennä-napin painamiselle. Tyhjennetään näytetty lista kokonaisuudessan, jotta lisäämistä voidaan jatkaa
     */
    @FXML
    private void handleTyhjenna() {
        lisattava.clear();
    }
    
    /**
     * Näytetään kaikki lisättävissä olevat harjoitteet listalla. Valitaan vain alustetut harjoitteet, joita ei sidottu vielä erikseen mihinkään treeniin, eli joiden trid == 0.
     */
    private void naytaValittavat() {
        try {
            for(Harjoite har : paivakirja.getHarjoitteet()) {
                if(har.getKanta() == true) {
                    harjoitteet.add(har.getNimi(), har);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Näytetään muokkaustapauksessa myös lisättävät
     */
    private void naytaLisattavat() {
        nimi.setText(paivakirja.getTreeni(muokattava.getId()).getNimi());
        ArrayList<Harjoite> lisattavat = paivakirja.getHarjoitteet();
        for (Harjoite harjoite : lisattavat) {
            if(harjoite.getTrid() == muokattava.getId()) {
                lisattava.add(harjoite.getNimi(), harjoite);
            }
        }
    }
    
    private void muokkaa() throws SailoException {
        Treeni uusi = paivakirja.getTreeni(muokattava.getId());
        uusi.setNimi(nimi.getText());
        for(Harjoite harjoite : lisattava.getObjects()) {
            Harjoite klooni = harjoite.clone();
            klooni.rekisteroi();
            klooni.setTrid(treeni.getTrid());
            paivakirja.lisaa(klooni);
        }
        paivakirja.poista(paivakirja.getTreeni(muokattava.getId()));
        paivakirja.lisaa(uusi);
    }
    
    /**
     * Avataan Treeni-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param paivakirja Päiväkirja-olio, jot ahalutaan muokata
     * @param muokattava muokattava Treeni-olio, jos halutaan muokata
     * @return palautetaan muokattu Päiväkirja-olio
     */
    @SuppressWarnings("all")
    public static Paivakirja avaa(Stage modalityStage, Paivakirja paivakirja, Muokattava muokattava) {
        try {
            URL url = LisaaTreeniGUIController.class.getResource("LisaaTreeniView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            final LisaaTreeniGUIController ctrl = (LisaaTreeniGUIController)loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Treeni");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            ctrl.alusta(paivakirja, muokattava);
            stage.showAndWait();
            stage = new Stage();
            return paivakirja;
        } catch (IOException e) {
            e.printStackTrace();
            return paivakirja;
        }
    }
}
