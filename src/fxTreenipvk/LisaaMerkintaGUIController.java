package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import Treenipvk.Paivakirja;
import Treenipvk.SailoException;
import Treenipvk.Treeni;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kanta.Muokattava;
import kanta.Tarkistus;

/**
 * Controller-luokka merkinnän lisäämiselle
 * @author Akseli Jaara
 * @version 22 Mar 2021
 *
 */
public class LisaaMerkintaGUIController{
    
    private Paivakirja paivakirja;
    private Muokattava muokattava;
    private Treeni treeni = new Treeni();
    private Tarkistus tarkistus = new Tarkistus();
    private static Stage stage = new Stage();
    
    @FXML private TextField pvm;
    @FXML private ListChooser<Treeni> treenit;
    
    @SuppressWarnings("all")
    private void alusta(Paivakirja paivakirja, Muokattava muokattava) {
        this.paivakirja = paivakirja;
        this.muokattava = muokattava;
        treenit.clear();
        nayta();
        if(muokattava != null) {asetaValittu();}
    }
    
    /**
     * Handle OK-painikkeelle
     * @throws SailoException 
     */
    @FXML
    private void handleOK() throws SailoException {
        try {
            String tarkistettu = tarkistus.tarkistaPvm(pvm.getText());
            if (tarkistettu != null) { Dialogs.showMessageDialog(tarkistettu); return; }
            if(muokattava != null) {muokkaa(); stage.close(); return;}
            String[] arvot = pvm.getText().split("\\.");
            LocalDate annettu = LocalDate.of(Integer.parseInt(arvot[2]), Integer.parseInt(arvot[1]), Integer.parseInt(arvot[0]));
            this.treeni = treenit.getSelectedObject().clone();
            this.treeni.setPvm(annettu);
            this.treeni.rekisteroi();
            paivakirja.kopioi(treenit.getSelectedObject(), treeni.getId());
            paivakirja.lisaa(this.treeni);
            stage.hide();
        } catch (NullPointerException e) {
            Dialogs.showMessageDialog("Valitse merkintään lisättävä harjoite!");
            e.printStackTrace();
            return;
        }
    }
    
    /**
     * Naytetään treenien oletusmuodot (joilla ei merkintää, eli ei omaa päivämäärää)
     */
    private void nayta() {
        for(Treeni trn : paivakirja.getTreenit()) {
            if(trn.getPvm() == null) {
                treenit.add(trn.getNimi(), trn);
            }
        }
    }
    
    /**
     * Asetetaan muokkausta varten valinta chooserille
     */
    private void asetaValittu() {
        for(int i = 0; i < treenit.getObjects().size(); i++) {
            if(treenit.getObjects().get(i).getId() == muokattava.getId()) {
                treenit.setSelectedIndex(i);
                this.pvm.setText(treenit.getObjects().get(i).pvmToString());
                return;
            }
        }
    }
    
    private void muokkaa() throws SailoException {
        String[] arvot = pvm.getText().split("\\.");
        LocalDate annettu = LocalDate.of(Integer.parseInt(arvot[2]), Integer.parseInt(arvot[1]), Integer.parseInt(arvot[0]));
        Treeni uusi = paivakirja.getTreeni(muokattava.getId());
        uusi.setPvm(annettu);
        paivakirja.poista(paivakirja.getTreeni(muokattava.getId()));
        paivakirja.lisaa(uusi);
    }
    
    /**
     * Avataan Sarja-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param paivakirja Päiväkirja-olio, jota halutaan muokata
     * @param muokattava muokattava Treeni-olio, jos halutaan muokata
     * @return palauttaa muokatun Päiväkirja-olion
     */
    @SuppressWarnings("all")
    public static Paivakirja avaa(Stage modalityStage, Paivakirja paivakirja, Muokattava muokattava) {
        try {
            URL url = LisaaMerkintaGUIController.class.getResource("LisaaMerkintaView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            final LisaaMerkintaGUIController ctrl = (LisaaMerkintaGUIController)loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Merkintä");
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
