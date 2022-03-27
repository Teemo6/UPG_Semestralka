public class Rychlost {
    private double velX;
    private double velY;

    public Rychlost(double x, double y){
        this.velX = x;
        this.velY = y;
    }

    public double getVelY() {
        return velY;
    }

    public double getVelX() {
        return velX;
    }

    @Override
    public String toString(){
        return "(" +velX+ ", " +velY+ ")";
    }
}
