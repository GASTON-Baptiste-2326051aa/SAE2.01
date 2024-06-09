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

    /**
     * Méthode appelée lors du démarrage de l'application JavaFX.
     * Cette méthode configure et affiche la scène principale de l'application.
     *
     * @param stage Le stage principal de l'application JavaFX.
     * @throws Exception Si une erreur se produit lors du chargement de la scène depuis le fichier FXML.
     * @see Application#start(Stage)
     * @author Baptiste Gaston
     */
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
