package echec.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {


    @FXML
    private BorderPane borderPane;

    @FXML
    private Button boutonJvJlog;
    @FXML
    private Button boutonJvBlog;

    private ButtonController buttonController;

    public void initialize(URL url, ResourceBundle resourceBundle) { // Initialisation des boutons Controller pour savoir quel mode de jeux on choisi
        buttonController = new ButtonController();
        if (boutonJvJlog != null) {
            buttonController.initButtonLogJvJ(boutonJvJlog);
        }
        if (boutonJvBlog != null) {
            buttonController.initButtonLogJvB(boutonJvBlog);
        }

    }
}