package echec.demo;

import echec.Pions.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.ArrayList;


public class EchecApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        Screen screen = Screen.getPrimary();
        FXMLLoader loader = new FXMLLoader(EchecApp.class.getResource("view/accueil.fxml"));
        scene = new Scene(loader.load());
        scene.getRoot().setStyle("-fx-background-color: rgb(48, 46, 43);");
        Image image = new Image(getClass().getResourceAsStream("img/logo.png"));
        stage.getIcons().add(image);
        stage.setMinHeight(1080);
        stage.setMinWidth(1920);
        stage.setTitle("Echec");
        stage.setScene(scene);

        stage.show();
    }
}
