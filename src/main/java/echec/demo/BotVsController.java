package echec.demo;

import echec.Pions.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BotVsController implements Initializable {
    @FXML
    private GridPane plateau;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label j1;
    @FXML
    private Label j2 = new Label("BOT");
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
    private Button boutonFin;

    private Position positionDep = new Position();
    private Position positionFin = new Position();
    private Position positionDepBot = new Position();
    private Position positionFinBot = new Position();

    private boolean peutJouerJ1;
    private boolean peutJouerJ2;

    private ArrayList<ArrayList<Pions>> matriceJeu;

    private ButtonController buttonController;
    private LoginController loginController;

    public void setPlayerNamesJvB(String player1Prenom,String player1Nom){ //fonction permettant de set le nom du joueur dans le mode Joueur vs Bot
        j1.setText(player1Prenom + " " + player1Nom);
    }

    public void Findejeu(String winnerName, String loserName) throws IOException { //fonction permettant qu'a la fin du jeu, une fenetre fin du jeu pop.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/fin.fxml"));
        Stage fin = (Stage) boutonFin.getScene().getWindow();
        Scene scene;
        scene = new Scene(loader.load());
        FinController finController = loader.getController();
        finController.setPlayerNames(winnerName, loserName);
        fin.setScene(scene);
        fin.setMinHeight(900);
        fin.setMinWidth(500);
        fin.setHeight(900);
        fin.setWidth(500);
        fin.centerOnScreen();

        startButton.setDisable(false); // pertmet de re-activé le bouton apres la fin du jeu
    }

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
            matriceJeu.get(1).set(i, new Pion("noir", colonnes[i], 7, "img/pionNoir.png"));
        }
        matriceJeu.get(0).set(0, new Tour("noir", "a", 8, "img/tourNoir.png"));
        matriceJeu.get(0).set(7, new Tour("noir", "h", 8, "img/tourNoir.png"));
        matriceJeu.get(0).set(1, new Cavalier("noir", "b", 8, "img/cavalier.png"));
        matriceJeu.get(0).set(6, new Cavalier("noir", "g", 8, "img/cavalier.png"));
        matriceJeu.get(0).set(2, new Fou("noir", "c", 8, "img/fouNoir.png"));
        matriceJeu.get(0).set(5, new Fou("noir", "f", 8, "img/fouNoir.png"));
        matriceJeu.get(0).set(3, new Reine("noir", "d", 8, "img/reineNoire.png"));
        matriceJeu.get(0).set(4, new Roi("noir", "e", 8, "img/roiNoir.png"));

        // Pions blancs
        for (int i = 0; i < 8; i++) {
            matriceJeu.get(6).set(i, new Pion("blanc", colonnes[i], 2, "img/pionBlanc.png"));
        }
        matriceJeu.get(7).set(0, new Tour("blanc", "a", 1, "img/tourBlanche.png"));
        matriceJeu.get(7).set(7, new Tour("blanc", "h", 1, "img/tourBlanche.png"));
        matriceJeu.get(7).set(1, new Cavalier("blanc", "b", 1, "img/cavalierBlanc.png"));
        matriceJeu.get(7).set(6, new Cavalier("blanc", "g", 1, "img/cavalierBlanc.png"));
        matriceJeu.get(7).set(2, new Fou("blanc", "c", 1, "img/fouBlanc.png"));
        matriceJeu.get(7).set(5, new Fou("blanc", "f", 1, "img/fouBlanc.png"));
        matriceJeu.get(7).set(3, new Reine("blanc", "d", 1, "img/reineBlanche.png"));
        matriceJeu.get(7).set(4, new Roi("blanc", "e", 1, "img/roiBlanc.png"));
    }

    public void afficheMatriceAvecPlateau(ArrayList<ArrayList<Pions>> matrice) {
        plateau.getChildren().removeIf(node -> node instanceof ImageView);
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
                    plateau.add(imageView, posX, 8 - pion.getPosY());
                }
            }
        }
    }

    public void clic(){
        plateau.setOnMouseClicked(e -> {
            if (this.positionDep.getX() < 0){
                this.positionDep.setX((int)e.getX());
                this.positionDep.setY((int)e.getY());
                this.positionDep.conversion(this.positionDep.getX(), this.positionDep.getY());
                if (matriceJeu.get(positionDep.getI()).get(positionDep.getJ()) == null){
                    positionDep.reset();
                }
            }
            else if (this.positionFin.getX() < 0 && this.positionDep.getJ() >= 0){
                this.positionFin.setX((int)e.getX());
                this.positionFin.setY((int)e.getY());
                this.positionFin.conversion(this.positionFin.getX(), this.positionFin.getY());
            }
            jeu();
        });
    }

    public void jeuBot(){
        if(peutJouerJ2) {
            // Création d'une variable random : permet de choisir aléatoirement les coordonnées des cases.
            Random random = new Random();

            // Modification des valeurs X et Y des deux variables de position et conversion en coordonnées de matrice.
            this.positionDepBot.setX(random.nextInt(800));
            this.positionDepBot.setY(random.nextInt(800));
            this.positionDepBot.conversion(this.positionDepBot.getX(), this.positionDepBot.getY());

            this.positionFinBot.setX(random.nextInt(800));
            this.positionFinBot.setY(random.nextInt(800));
            this.positionFinBot.conversion(this.positionFinBot.getX(), this.positionFinBot.getY());

            // Création de deux pions.
            Pions pionDepBot = this.matriceJeu.get(positionDepBot.getI()).get(positionDepBot.getJ());
            Pions pionFinBot = this.matriceJeu.get(positionFinBot.getI()).get(positionFinBot.getJ());

            /* Si le pion de départ n'est pas null ET est de couleur noire,
             * ET que le pion de départ peut être déplacé,
             * ET que le pion de fin est de couleur différente,
             * ET que le pion de départ peut se déplacer dans la direction voulu.
             * */
            if (pionDepBot != null && pionDepBot.getCouleur().equals("noir")
                    && pionDepBot.peutDeplacer(pionDepBot.getPosY(), pionDepBot.getPosX(), 8 -positionFinBot.getI(), positionFinBot.conversionIntLettre(positionFinBot.getJ()))
                    && (comparePionsMemeCouleur(pionDepBot, pionFinBot))
                    && comparePionsDirection(pionDepBot, positionFinBot.getI(), positionFinBot.conversionIntLettre(positionFinBot.getJ()))) {
                // Nous pouvons à présent déplacer les pions.
                deplacementPieceBot(pionDepBot, pionFinBot);

                // Si le roi est en défaite :
                if (pionFinBot instanceof Roi) {
                    try {
                            Findejeu(j1.getText(),j2.getText());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }

                // Affichage de la matrice du jeu dans l'interface graphique.
                afficheMatriceAvecPlateau(matriceJeu);

                // Les positions de départ et de fin sont réinitialisées.
                positionDepBot.reset();
                positionFinBot.reset();

            } else { // Sinon, on reprend au début de la fonction.
                // Les positions de départ et de fin sont réinitialisées.
                positionDepBot.reset();
                positionFinBot.reset();
                jeuBot();
            }
        }
    }

    public void jeu(){
        if ( this.positionDep.getI() >= 0 && this.positionDep.getJ() >= 0 && this.positionFin.getI() >= 0 && this.positionFin.getJ() >= 0){
            Pions pionDep = this.matriceJeu.get(positionDep.getI()).get(positionDep.getJ());
            Pions pionFin =this.matriceJeu.get(positionFin.getI()).get(positionFin.getJ());

            if (peutJouerJ1 && Objects.equals(pionDep.getCouleur(), "blanc")
                    && pionDep != null
                    && pionDep.peutDeplacer(pionDep.getPosY(), pionDep.getPosX(), 8 - positionFin.getI() , positionFin.conversionIntLettre(positionFin.getJ()))
                    && (comparePionsMemeCouleur(pionDep, pionFin)) && comparePionsDirection(pionDep,positionFin.getI() ,positionFin.conversionIntLettre(positionFin.getJ()))) {
                    deplacementPiece(pionDep, pionFin);
                    if (pionFin instanceof Roi) try {
                        Findejeu(j2.getText(), j1.getText());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                afficheMatriceAvecPlateau(this.matriceJeu);
            }
            positionDep.reset();
            positionFin.reset();
            if(peutJouerJ2) jeuBot();
        }
    }

    public void deplacementPiece(Pions pionDep, Pions pionFin){
        if (pionFin != null){
            pionDep.setPosY(pionFin.getPosY());
            pionDep.setPosX(pionFin.getPosX());
        }
        else{
            // On soustrait 8 à la position de fin pour la convertir en coordonnées de matrice
            pionDep.setPosY(8-positionFin.getI());
            pionDep.setPosX(positionFin.conversionIntLettre(positionFin.getJ()));
        }
        this.matriceJeu.get(positionDep.getI()).set(positionDep.getJ(), null);
        this.matriceJeu.get(positionFin.getI()).set(positionFin.getJ(), pionDep);

        boolean temp = peutJouerJ1;
        peutJouerJ1 = peutJouerJ2;
        peutJouerJ2 = temp;
    }

    public void deplacementPieceBot(Pions pionDep, Pions pionFin){
        if (pionFin != null){
            pionDep.setPosY(pionFin.getPosY());
            pionDep.setPosX(pionFin.getPosX());
        }
        else{
            // On soustrait 8 à la position de fin pour la convertir en coordonnées de matrice.
            pionDep.setPosY(8-positionFinBot.getI());
            pionDep.setPosX(positionFinBot.conversionIntLettre(positionFinBot.getJ()));
        }
        this.matriceJeu.get(positionDepBot.getI()).set(positionDepBot.getJ(), null);
        this.matriceJeu.get(positionFinBot.getI()).set(positionFinBot.getJ(), pionDep);

        boolean temp = peutJouerJ1;
        peutJouerJ1 = peutJouerJ2;
        peutJouerJ2 = temp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation de la grille");
        initMatrice();
        afficheMatriceAvecPlateau(this.matriceJeu);

        peutJouerJ1 = true;
        peutJouerJ2 = false;

        buttonController = new ButtonController();
        loginController = new LoginController();
        buttonController.initButtonAcc(boutonAcc);
        if (boutonFin!= null){
            buttonController.initButtonFin(boutonFin);
        }
        clic();
    }

    public boolean comparePionsMemeCouleur(Pions pion1, Pions pion2) {
        if (pion2 != null)
            return !(pion1.getCouleur().equals(pion2.getCouleur()));
        return true;
    }

    public boolean comparePionsDirection(Pions pion1, int posI, String posJstr) {
        Position position = new Position();
        int posJ = position.conversionLettreInt(posJstr);
        int posX = position.conversionLettreInt(pion1.getPosX());
        int posY = 8 - pion1.getPosY();


        switch (pion1.getClass().getSimpleName()) {
            case "Fou" -> {
                return directionDiagonale(pion1, posI, posJ, posX, posY);

            }
            case "Tour" -> {
                return directionHorizontalVertical(pion1, posI, posJ, posX, posY);

            }
            case "Reine" -> {
                if (posX == posJ || posY == posI){
                    return directionHorizontalVertical(pion1, posI, posJ, posX, posY);
                }
                return directionDiagonale(pion1, posI, posJ, posX, posY);

            }
            case "Pion" -> {
                if (matriceJeu.get(posI).get(posJ) != null && (posJ == position.conversionLettreInt(pion1.getPosX()))) {
                    return false;
                }
                return matriceJeu.get(posI).get(posJ) != null || (posJ == position.conversionLettreInt(pion1.getPosX()));
            }
        }
        return true;
    }

    public boolean directionDiagonale(Pions pion, int posI, int posJ, int posX, int posY) {
        if (Math.abs(posX - posJ) == Math.abs(posY - posI)) {
            int xDirection = (posJ - posX > 0) ? 1 : -1;
            int yDirection = (posI - posY > 0) ? 1 : -1;

            int x = posX + xDirection;
            int y = posY + yDirection;

            while (x != posJ && y != posI) {
                if (this.matriceJeu.get(y).get(x) != null) {
                    return false;
                }
                x += xDirection;
                y += yDirection;
            }
        }
        return true;
    }

    public boolean directionHorizontalVertical(Pions pion, int posI, int posJ, int posX, int posY){
        if (posY == posI) {
            if (posX > posJ) {
                for (int i = posX - 1; i >= posJ + 1; --i) {
                    if (this.matriceJeu.get(posI).get(i) != null) {
                        return false;
                    }
                }
            }
            if (posX < posJ) {
                for (int i = posX + 1; i <= posJ - 1; ++i) {
                    if (this.matriceJeu.get(posI).get(i) != null) {
                        return false;
                    }
                }
            }
        }

        if (posX == posJ) {
            if (posY > posI) {
                for (int i = posY - 1; i >= posI + 1; --i) {
                    if (this.matriceJeu.get(i).get(posJ) != null) {
                        return false;
                    }
                }
            }
            if (posY < posI) {
                for (int i = posY + 1; i <= posI - 1; ++i) {
                    if (this.matriceJeu.get(i).get(posJ) != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void situationRoi(Roi roi){
        //TODO
    }
}

