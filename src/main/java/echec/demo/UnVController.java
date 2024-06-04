package echec.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UnVController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private Button bouton3;
    @FXML

    private ButtonController buttonController;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonController = new ButtonController();
        buttonController.initButton3(bouton3);
    }

}
