package echec.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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


    @Override // Initialisation des boutons Controller pour savoir quel mode de jeux on choisi
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonController = new ButtonController();
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
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/pageJvJ.fxml"));
            Stage stage = (Stage) boutonJvJ.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            JeuController jeuController = loader.getController();
            jeuController.setPlayerNamesJvJ(joueur1Prenom.getText(),joueur1Nom.getText(), joueur2Prenom.getText(),joueur2Nom.getText());
            stage.setScene(scene);
            stage.setMinHeight(1080);
            stage.setMinWidth(1920);
            stage.setHeight(1080);
            stage.setWidth(1920);
            stage.centerOnScreen();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void bouttonJvB(ActionEvent actionEvent) {
        addCSV();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/pagebot.fxml"));
            Stage stage = (Stage) boutonJvB.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            JeuController jeuController = loader.getController();
            jeuController.setPlayerNamesJvB(joueur1Prenom.getText(),joueur1Nom.getText());
            stage.setScene(scene);
            stage.setMinHeight(1080);
            stage.setMinWidth(1920);
            stage.setHeight(1080);
            stage.setWidth(1920);
            stage.centerOnScreen();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void addCSV() {
        String joueur1PrenomText = joueur1Prenom.getText();
        String joueur1NomText = joueur1Nom.getText();
        if (joueur2Prenom == null) {
            if (!isPlayerInCsv(joueur1PrenomText, joueur1NomText)) {
                writeCsvFile(joueur1PrenomText, joueur1NomText, 0, 0);
            }
        } else {
            String joueur2PrenomText = joueur2Prenom.getText();
            String joueur2NomText = joueur2Nom.getText();
            if (!isPlayerInCsv(joueur1PrenomText, joueur1NomText)) {
                writeCsvFile(joueur1PrenomText, joueur1NomText, 0, 0);
            }
            if (!isPlayerInCsv(joueur2PrenomText, joueur2NomText)) {
                writeCsvFile(joueur2PrenomText, joueur2NomText, 0, 0);
            }
        }
    }

    private boolean isPlayerInCsv(String prenom, String nom) {
        String fileName = "joueurs.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    if (parts[0].equals(prenom) && parts[1].equals(nom)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void writeCsvFile(String joueurPrenom, String joueurNom, int matches, int victories) {
        String fileName = "joueurs.csv";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            if (new File(fileName).length() == 0) {
                writer.append("Prénom,Nom,Nombre de matchs,Nombre de victoires\n");
            }
            writer.append(joueurPrenom)
                    .append(',')
                    .append(joueurNom)
                    .append(',')
                    .append(String.valueOf(matches))
                    .append(',')
                    .append(String.valueOf(victories))
                    .append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateMatches(String prenom, String nom) {
        String fileName = "joueurs.csv";
        List<String[]> players = new ArrayList<>();

        // Read the file and store the data
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    if (parts[0].equals(prenom) && parts[1].equals(nom)) {
                        int matches = Integer.parseInt(parts[2]) + 1; // Increment matches
                        parts[2] = String.valueOf(matches);
                    }
                    players.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the data back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] player : players) {
                writer.append(player[0])
                        .append(',')
                        .append(player[1])
                        .append(',')
                        .append(player[2])
                        .append(',')
                        .append(player[3])
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateVictories(String prenom, String nom) {
        String fileName = "joueurs.csv";
        List<String[]> players = new ArrayList<>();

        // Read the file and store the data
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    if (parts[0].equals(prenom) && parts[1].equals(nom)) {
                        int victories = Integer.parseInt(parts[3]) + 1; // Increment victories
                        parts[3] = String.valueOf(victories);
                    }
                    players.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the data back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] player : players) {
                writer.append(player[0])
                        .append(',')
                        .append(player[1])
                        .append(',')
                        .append(player[2])
                        .append(',')
                        .append(player[3])
                        .append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

