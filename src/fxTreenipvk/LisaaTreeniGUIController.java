package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Treenipvk.Harjoite;
import Treenipvk.Harjoitteet;
import Treenipvk.SailoException;
import Treenipvk.Treeni;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller-luokka Treenin lisäämiselle
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaTreeniGUIController implements Initializable {
    private Treeni treeni = new Treeni();
    private static Stage stage = new Stage();
    
    @FXML private TextField nimi;
    @FXML private ListChooser<Harjoite> harjoitteet;
    @FXML private ListChooser<Harjoite> lisattava;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        harjoitteet.clear();
        harjoitteet.setOnMouseClicked(e ->{ if (e.getClickCount() > 1) lisaaListaan(harjoitteet.getSelectedObject()); });
        lisattava.clear();
        naytaValittavat();
        if (TreenipvkGUIController.muokataanko == true) {naytaLisattavat();}
    }
    
    private void lisaaListaan(Harjoite harjoite) {
        lisattava.add(harjoite.getNimi(), harjoite);
    }
    
    /**
     * Handle-funktio OK-napin painallukselle
     */
    @FXML
    private void handleOK() {
        try {
            if (TreenipvkGUIController.muokataanko == true) { muokkaa(); stage.close(); return;}
            this.treeni.setNimi(nimi.getText());
            this.treeni.rekisteroi();
            for(Harjoite harjoite : lisattava.getObjects()) {
                Harjoite klooni = harjoite.clone();
                klooni.rekisteroi();
                klooni.setTrid(treeni.getTrid());
                TreenipvkGUIController.paivakirja.getHarjoitteet().lisaaHarjoite(klooni);
            }
            TreenipvkGUIController.paivakirja.lisaa(this.treeni);
            stage.hide();
        }catch (Exception e) {
            e.printStackTrace();
            Dialogs.showMessageDialog("Valitse ainakin yksi treeniin lisättävä harjoite.");
        }
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
            for(Harjoite har : TreenipvkGUIController.paivakirja.getHarjoitteet()) {
                if(har.getTrid() == 0) {
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
        nimi.setText(TreenipvkGUIController.paivakirja.getTreenit().getTreeni(TreenipvkGUIController.muokattava.getId()).getNimi());
        Harjoitteet lisattavat = TreenipvkGUIController.paivakirja.getHarjoitteet();
        for (Harjoite harjoite : lisattavat) {
            if(harjoite.getTrid() == TreenipvkGUIController.muokattava.getId()) {
                lisattava.add(harjoite.getNimi(), harjoite);
            }
        }
    }
    
    private void muokkaa() throws SailoException {
        Treeni uusi = TreenipvkGUIController.paivakirja.getTreenit().getTreeni(TreenipvkGUIController.muokattava.getId());
        uusi.setNimi(nimi.getText());
        for(Harjoite harjoite : lisattava.getObjects()) {
            Harjoite klooni = harjoite.clone();
            klooni.rekisteroi();
            klooni.setTrid(treeni.getTrid());
            TreenipvkGUIController.paivakirja.getHarjoitteet().lisaaHarjoite(klooni);
        }
        TreenipvkGUIController.paivakirja.poista(TreenipvkGUIController.paivakirja.getTreenit().getTreeni(TreenipvkGUIController.muokattava.getId()));
        TreenipvkGUIController.paivakirja.lisaa(uusi);
    }
    
    /**
     * Avataan Treeni-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     */
    public static void avaa(Stage modalityStage) {
        try {
            URL url = LisaaHarjoiteGUIController.class.getResource("LisaaTreeniView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Treeni");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            stage.showAndWait();
            stage = new Stage();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
