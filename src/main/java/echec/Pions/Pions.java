package echec.Pions;

import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;

// Création d'une class Pions
public abstract class Pions{

    // Attribut divers de la class, couleur du pion, position en échéquier, url pour l'image
    private String couleur;
    private String posX;
    private int posY;
    private String url;
    private ImageView image;

    // Constructeur du Pions
    public Pions(String couleur,String posX,int posY,String fileName){
        this.couleur = couleur;
        this.posY = posY;
        this.posX = posX;
        this.url = this.getClass().getResource(fileName).toString();;
        this.image = new ImageView(this.url);
    }

    /**
     * @author Garrigues Ronan
     *
     *
     * @param posYDep étant une position sur les lignes
     * @param posXDep étant une position sur les colonnes
     * @param posYFin étant une position sur les lignes
     * @param posYFin étant une position sur les colonnes
     *
     * Méthode pour vérifier si un pion peut se déplacer selon ses propriétées cette méthode est abstraite et donc implémenté par toutes les classes filles et cela selon leur comportement aux échecs
     *
     * @return boolean true si le pion peut se déplacer, false sinon
     **/
    public abstract boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin);

    /**
     * @author Garrigues Ronan
     *
     * getter de posY
     *
     * @return posY
     **/
    public int getPosY(){ return this.posY; }

    /**
     * @author Garrigues Ronan
     *
     * @param posY valeur à initialiser
     * setter de posY
     *
     **/
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * @author Garrigues Ronan
     *
     * getter de posX
     *
     * @return posX
     **/
    public String getPosX(){ return this.posX; }

    /**
     * @author Garrigues Ronan
     *
     * @param posX valeur à initialiser
     * setter de posY
     *
     **/
    public void setPosX(String posX) {
        this.posX = posX;
    }

    /**
     * @author Garrigues Ronan
     *
     * getter de couleur
     *
     * @return couleur
     **/
    public String getCouleur(){ return this.couleur;}

    /**
     * @author Garrigues Ronan
     *
     * getter de url
     *
     * @return url
     **/
    public String getUrl(){return this.url;}

    /**
     * @author Garrigues Ronan
     *
     * Methode to string
     *
     * @return Un string avec le nom et la couleur.
     **/
    public String toString(){return getClass().getSimpleName() + "." + this.couleur;}}
