package echec.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class BotVsController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private Button boutonAcc;
    @FXML
    private ComboBox<Integer> timerBox;
    @FXML
    private Label timerLabel;
    @FXML
    private Label timerLabel2;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;


    private ButtonController buttonController;

    private Timer timer1;
    private Timer timer2;

    private TimerTask timerTask1;
    private TimerTask timerTask2;

    private int tempsRestant1;
    private int tempsRestant2;

    private boolean isTimer1Running = false;
    private boolean isTimer2Running = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonController = new ButtonController();
        buttonController.initButtonAcc(boutonAcc);

        timerBox.getItems().addAll(1, 5, 10, 15);
        timerBox.getSelectionModel().selectFirst();

        timerBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            String timeText = newValue + " Minutes";
            timerLabel.setText(timeText);
            timerLabel2.setText(timeText);
        });

        startButton.setOnAction(e -> startTimer1());
        pauseButton.setOnAction(e -> toggleTimers());
    }

    private void startTimer1() { //on lance le timer 1 a partir de la durée selectionner dans la ComboBox
        Integer selectedValue = timerBox.getValue();
        if (selectedValue == null) {
            throw new IllegalStateException("Timer value is not selected.");
        }

        int tempsSelectionne = selectedValue * 60;
        tempsRestant1 = tempsSelectionne;
        tempsRestant2 = tempsSelectionne;

        timer1 = new Timer();
        timerTask1 = createTimerTask(timerLabel, () -> --tempsRestant1);

        timer1.scheduleAtFixedRate(timerTask1, 1000, 1000);
        isTimer1Running = true;
    }

    private void toggleTimers() { // fonction pour mettre en pausse un timer et reprendre l'autre
        if (isTimer1Running) {
            timer1.cancel();
            isTimer1Running = false;
            startTimer2();
        } else if (isTimer2Running) {
            timer2.cancel();
            isTimer2Running = false;
            startTimer1();
        }
    }

    private void startTimer2() { //on lance le timer 2 a partir de la durée selectionner dans la ComboBox
        timer2 = new Timer();
        timerTask2 = createTimerTask(timerLabel2, () -> --tempsRestant2);

        timer2.scheduleAtFixedRate(timerTask2, 1000, 1000);
        isTimer2Running = true;
    }

    private TimerTask createTimerTask(Label timerLabel, Runnable updateRemainingTime) { //creation de la task pour le timer
        return new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    updateRemainingTime.run();
                    updateTimer(timerLabel);
                });
            }
        };
    }

    private void updateTimer(Label timerLabel) { //ici on va update le timer quand on change de valeur dans la combo box et quand il va etre a 0 on l'arrete.
        int tempsRestant = (timerLabel == this.timerLabel) ? tempsRestant1 : tempsRestant2;
        if (tempsRestant <= 0) {
            timerLabel.setText("00:00");
            if (timerLabel == this.timerLabel && timer1 != null) {
                timer1.cancel();
            } else if (timerLabel == this.timerLabel2 && timer2 != null) {
                timer2.cancel();
            }
        } else {
            int minutes = tempsRestant / 60;
            int seconds = tempsRestant % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        }
    }
}
