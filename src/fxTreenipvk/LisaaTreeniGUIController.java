package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Treenipvk.Harjoite;
import Treenipvk.Paivakirja;
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
    private Paivakirja paivakirja;
    private Treeni treeni;
    private static Stage stage;
    
    @FXML private TextField nimi;
    @FXML private ListChooser<Harjoite> harjoitteet;
    @FXML private ListChooser<Harjoite> lisattava;
    
    /**
     * Testi
     */
    public LisaaTreeniGUIController() {
        LisaaTreeniGUIController.stage = new Stage();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        harjoitteet = new ListChooser<Harjoite>();
        lisattava = new ListChooser<Harjoite>();
        harjoitteet.clear();
        lisattava.clear();
    }
    
    /**
     * Handle-funktio OK-napin painallukselle
     * TODO Milloin rekisteröidään?
     */
    @FXML
    private void handleOK() {
        try {
            this.treeni.setNimi(nimi.getText());
            this.treeni.rekisteroi();
            for(Harjoite harjoite : lisattava.getObjects()) {
                if(harjoite.getHarid() == 0) {
                    harjoite.setTrid(treeni.getTrid());
                    paivakirja.getHarjoitteet().lisaaHarjoite(harjoite);
                }
            }
            paivakirja.getTreenit().lisaaTreeni(treeni);
            stage.hide();
        }catch (Exception e) {
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
    private void nayta() {
        try {
            for(Harjoite har : paivakirja.getHarjoitteet()) {
                if(har.getTrid() == 0) {
                    System.out.println(har.getNimi());
                    harjoitteet.add(har.getNimi(), har);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Palautetaan lisätty Harjoite-olio
     * @return Controllerin Harjoite-olio
     */
    private Paivakirja getResult() {
        return paivakirja;
    }
    
    /**
     * Avataan Treeni-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param pvk Päiväkirja-olio, johon muokkauksia halutaan tehdä
     * @return palauttaa lisätyn Harjoite-olion.
     */
    public Paivakirja avaa(Stage modalityStage, Paivakirja pvk) {
        try {
            URL url = LisaaHarjoiteGUIController.class.getResource("LisaaTreeniView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            final LisaaTreeniGUIController dialogCtrl = (LisaaTreeniGUIController)loader.getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Treeni");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            
            paivakirja = pvk;
            nayta();
            
            stage.showAndWait();
            stage = new Stage();
            return dialogCtrl.getResult();
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
