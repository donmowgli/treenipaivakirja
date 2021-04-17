package fxTreenipvk;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Treenipvk.Sarja;
import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller-luokka sarjan lisäämiselle
 * @author Akseli Jaara
 * @version 20 Mar 2021
 *
 */
public class LisaaSarjaGUIController implements Initializable{
    private static int id = 0;
    private Sarja sarja = new Sarja();
    private static Stage stage = new Stage();
    
    @FXML private TextField tyopaino;
    @FXML private TextField toistot;
    @FXML private TextField toteutuneet;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (TreenipvkGUIController.muokataanko == true) {
            ArrayList<String> arvot = TreenipvkGUIController.muokattava.getArvot();
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
        try {
            if (TreenipvkGUIController.muokataanko == true) { muokkaa(); stage.close(); return;}
            sarja.setTyopaino(Integer.parseInt(tyopaino.getText()));
            sarja.setToistot(Integer.parseInt(toistot.getText()));
            sarja.setToteutuneet(Integer.parseInt(toteutuneet.getText()));
            sarja.setHarid(id);
            sarja.rekisteroi();
            stage.close();
            TreenipvkGUIController.paivakirja.getSarjat().lisaaSarja(sarja);
        }catch (NumberFormatException e) {
            Dialogs.showMessageDialog("Tiedot tulee olla numeroina!");
        }
    }
    
    /**
     * Muokataan Sarja-oliota vastaamaan valintoja
     */
    private void muokkaa() {
        Sarja uusi = TreenipvkGUIController.paivakirja.getSarjat().getSarja(TreenipvkGUIController.muokattava.getId());
        uusi.setTyopaino(Integer.parseInt(tyopaino.getText()));
        uusi.setToistot(Integer.parseInt(this.toistot.getText()));
        uusi.setToteutuneet(Integer.parseInt(this.toteutuneet.getText()));
        TreenipvkGUIController.paivakirja.poista(TreenipvkGUIController.paivakirja.getSarjat().getSarja(TreenipvkGUIController.muokattava.getId()));
        TreenipvkGUIController.paivakirja.lisaa(uusi);
    }
    
    /**
     * Avataan Sarja-dialogi ja sarjan lisäämiselle.
     * @param modalityStage modaalisuus, joka halutaan: ollaanko modaalisia jollekin toiselle ikkunalle.
     * @param harid harjoituksen id, joka sarjalle halutaan asettaa.
     */
    public static void avaa(Stage modalityStage, int harid) {
        try {
            URL url = LisaaSarjaGUIController.class.getResource("LisaaSarjaView.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Sarja");
            if ( modalityStage != null ) {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(modalityStage);
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
            }
            id = harid;
            stage.showAndWait();
            stage = new Stage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
