package echec.demo;

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

    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonController = new ButtonController();
        buttonController.initButton3(bouton3);
        timerBox.getItems().addAll(1,5,10,15);
        timerBox.getSelectionModel().selectFirst();
    }

    timerBox.valueProperty().addListener((observable, oldValue, NewValue) ->{
        timerLabel.setTexte(NewValue + "Minute");
        timerLabel2.setTexte(NewValue + "Minute");
    });

    Button startButton = new Button("Jouer");
    startButton.setOnAction(e -> startTimers());

    private void startTimers() {
        int tempsSelectionner = timerBox1.getValue();

        timer1 = new Timer();
        timer2 = new Timer();

        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Fin temps");
            }
        }, tempsSelectionner * 60 * 1000);

        timer1.schedule(new TimerTask(){
            @Override
            public void run() {
                System.out.println("Fin temps");
            }
        }, tempsSelectionner * 60 * 1000);
    }
}
