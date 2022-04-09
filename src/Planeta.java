public class Planeta {
    public String nazev;
    public String typ;
    public double posX;
    public double posY;
    public double velX;
    public double velY;
    public double hmotnost;
    public double polomer;

    public double accX;
    public double accY;

    public Planeta(String nazev, String typ, double posX, double posY, double velX, double velY, double hmotnost){
        this.nazev = nazev;
        this.typ = typ;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.hmotnost = hmotnost;
        this.polomer = Math.cbrt(6*hmotnost/Math.PI)/2;
        this.accX = 0;
        this.accY = 0;
    }

    public void setAcceleration(double accX, double accY){
        this.accX = accX;
        this.accY = accY;
    }

    @Override
    public String toString(){
        return "[Nazev: " +nazev+ ", Typ: " +typ+ ", Pozice: (" +posX+ ", " +posY+ "), Rychlost: (" +velX+ ", " +velY+ "), Hmotnost: " +hmotnost+ "]";
    }
}
