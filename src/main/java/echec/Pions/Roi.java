package echec.Pions;

public class Roi extends Pions{

    public Roi(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }

    public void deplacement(String posXDep, int posYDep, String posXFin, int posYFin){

    }

    @Override
    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        int startX = posXDep.charAt(0) - 'a' + 1;
        int endX = posXFin.charAt(0) - 'a' + 1;

        return (Math.abs(startX - endX) == 1 && posYDep == posYFin) || (Math.abs(posYDep - posYFin) == 1 && startX == endX || Math.abs(posYDep - posYFin) == 1 && Math.abs(startX - endX) == 1);
    }

}