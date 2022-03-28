public class Planeta {
    private String nazev;
    private String typ;
    private double posX;
    private double posY;
    private double velX;
    private double velY;
    private double hmotnost;

    public Planeta(String nazev, String typ, double posX, double posY, double velX, double velY, double hmotnost){
        this.nazev = nazev;
        this.typ = typ;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.hmotnost = hmotnost;
    }

    @Override
    public String toString(){
        return "[Nazev: " +nazev+ ", Typ: " +typ+ ", Pozice: (" +posX+ ", " +posY+ "), Rychlost: (" +velX+ ", " +velY+ "), Hmotnost: " +hmotnost+ "]";
    }
}
