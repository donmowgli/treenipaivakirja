package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import Treenipvk.Harjoite;
import Treenipvk.Paivakirja;
import Treenipvk.Sarja;
import fi.jyu.mit.fxgui.Dialogs;
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
 * Controller-luokka harjoitteen lisäämiselle
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaHarjoiteGUIController {
    
    private Paivakirja paivakirja;
    private Muokattava muokattava;
    private Harjoite harjoite = new Harjoite();
    private Tarkistus tarkistus = new Tarkistus();
    private static Stage stage = new Stage();
    
    @FXML private TextField nimi;
    @FXML private TextField lkm;
    
    @SuppressWarnings("all")
    private void alusta(Paivakirja paivakirja, Muokattava muokattava) {
        this.paivakirja = paivakirja;
        this.muokattava = muokattava;
        if (muokattava != null) {
            ArrayList<String> arvot = muokattava.getArvot();
            this.nimi.setText(arvot.get(0));;
            this.lkm.setText(arvot.get(1));
        }
    }
    
    /**
     * Handle-funktio OK-napin painallukselle
     */
    @FXML
    private void handleOK() {
        String tarkistettu = tarkistus.tarkista(nimi.getText(), null); if (tarkistettu != null) { Dialogs.showMessageDialog(tarkistettu); return; }
        tarkistettu = tarkistus.tarkistaInteger(lkm.getText()); if (tarkistettu != null) { Dialogs.showMessageDialog(tarkistettu); return; }
        if (muokattava != null) { muokkaa(); stage.close(); return;}
        harjoite.setNimi(nimi.getText());
        harjoite.setSarlkm(Integer.parseInt(lkm.getText()));
        harjoite.setKanta(true);
        harjoite.rekisteroi();
        lisaaSarjat(harjoite.getHarid());
        paivakirja.lisaa(harjoite);
        stage.hide();
    }
    
    private void lisaaSarjat(int id) {
        paivakirja = LisaaSarjaGUIController.avaa(null, paivakirja, null, id);
        Sarja sarja = paivakirja.getSarjat().get(paivakirja.getSarjat().size() - 1);
        for (int i = 1; i < Integer.parseInt(lkm.getText()); i++) {
            Sarja klooni = sarja.clone();
            klooni.rekisteroi();
            paivakirja.lisaa(klooni);
        }
    }
    
    private void muokkaa() {
        Harjoite uusi = paivakirja.getHarjoite(muokattava.getId());
        uusi.setNimi(nimi.getText());
        uusi.setSarlkm(Integer.parseInt(lkm.getText()));
        paivakirja.poista(paivakirja.getHarjoite(muokattava.getId()));
        paivakirja.lisaa(uusi);
    }
    
    /**
     * Avataan Harjoite-dialogi ja sarjan lisäämiselle.
     * @param paivakirja Päiväkirja, jota halutaan muokata
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param muokattava jos muokataan, niin muokattava objekti
     * @return palauttaa täytetyn ja muokatun päiväkirjaolion
     */
    @SuppressWarnings("all")
    public static Paivakirja avaa(Stage modalityStage, Paivakirja paivakirja, Muokattava muokattava) {
        try {
            URL url = LisaaHarjoiteGUIController.class.getResource("LisaaHarjoiteView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            final LisaaHarjoiteGUIController ctrl = (LisaaHarjoiteGUIController)loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Harjoite");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            ctrl.alusta(paivakirja, muokattava);
            stage.showAndWait();
            stage = new Stage();
            //ctrl.getResult(); //mitä vittua tällä tehdään
            return paivakirja;
            
        } catch (IOException e) {
            e.printStackTrace();
            return paivakirja;
        }
    }
}
