package echec.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

    private void startTimer1() {
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

    private void toggleTimers() {
        if (isTimer1Running) {
            timer1.cancel();
            isTimer1Running = false;
            startTimer2();
        } else if (isTimer2Running) {
            timer2.cancel();
            isTimer2Running = false;
            resumeTimer1();
        }
    }

    private void startTimer2() {
        timer2 = new Timer();
        timerTask2 = createTimerTask(timerLabel2, () -> --tempsRestant2);

        timer2.scheduleAtFixedRate(timerTask2, 1000, 1000);
        isTimer2Running = true;
    }

    private void resumeTimer1() {
        timer1 = new Timer();
        timerTask1 = createTimerTask(timerLabel, () -> --tempsRestant1);

        timer1.scheduleAtFixedRate(timerTask1, 1000, 1000);
        isTimer1Running = true;
    }

    private TimerTask createTimerTask(Label timerLabel, Runnable updateRemainingTime) {
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

    private void updateTimer(Label timerLabel) {
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
