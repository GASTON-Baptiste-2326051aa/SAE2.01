package echec.Pions;

import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;

// Création d'une class Pions
public abstract class Pions{

    // Attribut divers de la class, couleur du pion, position en échéquier,
    private String couleur;
    private String posX;
    private int posY;
    private String url;
    private ImageView image;

    public Pions(String couleur,String posX,int posY,String fileName){
        this.couleur = couleur;
        this.posY = posY;
        this.posX = posX;
        this.url = this.getClass().getResource(fileName).toString();;
        this.image = new ImageView(this.url);
    }

    public abstract boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin);

    public int getPosY(){ return this.posY; }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getPosX(){ return this.posX; }

    public void setPosX(String posX) {
        this.posX = posX;
    }


    public String getCouleur(){ return this.couleur;}

    public String getPos(){ return this.posY + String.valueOf(this.posX); }

    public String getUrl(){return this.url;}

    public String toString(){return getClass().getSimpleName() + "." + this.couleur;}}
