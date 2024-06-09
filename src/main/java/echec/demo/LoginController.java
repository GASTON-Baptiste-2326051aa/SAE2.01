package echec.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Classe Controller pour le Login dans le jeux d'echec.
 * Cette classe gère certains bouton pour aller vers d'autres pages, la sauvegarde des Noms, Prénoms, parties gagnées et les parties jouées par chaque joueur.
**/

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

    private static final String CSV_FILE_PATH = "src/main/resources/joueurs.csv";
    /**
     * Initialise les boutons du contrôleur pour déterminer le mode de jeu sélectionné.
     * Cette méthode est appelée lors de l'initialisation de la classe.
     *
     * @param url            L'URL utilisée pour initialiser le contrôleur (non utilisé dans cette implémentation).
     * @param resourceBundle Le ResourceBundle utilisé pour localiser l'objet racine (non utilisé dans cette implémentation).
     */
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

    /**
     * @authors Alex GONCALVES RODRIGUES Baptiste Gaston
     *
     * @params actionEvent, quand le bouton va âtre cliqué, le code va âtre exécuté
     *
     * Ici, quand le bouton va âtre cliqué, la scène va âtre changée par celle du mode Joueur vs Joueur.
     * Et on a aussi juste avant la sauvegarde des informations des joueurs dans un fichier csv.
     **/
    @FXML
    private void bouttonJvJ(ActionEvent actionEvent) {
        addCSV();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/pageJvJ.fxml"));
            Stage stage = (Stage) boutonJvJ.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            JeuController jeuController = loader.getController();
            jeuController.setPlayerNamesJvJ(joueur1Prenom.getText(), joueur1Nom.getText(), joueur2Prenom.getText(), joueur2Nom.getText());
            stage.setScene(scene);
            stage.setMinHeight(1080);
            stage.setMinWidth(1920);
            stage.setHeight(1080);
            stage.setWidth(1920);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @authors Alex GONCALVES RODRIGUES, Baptiste Gaston
     *
     * @params actionEvent, quand le bouton va être cliqué, le code va être exécuté
     *
     * Ici, quand le bouton va être cliqué, la scène va être changée par celle du mode Joueur vs Bot..
     * Et on a aussi juste avant la sauvegarde des informations des joueurs dans un fichier csv.
     **/
    @FXML
    private void bouttonJvB(ActionEvent actionEvent) {
        addCSV();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/pagebot.fxml"));
            Stage stage = (Stage) boutonJvB.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            BotVsController botVsController = loader.getController();
            botVsController.setPlayerNamesJvB(joueur1Prenom.getText(), joueur1Nom.getText());
            stage.setScene(scene);
            stage.setMinHeight(1080);
            stage.setMinWidth(1920);
            stage.setHeight(1080);
            stage.setWidth(1920);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute des entrées au fichier CSV.
     * Crée un fichier CSV avec des en-têtes si celui-ci n'existe pas encore.
     * Vérifie et ajoute les joueurs au fichier CSV s'ils n'existent pas déjà.
     *
     * @author Baptiste Gaston
     */
    private void addCSV() {
        // Create CSV file with headers if it doesn't exist
        if (Files.notExists(Paths.get(CSV_FILE_PATH))) {
            writeCsvFile("Prénom", "Nom", 0, 0); // Ajoute les en-têtes des colonnes
        }

        // Ajoute les joueurs s'ils n'existent pas déjà
        String joueur1PrenomText = joueur1Prenom.getText();
        String joueur1NomText = joueur1Nom.getText();
        if (joueur2Prenom == null || joueur2Prenom.getText().isEmpty()) {
            if (!isPlayerInCsv(joueur1PrenomText, joueur1NomText)) {
                writeCsvFile(joueur1PrenomText, joueur1NomText, 0, 0);
            }
            if (!isPlayerInCsv("BOT","David")) {
                writeCsvFile("BOT", "David", 0, 0);
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


    /**
     * Vérifie si un joueur est déjà présent dans le fichier CSV.
     *
     * @param prenom Le prénom du joueur à vérifier.
     * @param nom    Le nom du joueur à vérifier.
     * @return true si le joueur est présent dans le fichier CSV, sinon false.
     * @throws IOException En cas d'erreur lors de la lecture du fichier CSV.
     * @author Baptiste Gaston
     */
    private boolean isPlayerInCsv(String prenom, String nom) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
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
    /**
     * Écrit une entrée dans un fichier CSV contenant les informations d'un joueur, y compris son prénom, son nom, le nombre de matchs et le nombre de victoires.
     *
     * @param joueurPrenom Le prénom du joueur.
     * @param joueurNom    Le nom du joueur.
     * @param matches      Le nombre de matchs joués par le joueur.
     * @param victories    Le nombre de victoires remportées par le joueur.
     * @throws IOException En cas d'erreur lors de l'écriture dans le fichier CSV.
     * @author Baptiste Gaston
     */
    private void writeCsvFile(String joueurPrenom, String joueurNom, int matches, int victories) {
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {
            if (new File(CSV_FILE_PATH).length() == 0) {
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

    /**
     * Met à jour le nombre de matchs joués par un joueur dans le fichier CSV.
     *
     * @param prenom Le prénom du joueur dont les matchs doivent être mis à jour.
     * @param nom    Le nom du joueur dont les matchs doivent être mis à jour.
     * @throws IOException En cas d'erreur lors de la lecture ou de l'écriture dans le fichier CSV.
     * @throws NumberFormatException En cas d'erreur lors de la conversion du nombre de matchs en entier.
     * @author Baptiste Gaston
     */
    public void updateMatches(String prenom, String nom) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(prenom) && parts[1].equals(nom)) {
                    int matches = Integer.parseInt(parts[2]) + 1; // Increment matches
                    parts[2] = String.valueOf(matches);
                    line = String.join(",", parts);
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (String updatedLine : updatedLines) {
                writer.append(updatedLine).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour le nombre de victoires remportées par un joueur dans le fichier CSV.
     *
     * @param prenom Le prénom du joueur dont les victoires doivent être mises à jour.
     * @param nom    Le nom du joueur dont les victoires doivent être mises à jour.
     * @throws IOException En cas d'erreur lors de la lecture ou de l'écriture dans le fichier CSV.
     * @throws NumberFormatException En cas d'erreur lors de la conversion du nombre de victoires en entier.
     * @author Baptiste Gaston
     */
    public void updateVictories(String prenom, String nom) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(prenom) && parts[1].equals(nom)) {
                    int victories = Integer.parseInt(parts[3]) + 1; // Increment victories
                    parts[3] = String.valueOf(victories);
                    line = String.join(",", parts);
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (String updatedLine : updatedLines) {
                writer.append(updatedLine).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
