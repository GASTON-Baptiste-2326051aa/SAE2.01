package echec.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class EchecApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(EchecApp.class.getResource("view/accueil.fxml"));
        scene = new Scene(loader.load());
        stage.setTitle("Echec");
        stage.setScene(scene);
        stage.show();
    }
}