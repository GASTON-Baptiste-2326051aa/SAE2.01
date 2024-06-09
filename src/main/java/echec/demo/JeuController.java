package echec.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import echec.Pions.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.net.URL;
import java.util.Timer;

public class JeuController implements Initializable {

    @FXML
    private GridPane plateau;

    Position positionDep = new Position();
    Position positionFin = new Position();

    private boolean peutJouerJ1;
    private boolean peutJouerJ2;

    private ArrayList<ArrayList<Pions>> matriceJeu;

    @FXML
    private BorderPane borderPane;

    // Création des attributs correspondant aux position du roi

    private int posRoiNoirMatriceI = 0;
    private int posRoiNoirMatriceJ = 4;

    private int posRoiBlancMatriceI = 7;
    private int posRoiBlancMatriceJ = 4;

    private int nbEchecRoiNoir = 0;
    private int nbEchecRoiBlanc = 0;


    @FXML
    private Label j1;
    @FXML
    private Label j2;
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


    private ButtonController buttonController;
    private LoginController loginController = new LoginController();

    private Timer timer1;
    private Timer timer2;

    private TimerTask timerTask1;
    private TimerTask timerTask2;

    private int tempsRestant1;
    private int tempsRestant2;

    private boolean isTimer1Running = false;
    private boolean isTimer2Running = false;

    private boolean isTimerStarted = false;

    public void setPlayerNamesJvJ(String player1Prenom,String player1Nom ,String player2Prenom,String player2Nom){ //fonction permettant de set les noms des joueurs dans
        j1.setText(player1Prenom + " " + player1Nom);                                                              // le mode Joueur vs Joueurs
        j2.setText(player2Prenom + " " + player2Nom);
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

        isTimerStarted = false; //permet de re-activer le timer qpres la fin du jeu
        startButton.setDisable(false); // pertmet de re-activé le bouton apres la fin du jeu
    }
    private void startTimer1() {
        if (isTimerStarted) { //verrification pour savoir si le timer est deja lancé
            return;
        }

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
        isTimerStarted = true; // on enleve l'option pour relancer le timer
        startButton.setDisable(true); // on desactive le bouton play si la partie est deja lancé
    }

