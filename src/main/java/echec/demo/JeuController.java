/*package echec.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;

import javax.swing.text.PasswordView;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class JeuController implements Initializable {

    @FXML
    private Label timer;

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

    public void comparePionsJ1toJ2(){
        if(pion1.getPosX == pion2.getPosX && pion1.getPosY == pion2.getPosY && (!(pion1.getCouleur().equals(pion2.getCouleur()){
            //Si pion 2 est un roi : fin de la partie.
            pion2.setEtat == false;
        }
    }

    public void comparePionsJ2toJ1(){
        if(pion1.getPosX == pion2.getPosX && pion1.getPosY == pion2.getPosY && !(pion1.getCouleur().equals(pion2.getCouleur())){
            //Si pion 1 est un roi : fin de la partie.
            pion1.setEtat == false;
        }
    }

    public void timerJoueur1On(){
        // TODO
    }

    public void timerJoueur2On(){
        // TODO
    }
    public void timerJoueur1Off(){
        // TODO
    }
    public void timerJoueur2Off(){
        // TODO
    }

}

 */