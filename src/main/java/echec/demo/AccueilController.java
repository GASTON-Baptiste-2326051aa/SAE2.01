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
    private Button bouton1;
    @FXML
    private Button bouton2;

    private ButtonController buttonController;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonController = new ButtonController();
        buttonController.initButton1(bouton1);
        buttonController.initButton2(bouton2);
    }
}