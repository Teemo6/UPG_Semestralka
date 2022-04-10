public class DoubleVector2D {
    private double x;
    private double y;

    public DoubleVector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double computeDistance(DoubleVector2D otherVector){
        double posXDiff = otherVector.getX() - this.getX();
        double posYDiff = otherVector.getY() - this.getY();
        return Math.sqrt(posXDiff * posXDiff + posYDiff * posYDiff);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString(){
        return "(" +x+ ", " +y+")";
    }
}