    private void toggleTimers() { // permet de faire les switch de timer entre le 1er et le 2eme
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

    private void startTimer2() { //creation du timer 2
        timer2 = new Timer();
        timerTask2 = createTimerTask(timerLabel2, () -> --tempsRestant2);

        timer2.scheduleAtFixedRate(timerTask2, 1000, 1000);
        isTimer2Running = true;
    }

    private void resumeTimer1() { //creation du timer 1
        timer1 = new Timer();
        timerTask1 = createTimerTask(timerLabel, () -> --tempsRestant1);

        timer1.scheduleAtFixedRate(timerTask1, 1000, 1000);
        isTimer1Running = true;
    }

    private TimerTask createTimerTask(javafx.scene.control.Label timerLabel, Runnable updateRemainingTime) {
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

    private void updateTimer(Label timerLabel) { //fonction permettant de faire la verification du timer voir si il est null pour arrete le jeu.
        int tempsRestant = (timerLabel == this.timerLabel) ? tempsRestant1 : tempsRestant2;
        if (tempsRestant <= 0) {
            timerLabel.setText("00:00");
            if (timerLabel == this.timerLabel && timer1 != null) {
                timer1.cancel();
                try {
                    Findejeu(j2.getText(), j1.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (timerLabel == this.timerLabel2 && timer2 != null) {
                timer2.cancel();
                try{
                    Findejeu(j1.getText(), j2.getText());
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        } else {
            int minutes = tempsRestant / 60;
            int seconds = tempsRestant % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        }
    }

    // Fonction de génération de la matrice de Pions
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
            afficheMatriceAvecPlateau(matriceJeu);
            if (this.positionDep.getX() < 0){
                this.positionDep.setX((int)e.getX());
                this.positionDep.setY((int)e.getY());
                this.positionDep.conversion(this.positionDep.getX(), this.positionDep.getY());
                String urlSelection = Objects.requireNonNull(getClass().getResource("img/selection.png")).toExternalForm();
                ImageView selection = new ImageView(urlSelection);
                selection.setFitHeight(100.0);
                selection.setFitWidth(100.0);
                plateau.add(selection, positionDep.getJ(),  positionDep.getI());
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

    public void jeu(){
        if ( this.positionDep.getI() >= 0 && this.positionDep.getJ() >= 0 && this.positionFin.getI() >= 0 && this.positionFin.getJ() >= 0){
            Pions pionDep = this.matriceJeu.get(positionDep.getI()).get(positionDep.getJ());
            Pions pionFin =this.matriceJeu.get(positionFin.getI()).get(positionFin.getJ());

            // On soustrait 8 à la position de fin pour la convertir en coordonne de matrice
            if (pionDep != null && pionDep.peutDeplacer(pionDep.getPosY(), pionDep.getPosX(), 8 - positionFin.getI() ,positionFin.conversionIntLettre(positionFin.getJ())) && (comparePionsMemeCouleur(pionDep, pionFin)) && comparePionsDirection(pionDep,positionFin.getI() ,positionFin.conversionIntLettre(positionFin.getJ()))) {
                if((peutJouerJ1 && Objects.equals(pionDep.getCouleur(), "blanc")) || ((peutJouerJ2 && Objects.equals(pionDep.getCouleur(), "noir")))){
                    deplacementPiece(pionDep, pionFin);
                    afficheMatriceAvecPlateau(this.matriceJeu);
                    if (enEchec(posRoiNoirMatriceI, posRoiNoirMatriceJ)){
                        System.out.println("Le roi noir est en échec");
                    }
                    if (enEchec(posRoiBlancMatriceI, posRoiBlancMatriceJ)){
                        System.out.println("Le roi blanc est en échec");
                    }
                    if (nbEchecRoiNoir == 3 || nbEchecRoiBlanc == 3){
                        String[] partsJ1 = j1.getText().split(" ");
                        String[] partsJ2 = j2.getText().split(" ");
                        loginController.updateMatches(partsJ2[0],partsJ2[1]);
                        loginController.updateMatches(partsJ1[0],partsJ1[1]);
                        if (peutJouerJ1){
                            try {
                                loginController.updateVictories(partsJ1[0],partsJ1[1]);
                                Findejeu(j2.getText(), j1.getText());

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (peutJouerJ2){
                            try {
                                loginController.updateVictories(partsJ2[0],partsJ2[1]);
                                Findejeu(j1.getText(),j2.getText());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                }
            }
            positionDep.reset();
            positionFin.reset();
        }
    }

    public void deplacementPiece(Pions pionDep, Pions pionFin) {
        // Sauvegarder l'état actuel de la matrice
        ArrayList<ArrayList<Pions>> saveMatrice = new ArrayList<>();
        for (ArrayList<Pions> row : matriceJeu) {
            saveMatrice.add(new ArrayList<>(row));
        }

        Position position = new Position();
        // Sauvegarder la position actuelle du pion
        int savePosX = position.conversionLettreInt(pionDep.getPosX());
        int savePosY = pionDep.getPosY();

        // Effectuer le déplacement
        if (pionFin != null) {
            pionDep.setPosY(pionFin.getPosY());
            pionDep.setPosX(pionFin.getPosX());
        } else {
            pionDep.setPosY(8 - positionFin.getI());
            pionDep.setPosX(positionFin.conversionIntLettre(positionFin.getJ()));
        }

        // Mettre à jour la position du roi si le pion déplacé est un roi
        if (pionDep instanceof Roi) {
            if ( pionDep.getCouleur().equals("noir")){
                posRoiNoirMatriceI = positionFin.getI();
                posRoiNoirMatriceJ = positionFin.getJ();
            }
            else{
                posRoiBlancMatriceI = positionFin.getI();
                posRoiBlancMatriceJ = positionFin.getJ();
            }
        }

        // Mettre à jour la matrice
        this.matriceJeu.get(positionDep.getI()).set(positionDep.getJ(), null);
        this.matriceJeu.get(positionFin.getI()).set(positionFin.getJ(), pionDep);

        // Vérifier si le roi est en échec après le déplacement
        boolean roiEnEchec = false;
        if (peutJouerJ1 && enEchec(posRoiBlancMatriceI, posRoiBlancMatriceJ)) {
            roiEnEchec = true;
            ++nbEchecRoiBlanc;
        } else if (peutJouerJ2 && enEchec(posRoiNoirMatriceI, posRoiNoirMatriceJ)) {
            roiEnEchec = true;
            ++nbEchecRoiNoir;
        }

        // Si le roi est en échec, restaurer l'état précédent de la matrice et du pion
        if (roiEnEchec) {
            // Remettre l'état précédent du roi
            if (pionDep instanceof Roi) {
                if ( pionDep.getCouleur().equals("noir")){
                    posRoiNoirMatriceI = positionDep.getI();
                    posRoiNoirMatriceJ = positionDep.getJ();
                }
                else{
                    posRoiBlancMatriceI = positionDep.getI();
                    posRoiBlancMatriceJ = positionDep.getJ();
                }
            }
            matriceJeu = saveMatrice;
            pionDep.setPosX(position.conversionIntLettre(savePosX));
            pionDep.setPosY(savePosY);
        } else {
            // Changer de joueur et basculer les timers si le déplacement est valide
            if (peutJouerJ1) {
                nbEchecRoiBlanc = 0;
            }
            else if (peutJouerJ2) {
                nbEchecRoiNoir = 0;
            }
            boolean temp = peutJouerJ1;
            peutJouerJ1 = peutJouerJ2;
            peutJouerJ2 = temp;
            toggleTimers();
        }
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

        timerBox.getItems().addAll(1, 5, 10, 15); // Combo box selection des temps
        timerBox.getSelectionModel().selectFirst(); // par defaut le temps est celui de 1min

        timerBox.valueProperty().addListener((observable, oldValue, newValue) -> { //On regarde ce que la personne choisis comme temps et on l'affiche dans le timmer
            String timeText = newValue + " Min";
            timerLabel.setText(timeText);
            timerLabel2.setText(timeText);
        });

        startButton.setOnAction(e -> startTimer1()); // on start le timmer 1 avec la valeur affecté

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

    // Cette fonction permet via des coordonnés indiquer si la pièce est en échec. Cela signifie si la pièce peut être prise au tour suivant
    public boolean enEchec(int posIMatrice, int posJMatrice) {
        // Nous allons tester a partir d'une coordonne toutes les direction. Si le premier pion trouvé est unbe piece le mettant en échecs alors

        // Test du coté horizontal dans toutes les direction
        for (int i = posIMatrice+1; i <= 7; ++i) {
            if (this.matriceJeu.get(i).get(posJMatrice) != null) {
                if (this.matriceJeu.get(i).get(posJMatrice) instanceof Tour || this.matriceJeu.get(i).get(posJMatrice) instanceof Reine) {
                    if (!Objects.equals(this.matriceJeu.get(i).get(posJMatrice).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())) {
                        return true;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
        for (int i = posIMatrice-1; i >= 0; --i) {
            if (this.matriceJeu.get(i).get(posJMatrice) != null) {
                if (this.matriceJeu.get(i).get(posJMatrice) instanceof Tour || this.matriceJeu.get(i).get(posJMatrice) instanceof Reine) {
                    if (!Objects.equals(this.matriceJeu.get(i).get(posJMatrice).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())) {
                        return true;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
        for (int j = posJMatrice - 1; j >= 0; --j) {
            if (this.matriceJeu.get(posIMatrice).get(j) != null) {
                if (this.matriceJeu.get(posIMatrice).get(j) instanceof Tour || this.matriceJeu.get(posIMatrice).get(j) instanceof Reine) {
                    if (!Objects.equals(this.matriceJeu.get(posIMatrice).get(j).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())) {
                        return true;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
        for (int j = posJMatrice + 1; j <= 7; ++j) {
            if (this.matriceJeu.get(posIMatrice).get(j) != null) {
                if (this.matriceJeu.get(posIMatrice).get(j) instanceof Tour || this.matriceJeu.get(posIMatrice).get(j) instanceof Reine) {
                    if (!Objects.equals(this.matriceJeu.get(posIMatrice).get(j).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())) {
                        return true;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }

        // Test des digonales
        // Conpteur de boucle pour incrémenter et le i et le j
        int countBoucle = 0;
        for (int i = posIMatrice+1; i <= 7 && posJMatrice + countBoucle +1  <= 7; ++i) {
            ++countBoucle;
            if (this.matriceJeu.get(i).get(posJMatrice+countBoucle) != null) {
                if (this.matriceJeu.get(i).get(posJMatrice+countBoucle) instanceof Fou || this.matriceJeu.get(i).get(posJMatrice+countBoucle) instanceof Reine) {
                    if (!Objects.equals(this.matriceJeu.get(i).get(posJMatrice+countBoucle).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())) {
                        return true;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }

        }

        countBoucle = 0;
        for (int i = posIMatrice-1; i >= 0 && posJMatrice - countBoucle-1>= 0; --i) {
            ++countBoucle;
            if (this.matriceJeu.get(i).get(posJMatrice-countBoucle) != null) {

                if (this.matriceJeu.get(i).get(posJMatrice-countBoucle) instanceof Fou || this.matriceJeu.get(i).get(posJMatrice-countBoucle) instanceof Reine) {
                    if (!Objects.equals(this.matriceJeu.get(i).get(posJMatrice-countBoucle).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())) {
                        return true;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }

        countBoucle = 0;
        for (int i = posIMatrice+1; i <= 7 && posJMatrice - countBoucle-1>= 0; ++i) {
            ++countBoucle;
            if (this.matriceJeu.get(i).get(posJMatrice-countBoucle) != null) {
                if (this.matriceJeu.get(i).get(posJMatrice-countBoucle) instanceof Fou || this.matriceJeu.get(i).get(posJMatrice-countBoucle) instanceof Reine) {
                    if (!Objects.equals(this.matriceJeu.get(i).get(posJMatrice-countBoucle).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())) {
                        return true;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }

        countBoucle = 0;
        for (int i = posIMatrice-1; i >= 0 && posJMatrice + countBoucle +1  <= 7; --i) {
            ++countBoucle;
            if (this.matriceJeu.get(i).get(posJMatrice+countBoucle) != null) {
                if (this.matriceJeu.get(i).get(posJMatrice+countBoucle) instanceof Fou || this.matriceJeu.get(i).get(posJMatrice+countBoucle) instanceof Reine) {
                    if (!Objects.equals(this.matriceJeu.get(i).get(posJMatrice+countBoucle).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())) {
                        return true;
                    }
                    else {
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }

        // Test echecs par un cavalier.
        try{
            if(this.matriceJeu.get(posIMatrice-1).get(posJMatrice-2) instanceof Cavalier && !Objects.equals(this.matriceJeu.get(posIMatrice - 1).get(posJMatrice - 2).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                return true;
            }

        } catch(Exception _){}

        try{
            if(this.matriceJeu.get(posIMatrice-1).get(posJMatrice+2) instanceof Cavalier && !Objects.equals(this.matriceJeu.get(posIMatrice - 1).get(posJMatrice + 2).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                return true;
            }

        } catch(Exception _){}

        try{
            if(this.matriceJeu.get(posIMatrice+1).get(posJMatrice-2) instanceof Cavalier && !Objects.equals(this.matriceJeu.get(posIMatrice + 1).get(posJMatrice - 2).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                return true;
            }

        } catch(Exception _){}

        try{
            if(this.matriceJeu.get(posIMatrice+1).get(posJMatrice+2) instanceof Cavalier && !Objects.equals(this.matriceJeu.get(posIMatrice + 1).get(posJMatrice + 2).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                return true;
            }

        } catch(Exception _){}

        try{
            if(this.matriceJeu.get(posIMatrice-2).get(posJMatrice-1) instanceof Cavalier && !Objects.equals(this.matriceJeu.get(posIMatrice - 2).get(posJMatrice - 1).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                return true;
            }

        } catch(Exception _){}

        try{
            if(this.matriceJeu.get(posIMatrice-2).get(posJMatrice+1) instanceof Cavalier && !Objects.equals(this.matriceJeu.get(posIMatrice - 2).get(posJMatrice + 1).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                return true;
            }

        } catch(Exception _){}

        try{
            if(this.matriceJeu.get(posIMatrice+2).get(posJMatrice-1) instanceof Cavalier && !Objects.equals(this.matriceJeu.get(posIMatrice + 2).get(posJMatrice - 1).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                return true;
            }

        } catch(Exception _){}

        try{
            if(this.matriceJeu.get(posIMatrice+2).get(posJMatrice+1) instanceof Cavalier && !Objects.equals(this.matriceJeu.get(posIMatrice + 2).get(posJMatrice + 1).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                return true;
            }

        } catch(Exception _){}

        // Test echec par un Pion
        // On commence par regarder la couleur du roi
        if (this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur() == "noir"){
            // Puis on vérifie les coordonné du roi
            try{
                if (this.matriceJeu.get(posIMatrice+1).get(posJMatrice-1) instanceof Pion && !Objects.equals(this.matriceJeu.get(posIMatrice+1).get(posJMatrice-1).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                    return true;
                }
            }
            catch(Exception _){}
            try{
                if (this.matriceJeu.get(posIMatrice+1).get(posJMatrice+1) instanceof Pion && !Objects.equals(this.matriceJeu.get(posIMatrice+1).get(posJMatrice+1).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                    return true;
                }
            }
            catch(Exception _){}
        }
        else {
            try{
                if (this.matriceJeu.get(posIMatrice-1).get(posJMatrice-1) instanceof Pion && !Objects.equals(this.matriceJeu.get(posIMatrice-1).get(posJMatrice-1).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                    return true;
                }
            }
            catch(Exception _){}
            try{
                if (this.matriceJeu.get(posIMatrice-1).get(posJMatrice+1) instanceof Pion && !Objects.equals(this.matriceJeu.get(posIMatrice-1).get(posJMatrice+1).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                    return true;
                }
            }
            catch(Exception _){}
        }

        // Test echec Roi entre eux
        if (Math.abs(posRoiBlancMatriceI - posRoiNoirMatriceI) <= 1 && Math.abs(posRoiBlancMatriceJ - posRoiNoirMatriceJ) <= 1){
            return true;
        }

        return false;
    }

}
