package echec.Pions;

import java.util.ArrayList;
import java.util.Objects;

public class Tour extends Pions{

    public Tour(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }
    public void deplacement(String posXDep, int posYDep, String posXFin, int posYFin){

    }

    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin, ArrayList<ArrayList<Pions>> matrice) {
        return (Objects.equals(posXDep, posXFin) || posYDep == posYFin);
    }

}