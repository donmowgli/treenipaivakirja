package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Treenipvk.Harjoite;
import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kanta.Tarkistus;

/**
 * Controller-luokka harjoitteen lisäämiselle
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaHarjoiteGUIController implements Initializable {
    private static int id = 0;
    private Harjoite harjoite = new Harjoite();
    private Tarkistus tarkistus = new Tarkistus();
    private static Stage stage = new Stage();
    
    @FXML private TextField nimi;
    @FXML private TextField lkm;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (TreenipvkGUIController.muokataanko == true) {
            ArrayList<String> arvot = TreenipvkGUIController.muokattava.getArvot();
            this.nimi.setText(arvot.get(0));;
            this.lkm.setText(arvot.get(1));
        }
    }
    
    /**
     * Handle-funktio OK-napin painallukselle
     */
    @FXML
    private void handleOK() {
        try {
            String tarkistettu = tarkistus.tarkista(nimi.getText() + lkm.getText(), null);
            if (tarkistettu != null) { Dialogs.showMessageDialog(tarkistettu); return; }
            if (TreenipvkGUIController.muokataanko == true) { muokkaa(); stage.close(); return;}
            harjoite.setNimi(nimi.getText());
            harjoite.setSarlkm(Integer.parseInt(lkm.getText()));
            harjoite.setTrid(id);
            harjoite.rekisteroi();
            TreenipvkGUIController.paivakirja.getHarjoitteet().lisaaHarjoite(harjoite);
            stage.hide();
        }catch (NumberFormatException e) {
            Dialogs.showMessageDialog("Sarjojen lukumäärä tulee olla numeroina!");
        }
    }
    
    private void muokkaa() {
        Harjoite uusi = TreenipvkGUIController.paivakirja.getHarjoitteet().getHarjoite(TreenipvkGUIController.muokattava.getId());
        uusi.setNimi(nimi.getText());
        uusi.setSarlkm(Integer.parseInt(lkm.getText()));
        TreenipvkGUIController.paivakirja.poista(TreenipvkGUIController.paivakirja.getHarjoitteet().getHarjoite(TreenipvkGUIController.muokattava.getId()));
        TreenipvkGUIController.paivakirja.lisaa(uusi);
    }
    
    /**
     * Avataan Harjoite-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param trid Treenin id, jolle sarja halutaan asettaa
     */
    public static void avaa(Stage modalityStage, int trid) {
        try {
            URL url = LisaaHarjoiteGUIController.class.getResource("LisaaHarjoiteView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Harjoite");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            id = trid;
            stage.showAndWait();
            stage = new Stage();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
