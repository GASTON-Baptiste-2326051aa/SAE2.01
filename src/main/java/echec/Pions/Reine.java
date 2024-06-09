package echec.Pions;

import java.util.ArrayList;

public class Reine extends Pions{

    public Reine(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }

    @Override
    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        int startX = posXDep.charAt(0) - 'a' + 1;
        int endX = posXFin.charAt(0) - 'a' + 1;

        // On regarde si elle peut se déplacer en ligne ou en diagonale
        return (Math.abs(startX - endX) == Math.abs(posYDep - posYFin) || (startX == endX || posYDep == posYFin));
    }

}