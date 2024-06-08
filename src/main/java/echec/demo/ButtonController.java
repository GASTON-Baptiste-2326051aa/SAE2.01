package echec.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ButtonController {

    private LoginController loginController = new LoginController();

    public void initButtonJvJ(Button bouton) { //initilisation du bouton JvJ(apres login) qui renvoi a la page JvJ quand il est appuyé
        bouton.setOnMouseClicked(actionEvent -> {
            changeScene("view/pageJvJ.fxml", bouton);
        });
    }

    public void initButtonLogJvJ(Button bouton) { //initilisation du bouton JvJLogin qui renvoi a la page Login pour les joueurs quand il est appuyé
        bouton.setOnMouseClicked(actionEvent -> changeScene("view/login.fxml", bouton));
    }

    public void initButtonLogJvB(Button bouton) { //initilisation du bouton JvBLogin qui renvoi a la page Login pour le joueurs contre le bot quand il est appuyé
        bouton.setOnMouseClicked(actionEvent -> changeScene("view/login1J.fxml", bouton));
    }

    public void initButtonJvB(Button bouton) { //initilisation du bouton JvB (apres login) qui renvoi a la page JvB quand il est appuyé
        bouton.setOnMouseClicked(actionEvent -> changeScene("view/pagebot.fxml", bouton));

    }

    public void initButtonAcc(Button bouton) { //initilisation du bouton Accueil qui renvoi a la page d'accueil quand il est appuyé
        bouton.setOnMouseClicked(actionEvent -> changeScene("view/accueil.fxml", bouton));
    }
    public void initButtonFin(Button bouton){
        bouton.setOnMouseClicked(actionEvent -> changeScene("view/fin.fxml", bouton));
    }

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
