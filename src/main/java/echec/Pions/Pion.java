package echec.Pions;

import java.util.ArrayList;

public class Pion extends Pions{

    public Pion(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }

    public void deplacement(String posXDep, int posYDep, String posXFin, int posYFin){

    }

    @Override
    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin, ArrayList<ArrayList<Pions>> matrice) {
        return (Math.abs(posYDep - posYFin) == 1);
    }

}