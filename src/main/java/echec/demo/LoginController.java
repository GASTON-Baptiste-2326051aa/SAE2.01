package echec.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button boutonJvJ;

    @FXML
    private Button boutonJvB;

    private ButtonController buttonController;

    @FXML
    private TextField joueur1Prenom;
    @FXML
    private TextField joueur1Nom;
    @FXML
    private TextField joueur2Prenom;
    @FXML
    private TextField joueur2Nom;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonController = new ButtonController();
        if (boutonJvJ != null) {
            buttonController.initButtonJvJ(boutonJvJ);
        }
        if (boutonJvB != null) {
            buttonController.initButtonJvB(boutonJvB);
        }


    }
}
