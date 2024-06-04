package echec.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ButtonController {

    public void initButton1(Button bouton) {

        bouton.setOnMouseClicked(actionEvent -> {
            changeScene("view/pageJvJ.fxml", bouton);
        });
    }
    public void initButton2(Button bouton) {
        bouton.setOnMouseClicked(actionEvent -> {
            changeScene("view/pagebot.fxml", bouton);
        });
    }

    public void initButton3(Button bouton) {
        bouton.setOnMouseClicked(actionEvent -> {
            changeScene("view/accueil.fxml", bouton);
        });
    }

    public void changeScene(String sceneName, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(EchecApp.class.getResource(sceneName));
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.getScene().getRoot().setStyle("-fx-background-color: rgb(48, 46, 43);");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}