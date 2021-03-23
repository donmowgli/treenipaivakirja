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
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaTreeniGUIController {
    private  Paivakirja paivakirja;
    private  Treeni treeni;
    private  static Stage stage;
    
    @FXML private TextField nimi;
    @FXML private static ListView<String> harjoitteet = new ListView<String>();
    @FXML private static ListView<String> lisattava = new ListView<String>();
    
    /**
     * Testi
     */
    public LisaaTreeniGUIController() {
        this.paivakirja = new Paivakirja();
        this.treeni = new Treeni();
        LisaaTreeniGUIController.stage = new Stage();
    }
    
    /**
     * Handle-funktio OK-napin painallukselle
     */
    @FXML
    private void handleOK() {
        try {
            this.treeni.setNimi(nimi.getText());
            for(String tNimi : lisattava.getItems()) {
                for(Harjoite harjoite : paivakirja.getHarjoitteet()) {
                    if(tNimi == harjoite.getNimi() && harjoite.getHarid() == 0) {
                        paivakirja.getHarjoitteet().lisaaHarjoite(harjoite);
                    }
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
        ObservableList<String> tyhja = FXCollections.observableArrayList();
        lisattava.setItems(tyhja);
    }
    
    /**
     * Näytetään kaikki lisättävissä olevat harjoitteet listalla. Valitaan vain alustetut harjoitteet, joita ei sidottu vielä erikseen mihinkään treeniin, eli joiden trid == 0.
     */
    private void nayta() {
        try {
            ObservableList<String> naytto = FXCollections.observableArrayList();
            for(Harjoite har : paivakirja.getHarjoitteet()) {
                if(har.getTrid() == 0) {
                    System.out.println(har.getNimi());
                    naytto.add(har.getNimi());
                }
            }
            harjoitteet.setItems(naytto);
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
