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

    // Ajout de deux boolean controllant la personne qui doit jouer
    private boolean peutJouerJ1;
    private boolean peutJouerJ2;

    // Variable contenant la matrice du jeu d'échec
    private ArrayList<ArrayList<Pions>> matriceJeu;

    @FXML
    private BorderPane borderPane;

    // Création des attributs correspondant aux position du roi et leur nombre d'echec

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

    /**
     * @author Alex GONCALVES RODRIGUES
     *
     * Fonction permettant de set les noms des joueurs dans le mode JvJ.
     **/
    public void setPlayerNamesJvJ(String player1Prenom,String player1Nom ,String player2Prenom,String player2Nom){
        j1.setText(player1Prenom + " " + player1Nom); //on met le prenom et le nom du joueur 1 dans le label 1
        j2.setText(player2Prenom + " " + player2Nom); //on met le prenom et le nom du joueur 2 dans le label 2
    }

    /**
     * @author Alex GONCALVES RODRIGUES
     *
     * @params String WinnerName = noms du gagnant et String LoserName = nom du perdant
     *
     * Cette fonction, quand elle est appellée, elle va changer la fenetre et mettre celle de la fin tout en mettant les noms des joueurs
     * correspondant a leurs resultat de la partie.
     **/
    public void Findejeu(String winnerName, String loserName) throws IOException { //fonction permettant qu'a la fin du jeu, une fenetre fin du jeu pop.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/fin.fxml"));
        Stage fin = (Stage) boutonFin.getScene().getWindow();
        Scene scene;
        scene = new Scene(loader.load());
        FinController finController = loader.getController(); //on va chercher dans fincontroller des controller
        finController.setPlayerNames(winnerName, loserName); // on utilise la fonction dans finController pour mettres les noms des joueurs a leurs resultats
        fin.setScene(scene);
        fin.setMinHeight(900);
        fin.setMinWidth(500);
        fin.setHeight(900);
        fin.setWidth(500);
        fin.centerOnScreen();

        isTimerStarted = false; //permet de re-activer le timer qpres la fin du jeu
        startButton.setDisable(false); // pertmet de re-activé le bouton apres la fin du jeu
    }

    /**
     * @author Alex GONCALVES RODRIGUES
     *
     * startTimer1 nous permet de lancer les timers quand les joueurs sont pret.
     **/
    private void startTimer1() {
        if (isTimerStarted) { //verrification pour savoir si le timer est deja lancé
            return;
        }

        Integer selectedValue = timerBox.getValue(); //on recup la valeur de la Combo Box et on la met dans selectedValue
        if (selectedValue == null) { //on regarde si la valeur selectionner est null (sauf que par defaut elle est a 1) juste une assistances au cas ou
            throw new IllegalStateException("Timer value is not selected.");
        }

        int tempsSelectionne = selectedValue * 60;
        tempsRestant1 = tempsSelectionne;
        tempsRestant2 = tempsSelectionne;

        timer1 = new Timer();
        timerTask1 = createTimerTask(timerLabel, () -> --tempsRestant1);

        timer1.scheduleAtFixedRate(timerTask1, 1000, 1000);
        isTimer1Running = true; // on lance le premier timmer
        isTimerStarted = true; // on enleve l'option pour relancer le timer
        startButton.setDisable(true); // on desactive le bouton play si la partie est deja lancé
    }

    /**
     * @author Alex GONCALVES RODRIGUES
     *
     * Ici, cette fonction nous permet de faire switch le timer entre le premier et le deuxieme.
     **/
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

    /**
     * @author Alex GONCALVES RODRIGUES
     *
     * On crée dans cette fonction le timer 2, reduissant de 1 a 1 par secondes.
     **/
    private void startTimer2() {
        timer2 = new Timer();
        timerTask2 = createTimerTask(timerLabel2, () -> --tempsRestant2);

        timer2.scheduleAtFixedRate(timerTask2, 1000, 1000);
        isTimer2Running = true;
    }

    /**
     * @author Alex GONCALVES RODRIGUES
     *
     * On crée dans cette fonction le timer 1, reduissant de 1 a 1 par secondes.
     **/
    private void resumeTimer1() {
        timer1 = new Timer();
        timerTask1 = createTimerTask(timerLabel, () -> --tempsRestant1);

        timer1.scheduleAtFixedRate(timerTask1, 1000, 1000);
        isTimer1Running = true;
    }

    /**
     * @author Alex GONCALVES RODRIGUES
     *
     * @params le Label du timer et la fonction update.
     * @params updateRemainingTime qui permet de mettre a jour le timer
     *
     * Cette fonction permet de crée un tache qui celle ci est de mettre a jour le timer et de l'afficher dans le Label.
     **/
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

    /**
     * @author Alex GONCALVES RODRIGUES
     *
     * @params Label du timer present dans le fxml.
     *
     * Dans cette classe, on met a jour les timers pour l'affichage et on verifie si les timers sont nuls (<=0), si c'est le cas
     * alors le jeu est fini car il ne reste plus de temps a un joueur.
     **/
    private void updateTimer(Label timerLabel) {
        int tempsRestant = (timerLabel == this.timerLabel) ? tempsRestant1 : tempsRestant2;
        if (tempsRestant <= 0) {
            timerLabel.setText("00:00"); //Mise a 0 du label
            if (timerLabel == this.timerLabel && timer1 != null) { // verifications du timer 1 voir s'il est null
                timer1.cancel(); //on arrete le timer
                try {
                    Findejeu(j2.getText(), j1.getText()); //on met la fenetre fin de jeu avec les noms qui corresponde au gagant et perdant
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

    /**
     * @author Garrigues Ronan
     *
     * Cette méthode permet d'initier un échiquier dans une matrice
     **/
    // Fonction de génération de la matrice de Pions
    public void initMatrice() {
        // On définit la taille de la matrice
        matriceJeu = new ArrayList<>(8);
        // Puis on la parcours avec un itérateur i et j
        for (int i = 0; i < 8; i++) {
            matriceJeu.add(new ArrayList<>(8));
            for (int j = 0; j < 8; j++) {
                matriceJeu.get(i).add(null);
            }
        }

        // On initialise une liste de position
        String[] colonnes = {"a", "b", "c", "d", "e", "f", "g", "h"};

        // On mets les valeurs des Pions noir dans la matrice
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

        // On mets les valeurs des Pions blanc dans la matrice
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

    /**
     * @author Garrigues Ronan
     *
     * @params une arraylist étant une matrice de Pions
     *
     * Cette méthode permet d'afficher l'échiquier
     **/
    public void afficheMatriceAvecPlateau(ArrayList<ArrayList<Pions>> matrice) {
        // On commence par supprimer tous les imagesView de la grille
        plateau.getChildren().removeIf(node -> node instanceof ImageView);

        // Puis on parcours la matrice de jeu
        Position position = new Position();
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                // On récupere le pion se trouvant au coordonnés I,J de la Matrice
                Pions pion = matriceJeu.get(ligne).get(colonne);
                // Si il est nul on ne fait rien
                if (pion == null) {
                    continue;
                }
                // Sinon on pose l'image du pion, obtenu par son url, sur le gridPane d'affichage
                else {
                    int posX = position.conversionLettreInt(pion.getPosX());
                    String cheminImage = pion.getUrl();
                    ImageView imageView = new ImageView(cheminImage);
                    imageView.setFitHeight(100.0);
                    imageView.setFitWidth(100.0);
                    // On ajoute ici sur le plateau image suivis de ses coordonnées i et j
                    plateau.add(imageView, posX, 8 - pion.getPosY());
                }
            }
        }
    }


    /**
     * @author Garrigues Ronan
     *
     * Cette méthode permet de gérer l'évenement du clic de l'utilisateur
     **/
    public void clic(){

        // On récupere l'évènement du clic de souris
        plateau.setOnMouseClicked(e -> {
            // On affiche la matrice
            afficheMatriceAvecPlateau(matriceJeu);
            // On vérifie si le clic est le clic de départ c'est à dire que la position de depart est inférieur à 0
            if (this.positionDep.getX() < 0){
                // On mets les coordonnées du clic
                this.positionDep.setX((int)e.getX());
                this.positionDep.setY((int)e.getY());
                // On effectue une conversion
                this.positionDep.conversion(this.positionDep.getX(), this.positionDep.getY());
                // On affiche l'image de sélection
                String urlSelection = Objects.requireNonNull(getClass().getResource("img/selection.png")).toExternalForm();
                ImageView selection = new ImageView(urlSelection);
                selection.setFitHeight(100.0);
                selection.setFitWidth(100.0);
                plateau.add(selection, positionDep.getJ(),  positionDep.getI());
                // Si la position de départ n'est pas un pion on annule le clic de départ
                if (matriceJeu.get(positionDep.getI()).get(positionDep.getJ()) == null){
                    positionDep.reset();
                }

            }
            // Sinon si on a une position de début on mets à jour la position de fin
            else if (this.positionFin.getX() < 0 && this.positionDep.getJ() >= 0){
                this.positionFin.setX((int)e.getX());
                this.positionFin.setY((int)e.getY());
                this.positionFin.conversion(this.positionFin.getX(), this.positionFin.getY());

            }
            // On lance le jeu
            jeu();
        });

    }

    /**
     * @authors Garrigues Ronan, Verhille Manon, Gaston Baptise, Goncalves Rodrigues Alex
     *
     *
     * Cette méthode permet de lancer le jeu, c'est à dire les déplacements du pion, les conditions de déplacements, les conditions de victoires
     **/
    public void jeu(){
        // On vérifie que nous avons bien une position de départ et de fin
        if ( this.positionDep.getI() >= 0 && this.positionDep.getJ() >= 0 && this.positionFin.getI() >= 0 && this.positionFin.getJ() >= 0){
            // On récupere les pions de la position de départ et de fin
            Pions pionDep = this.matriceJeu.get(positionDep.getI()).get(positionDep.getJ());
            Pions pionFin =this.matriceJeu.get(positionFin.getI()).get(positionFin.getJ());

            // On effectue une série de test. On regarde si le pion de départ peut se déplacer selon son propore comportement. On n'oublie pas de soustraire à 8 la position de fin pour la convertir en coordonne de matrice
            // Ensuite on vérifie qu'il ne soit pas de la même couleur
            // Puis si aucun pion ne gêne le déplacement
            if (pionDep.peutDeplacer(pionDep.getPosY(), pionDep.getPosX(), 8 - positionFin.getI() ,positionFin.conversionIntLettre(positionFin.getJ())) &&
                    (comparePionsCouleurDifferente(pionDep, pionFin)) && comparePionsDirection(pionDep,positionFin.getI() ,positionFin.conversionIntLettre(positionFin.getJ()))) {

                // On regarde que le joueur déplace bien un pion à lui
                if((peutJouerJ1 && Objects.equals(pionDep.getCouleur(), "blanc")) || ((peutJouerJ2 && Objects.equals(pionDep.getCouleur(), "noir")))){
                    // On effectue le déplacement du pion
                    deplacementPiece(pionDep, pionFin);
                    // Puis on réaffiche la matrice
                    afficheMatriceAvecPlateau(this.matriceJeu);
                    // On mets un message si un des deux roi est en échec
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
                                loginController.updateVictories(partsJ2[0],partsJ2[1]);
                                Findejeu(j2.getText(), j1.getText());

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (peutJouerJ2){
                            try {
                                loginController.updateVictories(partsJ1[0],partsJ1[1]);
                                Findejeu(j1.getText(),j2.getText());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                }
            }
            // On remets à -1 les positions pour pouvoir rejouer
            positionDep.reset();
            positionFin.reset();
        }
    }

    /**
     * @authors Garrigues Ronan, Verhille Manon, Gaston Baptise, Goncalves Rodrigues Alex
     *
     * @param pionDep : le pion correspondant au clic de départ
     * @param pionFin : Le pion correspondant au clic de fin
     *
     * Cette fonction permet d'effectuer le déplacement d'un pion,  si il ne se mets pas en échec
     **/
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
        // On attribut les nouvelles coordonnées
        pionDep.setPosY(8 - positionFin.getI());
        pionDep.setPosX(positionFin.conversionIntLettre(positionFin.getJ()));

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

        // On mets à jour la matrice
        this.matriceJeu.get(positionDep.getI()).set(positionDep.getJ(), null);
        this.matriceJeu.get(positionFin.getI()).set(positionFin.getJ(), pionDep);

        // On vérifie si le roi est en échec après le déplacement
        boolean roiEnEchec = false;
        if (peutJouerJ1 && enEchec(posRoiBlancMatriceI, posRoiBlancMatriceJ)) {
            roiEnEchec = true;
            ++nbEchecRoiBlanc;
        } else if (peutJouerJ2 && enEchec(posRoiNoirMatriceI, posRoiNoirMatriceJ)) {
            roiEnEchec = true;
            ++nbEchecRoiNoir;
        }

        // Si le roi est en échec on restaure l'état précédent de la matrice et du pion
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
            // Changer de joueur et basculer les timers si le déplacement est valide ainsi que remettre les compteurs du roi à 0
            if (peutJouerJ1) {
                nbEchecRoiBlanc = 0;
            }
            else if (peutJouerJ2) {
                nbEchecRoiNoir = 0;
            }
            // On change de joueur
            boolean temp = peutJouerJ1;
            peutJouerJ1 = peutJouerJ2;
            peutJouerJ2 = temp;
            toggleTimers();
        }
    }

    /**
     * @authors Garrigues Ronan, Verhille Manon, Gaston Baptise, Goncalves Rodrigues Alex
     *
     * Initialisations de la classe.
     * @params  passe en parametres l'url et les ressources utilisé pour localisé les objets utilisé par le root.
     **/
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
        timerBox.getSelectionModel().selectFirst(); // par défaut le temps est celui de 1min

        timerBox.valueProperty().addListener((observable, oldValue, newValue) -> { //On regarde ce que la personne choisis comme temps et on l'affiche dans le timmer
            String timeText = newValue + " Min";
            timerLabel.setText(timeText);
            timerLabel2.setText(timeText);
        });

        startButton.setOnAction(e -> startTimer1()); // on start le timer 1 avec la valeur affecté

        clic();
    }


    /**
     * @author Garrigues Ronan
     *
     *
     * @param pion1 étant le premier pion à comparer
     * @param pion2 étant le deuxième pion à comparer
     *
     * Fonction permettant de regarder si les couleurs sont différentes
     *
     * @return boolean true si ils sont de couleur différentes, false sinon
     **/
    public boolean comparePionsCouleurDifferente(Pions pion1, Pions pion2) {
        if (pion2 != null)
            return !(pion1.getCouleur().equals(pion2.getCouleur()));
        return true;
    }

    /**
     * @authors Garrigues Ronan, Verhille Manon
     *
     *
     * @param pion1 étant pion de départ
     * @param posI étant la postion de la ligne correspondant au clic de fin, à la où on souhaite aller
     * @param posJstr étant la postion de la colonne
     *
     * Methodes permettant de vérifier si le pion rencontre un obstacle. Et ceci selon de quelle classe est le pion
     *
     * @return boolean true si il n'y a aucun obstacle, false sinon
     **/
    public boolean comparePionsDirection(Pions pion1, int posI, String posJstr) {
        Position position = new Position();
        // On récupere les coordonnées du pion et de l'arrivée, tout ça en position de matrice
        int posJ = position.conversionLettreInt(posJstr);
        int posX = position.conversionLettreInt(pion1.getPosX());
        int posY = 8 - pion1.getPosY();

        // On regarde quel est le pion
        switch (pion1.getClass().getSimpleName()) {
            case "Fou" -> {
                // Si c'est le fou on effectue une vérification diagonale
                return directionDiagonale( posI, posJ, posX, posY);

            }
            case "Tour" -> {
                // Si c'est la tour on regarde les directions horizontaux et verticaux
                return directionHorizontalVertical( posI, posJ, posX, posY);

            }
            case "Reine" -> {
                // Si c'est la reine on regarde par rapport au pion ceux qui faut choisir.
                if (posX == posJ || posY == posI){
                    return directionHorizontalVertical( posI, posJ, posX, posY);
                }
                return directionDiagonale( posI, posJ, posX, posY);

            }
            case "Pion" -> {
                // Si c'est le pion on regarde si il se trouve à une case en diagonale pour pouvoir prendre le pion
                if (matriceJeu.get(posI).get(posJ) != null && (posJ == position.conversionLettreInt(pion1.getPosX()))) {
                    return false;
                }
                return matriceJeu.get(posI).get(posJ) != null || (posJ == position.conversionLettreInt(pion1.getPosX()));
            }
        }


        return true;
    }


    /**
     * @author Garrigues Ronan
     *
     *
     * @param posI étant la position initial des lignes
     * @param posJ étant la position initial des colonnes
     * @param posX étant la position final des lignes
     * @param posY étant la position final des colonnes
     *
     * Fonction pour vérifier si entre deux case de matrice le chemin diagonal est libre, c'est à dire il ne possède aucun pion
     *
     * @return boolean true si il n'y a aucun obstacle, false sinon
     **/
    public boolean directionDiagonale( int posI, int posJ, int posX, int posY) {
        if (Math.abs(posX - posJ) == Math.abs(posY - posI)) {
            // On détermine la direction de la diagonale
            int xDirection = (posJ - posX > 0) ? 1 : -1;
            int yDirection = (posI - posY > 0) ? 1 : -1;

            // Intialise les positions de fin
            int x = posX + xDirection;
            int y = posY + yDirection;

            // Tant que la position n'est pas atteint on regarde si il y a un pion
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

    /**
     * @authors Garrigues Ronan, Verhille Manon
     *
     *
     * @param posI étant la position initial des lignes
     * @param posJ étant la position initial des colonnes
     * @param posX étant la position final des lignes
     * @param posY étant la position final des colonnes
     *
     * Fonction pour vérifier si entre deux case de matrice le chemin horizontal et vertical est libre, c'est à dire il ne possède aucun pion
     *
     * @return boolean true si il n'y a aucun obstacle, false sinon
     **/
    public boolean directionHorizontalVertical( int posI, int posJ, int posX, int posY){
        // On regarde si la direction est une direction Vertical
        if (posY == posI) {
            // Puis on regarde la direction
            if (posX > posJ) {
                // On parcours jusqu'au dernier indice à partir de la case qui suis notre pion
                for (int i = posX - 1; i >= posJ + 1; --i) {
                    // S'il y a un pion on renvoi false
                    if (this.matriceJeu.get(posI).get(i) != null) {
                        return false;
                    }
                }
            }
            // Même situation dans la direction opposé
            if (posX < posJ) {
                for (int i = posX + 1; i <= posJ - 1; ++i) {
                    if (this.matriceJeu.get(posI).get(i) != null) {
                        return false;
                    }
                }
            }
        }

        // Même situations cette fois ci à l'horizontal
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
        // Si aucun des cas est trouvé on renvoi true
        return true;
    }


    /**
     * @author Garrigues Ronan
     *
     *
     * @param posIMatrice étant la position des lignes du pion à vérifier
     * @param posJMatrice étant la position des colonnes du pion à vérifier
     *
     * Fonction pour vérifier si un pion est en échec
     *
     * @return boolean true si le pion est echec, false sinon
     **/
    // Cette fonction permet via des coordonnés indiquer si la pièce est en échec. Cela signifie si la pièce peut être prise au tour suivant
    public boolean enEchec(int posIMatrice, int posJMatrice) {
        // Cette fonction permet via des coordonnées indiquer si la pièce est en échec. Cela signifie si la pièce peut être prise au tour suivant
        // Nous allons tester a partir d'une coordonne toutes les direction. Si le premier pion trouvé est unbe piece le mettant en échecs alors

        // Test du coté horizontal et vertical dans toutes les directions
        for (int i = posIMatrice+1; i <= 7; ++i) { // On parcours à partir de la case de départ en incrémentant la position i jusqu'au bord du plateau de jeu
            if (this.matriceJeu.get(i).get(posJMatrice) != null) { // Si il y a un pion
                // On regarde si c'est une reine ou une tour sinon on sort de la boucle
                if (this.matriceJeu.get(i).get(posJMatrice) instanceof Tour || this.matriceJeu.get(i).get(posJMatrice) instanceof Reine) {
                    // On vérifie maintenant si la couleur est celle de l'adversaire
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

        // On effectue le même principe en desincrémentant la valeur de la position i
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

        // On effectue le même principe en desincrémentant la valeur de la position j
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

        // On effectue le même principe en incrémentant la valeur de la position j
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
        // On initialise un compteur de boucle afin d'avancer en diagonale
        int countBoucle = 0;
        // Effectue une boucle allant jusqu'au bord de l'échiquier
        for (int i = posIMatrice+1; i <= 7 && posJMatrice + countBoucle +1  <= 7; ++i) {
            ++countBoucle; // On augmente le nombre du compteur de boucle
            // L'instruction this.matriceJeu.get(i).get(posJMatrice+countBoucle) permet d'avancer en diagonale, en augmentant le i et le j de même manière
            if (this.matriceJeu.get(i).get(posJMatrice+countBoucle) != null) {  // Si il y a un pion
                if (this.matriceJeu.get(i).get(posJMatrice+countBoucle) instanceof Fou || this.matriceJeu.get(i).get(posJMatrice+countBoucle) instanceof Reine) { // On regarde si c'est une reine ou un fou
                    if (!Objects.equals(this.matriceJeu.get(i).get(posJMatrice+countBoucle).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())) { // Puis si la couleur est celle de l'adversaire
                        return true; // Dans ce cas le pion est échec, dans les autres cas non
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

        // Puis on effectue cela pour chacune des directions de diagonales.

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
        try{ // On essaie d'accéder à une position de la matrice ou un cavalier pourrait mettre en echec la pièce. On vérifie donc si la case est bien un cavalier
            if(this.matriceJeu.get(posIMatrice-1).get(posJMatrice-2) instanceof Cavalier && !Objects.equals(this.matriceJeu.get(posIMatrice - 1).get(posJMatrice - 2).getCouleur(), this.matriceJeu.get(posIMatrice).get(posJMatrice).getCouleur())){
                return true;
            }

        } catch(Exception _){} // En cas d'exception c'est à dire que la case dépasse du tableau on ignore le cas de figure

        // Puis on répete le processus pour toutes les positions du possible du cavalier

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
                // Cette vérification permet de regarder si un pion se trouve devant le roi noir donc plus bas sur l'échiquier. Si c'est le cas et qui s'agit de une case en diagonale et que de plus le pion est de la couleur adverse alors le roi est en échec
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

        // On fait la même procédure pour cette fois ci le roi blanc, donc si le pion se trouve plus haut sur l'échiquier donc plus bas dans la matrice
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
        // On effectue le calcul entre la position des 2 rois. Pour établir si il y a échec.
        if (Math.abs(posRoiBlancMatriceI - posRoiNoirMatriceI) <= 1 && Math.abs(posRoiBlancMatriceJ - posRoiNoirMatriceJ) <= 1){
            return true;
        }

        return false;
    }

}
