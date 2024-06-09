package echec.Pions;

import java.util.ArrayList;
import java.util.Objects;

public class Tour extends Pions{

    public Tour(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }

    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        return (Objects.equals(posXDep, posXFin) || posYDep == posYFin);
    }

}