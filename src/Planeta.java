public class Planeta {
    public String nazev;
    public String typ;
    public double posX;
    public double posY;
    public double velX;
    public double velY;
    public double hmotnost;

    public Planeta(String nazev, String typ, double posX, double posY, double velX, double velY, double hmotnost){
        this.nazev = nazev;
        this.typ = typ;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.hmotnost = hmotnost;
    }

    public double[] getCenterPos(){
        return new double[]{posX + hmotnost/2, posY + hmotnost/2};
    }

    @Override
    public String toString(){
        return "[Nazev: " +nazev+ ", Typ: " +typ+ ", Pozice: (" +posX+ ", " +posY+ "), Rychlost: (" +velX+ ", " +velY+ "), Hmotnost: " +hmotnost+ "]";
    }
}
