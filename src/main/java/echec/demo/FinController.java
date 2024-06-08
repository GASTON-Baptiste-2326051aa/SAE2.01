package echec.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ResourceBundle;



public class FinController implements Initializable {

    @FXML
    private Label LabelWin1V;
    @FXML
    private Image ImWin1V;
    @FXML
    private Image ImLoss1V;
    @FXML
    private Label NomLoss1V;
    @FXML
    private Label NomWin1V;
    @FXML
    private Button boutonAcc;

    private ButtonController buttonController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonController = new ButtonController();
        buttonController.initButtonAcc(boutonAcc);
    }
    public void setPlayerNames(String winnerName, String loserName) {
        LabelWin1V.setText("Vous avez gagné face à " + loserName);
        NomWin1V.setText(winnerName);
        NomLoss1V.setText(loserName);
    }
}
