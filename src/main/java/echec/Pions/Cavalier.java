package echec.Pions;

public class Cavalier extends Pions{

    public Cavalier(String couleur, String posX, int posY, String url) {
        super(couleur, posX, posY, url);
    }

    public void deplacement(String posXDep, int posYDep, String posXFin, int posYFin){

    }

    @Override
    public boolean peutDeplacer(int posYDep, String posXDep, int posYFin, String posXFin) {
        return false;
    }

}