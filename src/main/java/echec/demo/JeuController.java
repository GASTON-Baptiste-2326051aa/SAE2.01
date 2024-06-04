package echec.demo;

import javafx.fxml.FXML;

import echec.Pions.*;

import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class JeuController implements Initializable {

    @FXML
    private Label labelTimerJ1;
    @FXML
    private Label labelTimerJ2;

    private Timer timerJ1 = new Timer();
    private Timer timerJ2 = new Timer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation de la grille");


        timerJoueur1On();


    } // Marche avec le bouton JOUER

    public void deplacementJoueur1(){

        timerJoueur1Off();
        timerJoueur2On();
    }

    public void deplacementJoueur2(){

        timerJoueur2Off();
        timerJoueur1On();
    }

    public void comparePions(Pions pion1, Pions pion2){
        if(pion1.getPosX() == pion2.getPosX() && pion1.getPosY() == pion2.getPosY() && !(pion1.getCouleur().equals(pion2.getCouleur()))){
            pion2.setEtat(false);
        }
    }

    public void situationRoi(Roi roi){
        //TODO
    }

    public void timerJoueur1On(){
        timerJ2.scheduleAtFixedRate(new TimerTask() {
            int i = 300;
            @Override
            public void run() {
                labelTimerJ2.setText(String.valueOf(i) + "secondes");
                i--;

                if (i < 0) {
                    timerJ2.cancel();
                    labelTimerJ2.setText("Time Over");
                }
            }
        }, 0, 100);

        timerJoueur2Off();
    }

    public void timerJoueur2On(){
        timerJ2.scheduleAtFixedRate(new TimerTask() {
            int i = 300;
            @Override
            public void run() {
                labelTimerJ1.setText(String.valueOf(i) + "secondes");
                i--;

                if (i < 0) {
                    timerJ1.cancel();
                    labelTimerJ1.setText("Time Over");
                }
            }
        }, 0, 100);

        timerJoueur1Off();
    }
    public void timerJoueur1Off(){
        timerJ1.cancel();
    }
    public void timerJoueur2Off(){
        timerJ2.cancel();
    }

}