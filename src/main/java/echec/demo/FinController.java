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

    /**
     * Initialisations de la classe.
     * @params  passe en parametres l'url et les ressources utilisé pour localisé les objets utilisé par le root.
     **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //initialisation du bouton Acceuil
        buttonController = new ButtonController();
        buttonController.initButtonAcc(boutonAcc);
    }
    public void setPlayerNames(String winnerName, String loserName) { //Creation d'une fonction pour mettre les noms du perdant et du gagnant a la fin.
        LabelWin1V.setText("Vous avez gagné face à " + loserName);
        NomWin1V.setText(winnerName);
        NomLoss1V.setText(loserName);
    }
}
