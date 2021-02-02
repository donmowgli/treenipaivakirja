package treenipvk;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Akseli Jaara
 * @version 2.2.2021
 *
 */
public class TreenipvkMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("TreenipvkGUIView.fxml"));
            final Pane root = ldr.load();
            //final TreenipvkGUIController treenipvkCtrl = (TreenipvkGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("treenipvk.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("treenipvk");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        launch(args);
    }
}