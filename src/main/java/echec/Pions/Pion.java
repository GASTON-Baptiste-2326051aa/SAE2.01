package echec.Pions;

import java.util.ArrayList;
import java.util.Objects;

public class Pion extends Pions{
    private int deplacement = 0;

    public Pion(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }

    public void deplacement(String posXDep, int posYDep, String posXFin, int posYFin){

    }

    @Override
    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin, ArrayList<ArrayList<Pions>> matrice) {
        if (Objects.equals(super.getCouleur(), "noir")){
            if (this.deplacement == 0 ){
                ++this.deplacement;
                return ((posYDep - posYFin) == 2 || ( posYDep - posYFin) == 1) && Objects.equals(posXDep, posXFin);
            }
            ++this.deplacement;
            return ( posYDep - posYFin) == 1 && Objects.equals(posXDep, posXFin);
        }
        else{
            if (this.deplacement == 0 ){
                ++this.deplacement;
                return ((posYDep - posYFin) == -2 || ( posYDep - posYFin) == -1) && Objects.equals(posXDep, posXFin);
            }
            ++this.deplacement;
            return ( posYDep - posYFin) == -1 && Objects.equals(posXDep, posXFin);
        }
    }

}