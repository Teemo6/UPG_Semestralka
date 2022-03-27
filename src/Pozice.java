public class Pozice {
    private double posX;
    private double posY;

    public Pozice(double x, double y){
        this.posX = x;
        this.posY = y;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosX() {
        return posX;
    }

    @Override
    public String toString(){
        return "(" +posX+ ", " +posY+ ")";
    }
}
