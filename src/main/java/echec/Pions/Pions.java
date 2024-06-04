package echec.Pions;

import javafx.scene.image.ImageView;

public abstract class Pions{

private String couleur;
private String posX;
private int posY;
private boolean etat = true;
private String url;
private ImageView image;

public Pions(String couleur,String posX,int posY,String url){
    this.couleur = couleur;
    this.posY = posY;
    this.posX = posX;
    this.url = url;
    this.image = new ImageView(this.url);
}

public abstract void deplacement(String posXDep, int posYDep, String posXFin, int posYFin);

public abstract boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin);

public void setEtat(Boolean etat){ this.etat = etat; }

public int getPosY(){ return this.posY; }

public String getPosX(){ return this.posX; }

public boolean getEtat(){ return this.etat; }

public String getCouleur(){ return this.couleur;}

public String getPos(){ return this.posY + String.valueOf(this.posX); }

}