package echec.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ButtonController {

    private LoginController loginController = new LoginController();


    /**
     * Initialise le bouton JvJ (Joueur contre Joueur) après la connexion.
     * Ce bouton redirige vers la page JvJ lorsqu'il est appuyé.
     *
     * @param bouton Le bouton JvJ à initialiser.
     * @author Baptiste Gaston
     */
    public void initButtonJvJ(Button bouton) {
        bouton.setOnMouseClicked(actionEvent -> {
            changeScene("view/pageJvJ.fxml", bouton);
        });
    }


    /**
     * Initialise le bouton JvJLogin (Joueur contre Joueur - Connexion).
     * Ce bouton redirige vers la page de connexion pour les joueurs lorsqu'il est appuyé.
     *
     * @param bouton Le bouton JvJLogin à initialiser.
     * @author Baptiste Gaston
     */
    public void initButtonLogJvJ(Button bouton) {
        bouton.setOnMouseClicked(actionEvent -> changeScene("view/login.fxml", bouton));
    }

    /**
     * Initialise le bouton JvBLogin (Joueur contre Bot - Connexion).
     * Ce bouton redirige vers la page de connexion pour le joueur contre le bot lorsqu'il est appuyé.
     *
     * @param bouton Le bouton JvBLogin à initialiser.
     * @author Baptiste Gaston
     */
    public void initButtonLogJvB(Button bouton) {
        bouton.setOnMouseClicked(actionEvent -> changeScene("view/login1J.fxml", bouton));
    }

    /**
     * Initialise le bouton JvB (Joueur contre Bot) après la connexion.
     * Ce bouton redirige vers la page JvB lorsqu'il est appuyé.
     *
     * @param bouton Le bouton JvB à initialiser.
     * @author Baptiste Gaston
     */
    public void initButtonJvB(Button bouton) {
        bouton.setOnMouseClicked(actionEvent -> changeScene("view/pagebot.fxml", bouton));
    }


    /**
     * Initialise le bouton Accueil.
     * Ce bouton redirige vers la page d'accueil lorsqu'il est appuyé.
     *
     * @param bouton Le bouton Accueil à initialiser.
     * @author Baptiste Gaston
     */
    public void initButtonAcc(Button bouton) {
        bouton.setOnMouseClicked(actionEvent -> changeScene("view/accueil.fxml", bouton));
    }

    /**
     * Initialise le bouton de fin.
     * Ce bouton redirige vers la page de fin lorsqu'il est appuyé.
     *
     * @param bouton Le bouton de fin à initialiser.
     * @author Alex Gonçalves
     */
    public void initButtonFin(Button bouton) {
        bouton.setOnMouseClicked(actionEvent -> changeScene("view/fin.fxml", bouton));
    }

    /**
     * Change la scène affichée dans la fenêtre actuelle.
     * Cette fonction permet de charger une nouvelle scène à partir d'un fichier FXML spécifié
     * et de l'afficher dans la fenêtre actuelle.
     *
     * @param sceneName Le nom du fichier FXML de la nouvelle scène à charger.
     * @param button Le bouton à partir duquel l'action de changement de scène est déclenchée.
     *               Ce bouton est utilisé pour obtenir la fenêtre actuelle.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement de la nouvelle scène.
     * @author Baptiste Gaston
     */
    public static void changeScene(String sceneName, Button button) {
        try {
            // Charger la nouvelle scène depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(EchecApp.class.getResource(sceneName));
            Scene newScene = new Scene(loader.load());

            // Obtenir le stage actuel depuis le bouton
            Stage currentStage = (Stage) button.getScene().getWindow();

            // Définir la nouvelle scène sur le stage actuel
            currentStage.setScene(newScene);
            currentStage.getScene().getRoot().setStyle("-fx-background-color: rgb(48, 46, 43);");

            // Définir la taille minimale en fonction de la scène
            if (sceneName.equals("view/login.fxml") || sceneName.equals("view/login1J.fxml")) {
                currentStage.setMinHeight(900);
                currentStage.setMinWidth(500);
                currentStage.setHeight(900);
                currentStage.setWidth(500);
            } else if (sceneName.equals("view/accueil.fxml")) {
                currentStage.setMinHeight(1080);
                currentStage.setMinWidth(1920);
                currentStage.setHeight(1080);
                currentStage.setWidth(1920);
            } else {
                currentStage.setMinHeight(1080);
                currentStage.setMinWidth(1920);
                currentStage.setHeight(1080);
                currentStage.setWidth(1920);
            }
            currentStage.centerOnScreen();
            currentStage.toFront();

            // Afficher le stage avec la nouvelle scène
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
