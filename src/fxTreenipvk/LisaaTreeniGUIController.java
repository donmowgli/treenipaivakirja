package fxTreenipvk;

import java.io.IOException;
import java.net.URL;

import Treenipvk.Harjoite;
import Treenipvk.Paivakirja;
import Treenipvk.Treeni;
import fi.jyu.mit.fxgui.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller-luokka Treenin lisäämiselle
 * TODO bugin korjaaminen, jossa stage.hide() ei toimi.
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaTreeniGUIController {
    private  Paivakirja paivakirja;
    private  Treeni treeni;
    private  Stage stage;
    
    @FXML private TextField nimi;
    @FXML private ListView<Harjoite> harjoitteet = new ListView<Harjoite>();
    @FXML private ListView<Harjoite> lisattava = new ListView<Harjoite>();
    
    /**
     * Testi
     */
    public LisaaTreeniGUIController() {
        this.paivakirja = new Paivakirja();
        this.treeni = new Treeni();
        this.stage = new Stage();
    }
    
    /**
     * Handle-funktio OK-napin painallukselle
     */
    @FXML
    private void handleOK() {
        try {
            this.treeni.setNimi(nimi.getText());
            for(Harjoite har : lisattava.getItems()) {
                har.setTrid(treeni.getTrid());
                paivakirja.getHarjoitteet().lisaaHarjoite(har);
            }
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
        ObservableList<Harjoite> tyhja = FXCollections.observableArrayList();
        lisattava.setItems(tyhja);
    }
    
    /**
     * Näytetään kaikki lisättävissä olevat harjoitteet listalla. Valitaan vain alustetut harjoitteet, joita ei sidottu vielä erikseen mihinkään treeniin, eli joiden trid == 0.
     */
    private void nayta() {
        for(Harjoite har : paivakirja.getHarjoitteet()) {
            if(har.getTrid() == 0) {
                harjoitteet.getItems().add(har);
            }
        }
        harjoitteet.refresh();
    }
    
    /**
     * Palautetaan lisätty Harjoite-olio
     * @return Controllerin Harjoite-olio
     */
    private Paivakirja getResult() {
        return paivakirja;
    }
    
    private void alusta() {
        harjoitteet.setCellFactory(null);
        lisattava.setCellFactory(null);
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
            alusta();
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
