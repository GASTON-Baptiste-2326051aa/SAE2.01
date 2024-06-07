package echec.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button boutonJvJ;

    @FXML
    private Button boutonJvB;

    private ButtonController buttonController;
    private UnVController unVController;

    @FXML
    private TextField joueur1Prenom;
    @FXML
    private TextField joueur1Nom;
    @FXML
    private TextField joueur2Prenom;
    @FXML
    private TextField joueur2Nom;


    @Override // Initialisation des boutons Controller pour savoir quel mode de jeux on choisi
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonController = new ButtonController();
        unVController = new UnVController();
        if (boutonJvJ != null) {
            buttonController.initButtonJvJ(boutonJvJ);
        }
        if (boutonJvB != null) {
            buttonController.initButtonJvB(boutonJvB);
        }


    }

    @FXML
    private void bouttonJvJ(ActionEvent actionEvent) {
        addCSV();
        unVController.saveName(joueur1Prenom,joueur2Prenom,joueur1Nom,joueur2Nom);
    }
    private void addCSV() { // stockage des Noms et Prenoms des joueurs dans un fichier csv
        String joueur1PrenomText = joueur1Prenom.getText();
        String joueur1NomText = joueur1Nom.getText();
        String joueur2PrenomText = joueur2Prenom.getText();
        String joueur2NomText = joueur2Nom.getText();
        writeCsvFile(joueur1PrenomText, joueur1NomText, joueur2PrenomText, joueur2NomText);
    }



    private void writeCsvFile(String joueur1Prenom, String joueur1Nom, String joueur2Prenom, String joueur2Nom) { // ecriture des noms dans le fichier csv
        String fileName = "joueurs.csv";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(joueur1Prenom)
                    .append(',')
                    .append(joueur1Nom)
                    .append(',')
                    .append('\n')
                    .append(joueur2Prenom)
                    .append(',')
                    .append(joueur2Nom)
                    .append(',')
                    .append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

