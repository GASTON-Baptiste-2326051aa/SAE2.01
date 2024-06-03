package echec.Pions;

public class Tour extends Pions{

    public Tour(String couleur, String posX, int posY) {
        super(couleur, posX, posY);
    }
    public void deplacement(String posXDep, int posYDep, String posXFin, int posYFin){

    }

    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        int startX = posXDep.charAt(0) - 'a' + 1;
        int endX = posXFin.charAt(0) - 'a' + 1;

        return (startX == endX || posXDep == posXFin);
    }

}