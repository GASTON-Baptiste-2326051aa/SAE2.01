package echec.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ButtonController {

    public void initButton(Button bouton1, Button bouton2) {

        bouton1.setOnMouseClicked(actionEvent -> {
            changeScene("view/plateau.fxml", bouton1);
        });
        bouton2.setOnMouseClicked(actionEvent -> {
            changeScene("view/pageJvJ.fxml", bouton2);
        });
    }

    public void changeScene(String sceneName, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(EchecApp.class.getResource(sceneName));
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}