package echec.demo;

import echec.Pions.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;


public class EchecApp extends Application {

    private static Scene scene;

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
                    System.out.print("  .  ");
                } else {
                    System.out.print(" " + pion + " ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(EchecApp.class.getResource("view/accueil.fxml"));
        scene = new Scene(loader.load());
        scene.getRoot().setStyle("-fx-background-color: rgb(48, 46, 43);");
        initMatrice();
        afficheMatrice(this.matriceJeu);
        stage.setMinHeight(1080);
        stage.setMinWidth(1920);
        stage.setTitle("Echec");
        stage.setScene(scene);
        stage.show();
    }
}