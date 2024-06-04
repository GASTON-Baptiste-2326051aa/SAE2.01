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
    @FXML
    private GridPane plateau;

    private Timer timerJ1 = new Timer();
    int dernierTimerJ1 = 300;
    int dernierTimerJ2 = 300;
    private Timer timerJ2 = new Timer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation de la grille");


        timerJoueur1On();


    } // Marche avec le bouton JOUER

    public void selectionCase(){



    }

    public void deplacement(){



    }

    /*public void deplacementJoueur1(){


        timerJoueur1Off();
        timerJoueur2On();
    }

    public void deplacementJoueur2(){

        timerJoueur2Off();
        timerJoueur1On();
    }*/

    /*
     * @author Manon VERHILLE
     *
     * @return Le nouvel état du pion2 si le pion1 vient sur sa case.
     *
     * @version 1.0
     * */
    public void comparePions(Pions pion1, Pions pion2){
        if(pion1.getPosX() == pion2.getPosX() && pion1.getPosY() == pion2.getPosY() && !(pion1.getCouleur().equals(pion2.getCouleur()))){
            pion2.setEtat(false);
        }
    }

    public void situationRoi(Roi roi){
        //TODO
    }

    /*
     * @author Manon VERHILLE
     *
     * @return Le temps restant au J1 pour effectuer un déplacement.
     *
     * @version 1.1
     * */
    public void timerJoueur1On(){
        timerJ1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                labelTimerJ1.setText(String.valueOf(dernierTimerJ1) + " secondes");
                dernierTimerJ1--;

                if (dernierTimerJ1 < 0) {
                    timerJ1.cancel();
                    labelTimerJ1.setText("Plus de déplacement possible");
                }
            }
        }, 0, 1000);

        timerJoueur2Off();
    }

    /*
     * @author Manon VERHILLE
     *
     * @return Le temps restant au J2 pour effectuer un déplacement.
     *
     * @version 1.1
     * */
    public void timerJoueur2On(){
        timerJ2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                labelTimerJ2.setText(String.valueOf(dernierTimerJ2) + " secondes");
                dernierTimerJ2--;

                if (dernierTimerJ2 < 0) {
                    timerJ2.cancel();
                    labelTimerJ2.setText("Plus de déplacement possible");
                }
            }
        }, 0, 1000);

        timerJoueur1Off();
    }

    /*
     * @author Manon VERHILLE
     *
     * @return L'arrêt du compte à rebours du J1 et stockage de ce temps dans une variable dernierTimerJ1.
     *
     * @version 1.0
     * */
    public void timerJoueur1Off(){
        timerJ1.cancel();
    }

    /*
     * @author Manon VERHILLE
     *
     * @return L'arrêt du compte à rebours du J2 et stockage de ce temps dans une variable dernierTimerJ2.
     *
     * @version 1.0
     * */
    public void timerJoueur2Off(){
        timerJ2.cancel();
    }

}