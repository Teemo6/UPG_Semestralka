/**
 * Instance třídy {@code DoubleVector2D} představuje přepravku pro body na ose x a y
 * @author Štěpán Faragula 10-04-2022
 * @version 1.21
 */

public class DoubleVector2D {
    // Hodnoty x, y
    private double x;
    private double y;

    /**
     * Nastaví atributy x, y
     * @param x hodnota x
     * @param y hodnota y
     */
    public DoubleVector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Vrátí vzdálenost dvou instancí {@code DoubleVector2D}
     * @param otherVector instance jiného vektoru
     * @return vzdálenost mezi touto instancí {@code DoubleVector2D} a jinou instancí {@code DoubleVector2D}
     */
    public double computeDistance(DoubleVector2D otherVector){
        double posXDiff = otherVector.getX() - this.x;
        double posYDiff = otherVector.getY() - this.y;
        return Math.sqrt(posXDiff * posXDiff + posYDiff * posYDiff);
    }

    /**
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * @return {@code String} v podobě: (x, y)
     */
    @Override
    public String toString(){
        return "(" +x+ ", " +y+")";
    }
}
