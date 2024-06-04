package echec.Pions;

public class Reine extends Pions{

    public Reine(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }

    @Override
    public void deplacement(String posXDep, int posYDep, String posXFin, int posYFin){

    }

    @Override
    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        int startX = posXDep.charAt(0) - 'a' + 1;
        int endX = posXFin.charAt(0) - 'a' + 1;

        return (Math.abs(startX - endX) == Math.abs(posYDep - posYFin) || (startX == endX || posYDep == posYFin));
    }

}