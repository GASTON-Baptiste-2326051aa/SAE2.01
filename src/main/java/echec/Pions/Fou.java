package echec.Pions;

public class Fou extends Pions{

    public Fou(String couleur, String posY, int posX, String url) {
        super(couleur, posY, posX, url);
    }

    public void deplacement(String posXDep, int posYDep, String posXFin, int posYFin){

    }

    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        int startX = posXDep.charAt(0) - 'a' + 1;
        int endX = posXFin.charAt(0) - 'a' + 1;

        return (Math.abs(startX - endX) == Math.abs(posYDep - posYFin));
    }

}