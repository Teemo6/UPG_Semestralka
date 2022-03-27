public class Planeta {
    private String nazev;
    private String typ;
    private Pozice pozice;
    private Rychlost rychlost;
    private double hmotnost;

    public Planeta(String nazev, String typ, Pozice pozice, Rychlost rychlost, double hmotnost){
        this.nazev = nazev;
        this.typ = typ;
        this.pozice = pozice;
        this.rychlost = rychlost;
        this.hmotnost = hmotnost;
    }

    @Override
    public String toString(){
        return "[Nazev: " +nazev+ ", Typ: " +typ+ ", Pozice: " +pozice.toString()+ ", Rychlost: " +rychlost.toString()+ ", Hmotnost: " +hmotnost+ "]";
    }
}
