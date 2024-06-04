package echec.demo;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;

import echec.Pions.*;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
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

    Position positionDep= new Position();
    Position positionFin= new Position();

    private ArrayList<ArrayList<Pions>> matriceJeu;

    public void initMatrice() {
        matriceJeu = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            matriceJeu.add(new ArrayList<>(8));
            for (int j = 0; j < 8; j++) {
                matriceJeu.get(i).add(null);
            }
        }

        String[] colonnes = {"a", "b", "c", "d", "e", "f", "g", "h"};

        // Pions noirs
        for (int i = 0; i < 8; i++) {
            matriceJeu.get(1).set(i, new Pion("noir", colonnes[i], 2, "img/pionNoir.png"));
        }
        matriceJeu.get(0).set(0, new Tour("noir", "a", 1, "img/tourNoir.png"));
        matriceJeu.get(0).set(7, new Tour("noir", "h", 1, "img/tourNoir.png"));
        matriceJeu.get(0).set(1, new Cavalier("noir", "b", 1, "img/cavalier.png"));
        matriceJeu.get(0).set(6, new Cavalier("noir", "g", 1, "img/cavalier.png"));
        matriceJeu.get(0).set(2, new Fou("noir", "c", 1, "img/fouNoir.png"));
        matriceJeu.get(0).set(5, new Fou("noir", "f", 1, "img/fouNoir.png"));
        matriceJeu.get(0).set(3, new Reine("noir", "d", 1, "img/reineNoire.png"));
        matriceJeu.get(0).set(4, new Roi("noir", "e", 1, "img/roiNoir.png"));

        // Pions blancs
        for (int i = 0; i < 8; i++) {
            matriceJeu.get(6).set(i, new Pion("blanc", colonnes[i], 7, "img/pionBlanc.png"));
        }
        matriceJeu.get(7).set(0, new Tour("blanc", "a", 8, "img/tourBlanche.png"));
        matriceJeu.get(7).set(7, new Tour("blanc", "h", 8, "img/tourBlanche.png"));
        matriceJeu.get(7).set(1, new Cavalier("blanc", "b", 8, "img/cavalierBlanc.png"));
        matriceJeu.get(7).set(6, new Cavalier("blanc", "g", 8, "img/cavalierBlanc.png"));
        matriceJeu.get(7).set(2, new Fou("blanc", "c", 8, "img/fouBlanc.png"));
        matriceJeu.get(7).set(5, new Fou("blanc", "f", 8, "img/fouBlanc.png"));
        matriceJeu.get(7).set(3, new Reine("blanc", "d", 8, "img/reineBlanche.png"));
        matriceJeu.get(7).set(4, new Roi("blanc", "e", 8, "img/roiBlanc.png"));
    }

    public void afficheMatrice(ArrayList<ArrayList<Pions>> matrice){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pions pion = matriceJeu.get(i).get(j);
                if (pion == null) {
                    System.out.print("  case vide  ");
                } else {
                    System.out.print(" " + pion + " ");
                }
            }
            System.out.println();
        }
    }

    public void afficheMatriceAvecPlateau(ArrayList<ArrayList<Pions>> matrice){
        Position position = new Position();
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                Pions pion = matriceJeu.get(ligne).get(colonne);
                if (pion == null) {
                    continue;
                } else {
                    int posX = position.conversionLettreInt(pion.getPosX());
                    String cheminImage = pion.getUrl();
                    ImageView imageView = new ImageView(cheminImage);
                    imageView.setFitHeight(100.0);
                    imageView.setFitWidth(100.0);
                    plateau.add(imageView, posX, pion.getPosY()-1);
                }
            }
            System.out.println();
        }
    }

    public void clic(){

        plateau.setOnMouseClicked(e -> {
            if (this.positionDep.getX() < 0 ){
                this.positionDep.setX((int)e.getX());
                this.positionDep.setY((int)e.getY());
                this.positionDep.conversion(this.positionDep.getX(), this.positionDep.getY());
            }
            else{
                this.positionFin.setX((int)e.getX());
                this.positionFin.setY((int)e.getY());
                this.positionFin.conversion(this.positionFin.getX(), this.positionFin.getY());
            }

            if ( this.positionDep.getI() >= 0 && this.positionDep.getJ() >= 0 && this.positionFin.getI() >= 0 && this.positionFin.getI() >= 0){

                System.out.println( this.positionDep.getI() + " " + this.positionDep.getJ());

                Pions tmp = this.matriceJeu.get(positionDep.getI()).get(positionDep.getJ());
                Pions tmp2 =this.matriceJeu.get(positionFin.getI()).get(positionFin.getJ());
                //System.out.println(tmp.toString() + tmp2.toString());

                this.matriceJeu.get(positionDep.getI()).set(positionDep.getJ(), tmp2);
                this.matriceJeu.get(positionFin.getI()).set(positionFin.getJ(), tmp);

                System.out.println(this.matriceJeu);

                afficheMatriceAvecPlateau(this.matriceJeu);


                positionDep.reset();
                positionFin.reset();
            }

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation de la grille");
        initMatrice();
        System.out.println("Boucle?");
        //afficheMatriceAvecPlateau(this.matriceJeu);
        clic();

        //timerJoueur1On();


    } // Marche avec le bouton JOUER





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