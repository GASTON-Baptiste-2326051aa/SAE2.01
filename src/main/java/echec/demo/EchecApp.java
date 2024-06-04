package echec.demo;

import echec.Pions.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;


public class EchecApp extends Application {

    private static Scene scene;



    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(EchecApp.class.getResource("view/accueil.fxml"));
        scene = new Scene(loader.load());
        scene.getRoot().setStyle("-fx-background-color: rgb(48, 46, 43);");
        stage.setMinHeight(1080);
        stage.setMinWidth(1920);
        stage.setTitle("Echec");
        stage.setScene(scene);
        stage.show();
    }
}