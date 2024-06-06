package echec.Pions;

import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;

public abstract class Pions{

    private String couleur;
    private String posX;
    private int posY;
    private boolean etat = true;
    private String url;
    private ImageView image;

    public Pions(String couleur,String posX,int posY,String fileName){
        this.couleur = couleur;
        this.posY = posY;
        this.posX = posX;
        this.url = this.getClass().getResource(fileName).toString();;
        this.image = new ImageView(this.url);
    }

    public abstract void deplacement(String posXDep, int posYDep, String posXFin, int posYFin);

    public abstract boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin);

    public void setEtat(Boolean etat){ this.etat = etat; }

    public int getPosY(){ return this.posY; }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getPosX(){ return this.posX; }

    public void setPosX(String posX) {
        this.posX = posX;
    }

    public boolean getEtat(){ return this.etat; }

    public String getCouleur(){ return this.couleur;}

    public String getPos(){ return this.posY + String.valueOf(this.posX); }

    public String getUrl(){return this.url;}

    public String toString(){return getClass().getSimpleName() + "." + this.couleur;}}
