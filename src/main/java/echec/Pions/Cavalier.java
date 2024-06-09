package echec.Pions;

import java.util.ArrayList;

public class Cavalier extends Pions{

    public Cavalier(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }

    @Override
    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        int startX = posXDep.charAt(0) - 'a' + 1;
        int endX = posXFin.charAt(0) - 'a' + 1;
        return (Math.abs(startX-endX)== 2 && Math.abs(posYDep-posYFin)==1 || Math.abs(startX-endX)== 1 && Math.abs(posYDep-posYFin)==2);

    }

}