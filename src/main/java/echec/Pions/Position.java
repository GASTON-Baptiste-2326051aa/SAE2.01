package echec.Pions;

// Class de conversion de position
public class Position {
    private int i = -1;
    private int j = -1;
    private int x = -1;
    private int y = -1;

    // COnvertion de coordonnée d'image en x et y en coordonnée de matrice i et j
    public void conversion(int x, int y){
        this.j = x/100;
        this.i = y/100;
    }

    // Conversion des lettre de l'échiquier en chiffre
    public int conversionLettreInt(String lettre){
        int resultat;
        switch (lettre.toLowerCase()) {
            case "a":
                resultat = 0;
                break;
            case "b":
                resultat = 1;
                break;
            case "c":
                resultat = 2;
                break;
            case "d":
                resultat = 3;
                break;
            case "e":
                resultat = 4;
                break;
            case "f":
                resultat = 5;
                break;
            case "g":
                resultat = 6;
                break;
            case "h":
                resultat = 7;
                break;
            default:
                resultat = -1;
                break;
        }
        return resultat;
    }

    // Conversion des chiffre en case d'échiquier
    public String conversionIntLettre(int chiffre){
        String resultat;
        switch (chiffre) {
            case 0:
                resultat = "a";
                break;
            case 1:
                resultat = "b";
                break;
            case 2:
                resultat = "c";
                break;
            case 3:
                resultat = "d";
                break;
            case 4:
                resultat = "e";
                break;
            case 5:
                resultat = "f";
                break;
            case 6:
                resultat = "g";
                break;
            case 7:
                resultat = "h";
                break;
            default:
                resultat = "";
                break;
        }
        return resultat;
    }


    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // reset des postions
    public void reset(){
        this.i = -1;
        this.j = -1;
        this.x = -1;
        this.y = -1;
    }
}
