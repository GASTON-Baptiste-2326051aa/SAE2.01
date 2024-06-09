package echec.Pions;

import java.util.ArrayList;
import java.util.Objects;

public class Pion extends Pions{
    private int deplacement = 0;

    public Pion(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }

    @Override
    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        Position position = new Position();
        int posXFinInt = position.conversionLettreInt(posXFin);
        int posXDepInt = position.conversionLettreInt(posXDep);
        boolean diagonaleEtDroit = Objects.equals(posXDep, posXFin) || (posXDepInt == posXFinInt - 1) || (posXDepInt == posXFinInt + 1);
        if (Objects.equals(super.getCouleur(), "noir")){
            if (this.deplacement == 0 ){
                ++this.deplacement;
                return ((posYDep - posYFin) == 2 || ( posYDep - posYFin) == 1) && diagonaleEtDroit;
            }
            ++this.deplacement;
            return ( posYDep - posYFin) == 1&& diagonaleEtDroit;
        }
        else{
            if (this.deplacement == 0 ){
                ++this.deplacement;
                return ((posYDep - posYFin) == -2 || ( posYDep - posYFin) == -1) && diagonaleEtDroit;
            }
            ++this.deplacement;
            return ( posYDep - posYFin) == -1 && diagonaleEtDroit;
        }
    }

}