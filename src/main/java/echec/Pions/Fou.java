package echec.Pions;

import java.util.ArrayList;

public class Fou extends Pions{

    public Fou(String couleur, String posY, int posX, String url) {
        super(couleur, posY, posX, url);
    }

    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        int startX = posXDep.charAt(0) - 'a' + 1;
        int endX = posXFin.charAt(0) - 'a' + 1;

        // On vérifie si le déplacement est diagonal, donc si la différence de ligne et de colonnes est la même
        return (Math.abs(startX - endX) == Math.abs(posYDep - posYFin));
    }

}