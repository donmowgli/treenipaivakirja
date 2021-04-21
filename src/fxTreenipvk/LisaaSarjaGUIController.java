package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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
 * Controller-luokka sarjan lisäämiselle
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaSarjaGUIController {
    
    private  int id = 0;
    private Paivakirja paivakirja;
    private Muokattava muokattava;
    private Sarja sarja = new Sarja();
    private Tarkistus tarkistus = new Tarkistus();
    private static Stage stage = new Stage();
    
    @FXML private TextField tyopaino;
    @FXML private TextField toistot;
    @FXML private TextField toteutuneet;
    
    @SuppressWarnings("all")
    private void alusta(Paivakirja paivakirja, Muokattava muokattava, int id) {
        this.paivakirja = paivakirja;
        this.muokattava = muokattava;
        this.id = id;
        if (muokattava != null) {
            ArrayList<String> arvot = muokattava.getArvot();
            this.tyopaino.setText(arvot.get(0));
            this.toistot.setText(arvot.get(1));
            this.toteutuneet.setText(arvot.get(2));
        }
    }
    
    /**
     * Handle-funktio OK-napin painallukselle
     */
    @FXML
    private void handleOK() {
        String tarkistettu = tarkistus.tarkistaDouble(tyopaino.getText()); if (tarkistettu != null) {Dialogs.showMessageDialog(tarkistettu); return; }
        tarkistettu = tarkistus.tarkistaInteger(toistot.getText()); if (tarkistettu != null) {Dialogs.showMessageDialog(tarkistettu); return; }
        tarkistettu = tarkistus.tarkistaInteger(toteutuneet.getText()); if (tarkistettu != null) {Dialogs.showMessageDialog(tarkistettu); return; }
       if (muokattava != null) { muokkaa(); stage.close(); return;}
       sarja.setTyopaino(Double.parseDouble(tyopaino.getText()));
       sarja.setToistot(Integer.parseInt(toistot.getText()));
       sarja.setToteutuneet(Integer.parseInt(toteutuneet.getText()));
       sarja.setHarid(id);
       sarja.rekisteroi();
       paivakirja.lisaa(sarja);
       stage.close();
    }
    
    /**
     * Muokataan Sarja-oliota vastaamaan valintoja
     */
    private void muokkaa() {
        Sarja uusi = paivakirja.getSarja(muokattava.getId());
        uusi.setTyopaino(Double.parseDouble(tyopaino.getText()));
        uusi.setToistot(Integer.parseInt(this.toistot.getText()));
        uusi.setToteutuneet(Integer.parseInt(this.toteutuneet.getText()));
        paivakirja.poista(paivakirja.getSarja(muokattava.getId()));
        paivakirja.lisaa(uusi);
    }
    
    /**
     * Avataan Sarja-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param paivakirja päiväkirjaolio, johon muutokset halutaan tehdä
     * @param muokattava jos muokataan, niin muokattava Sarja-olio
     * @param id harjoituksen id, joka sarjalle halutaan asettaa.
     * @return palauttaa muokatun Päiväkirja-olion
     */
    @SuppressWarnings("all")
    public static Paivakirja avaa(Stage modalityStage, Paivakirja paivakirja, Muokattava muokattava, int id) {
        try {
            URL url = LisaaSarjaGUIController.class.getResource("LisaaSarjaView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            final LisaaSarjaGUIController ctrl = (LisaaSarjaGUIController)loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Sarja");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            ctrl.alusta(paivakirja, muokattava, id);
            stage.showAndWait();
            stage = new Stage();
            return paivakirja;
        } catch (IOException e) {
            e.printStackTrace();
            return paivakirja;
        }
    }
}
