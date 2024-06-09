package echec.Pions;

import java.util.ArrayList;

public class Roi extends Pions{

    public Roi(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }

    @Override
    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        int startX = posXDep.charAt(0) - 'a' + 1;
        int endX = posXFin.charAt(0) - 'a' + 1;

        // On vérifie que son déplacement soit égal à 1 peu importe la direction
        return (Math.abs(startX - endX) == 1 && posYDep == posYFin) || (Math.abs(posYDep - posYFin) == 1 && startX == endX || Math.abs(posYDep - posYFin) == 1 && Math.abs(startX - endX) == 1);
    }

}