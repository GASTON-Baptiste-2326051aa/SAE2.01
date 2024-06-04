package echec.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class UnVController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private Button bouton3;
    @FXML
    private ComboBox<Integer> timerBox;
    @FXML
    private Label timerLabel;
    @FXML
    private Label timerLabel2;

    private ButtonController buttonController;

    private Timer timer1;
    private Timer timer2;

    private int tempsRestant1;
    private int tempsRestant2;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonController = new ButtonController();
        buttonController.initButton3(bouton3);

        timerBox.getItems().addAll(1, 5, 10, 15);
        timerBox.getSelectionModel().selectFirst();

        timerBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            String timeText = newValue + "Minute";
            timerLabel.setText(timeText);
            timerLabel2.setText(timeText);
        });

        Button startButton = new Button("Jouer");
        startButton.setOnAction(e -> startTimers());
    }

    private void startTimers() {
        int tempsSelectionne = timerBox.getValue() * 60;

        tempsRestant1 = tempsSelectionne;
        tempsRestant2 = tempsSelectionne;

        timer1 = new Timer();
        timer2 = new Timer();

        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateTimer(timerLabel, --tempsRestant1));
            }
        }, 1000, 1000);

        timer1.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                Platform.runLater(() -> updateTimer(timerLabel, --tempsRestant2));
            }
        }, 1000, 1000);
    }

    private void updateTimer(Label timerLabel, int tempsRestant1) {
        if (tempsRestant1 <= 0) {
            timerLabel.setText("");
            timer1.cancel();
            timer2.cancel();
        }else {
            int minutes = tempsRestant1 / 60;
            int seconds = tempsRestant1 % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        }
    }
}
