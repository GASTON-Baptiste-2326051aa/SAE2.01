package echec.Pions;

public class Pion extends Pions{

    public Pion(String couleur, String posX, int posY) {
        super(couleur, posX, posY);
    }

    public void deplacement(String posXDep, int posYDep, String posXFin, int posYFin){

    }

    @Override
    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        int startX = posXDep.charAt(0) - 'a' + 1;
        int endX = posXFin.charAt(0) - 'a' + 1;

        return (posYDep - posYFin == 1);
    }

}