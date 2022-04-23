/**
 * Instance třídy {@code Planeta} představuje vesmírné těleso
 * @author Štěpán Faragula 10-04-2022
 * @version 1.21
 */
public class Planeta {
    private String name;
    private String type;
    private DoubleVector2D position;
    private DoubleVector2D velocity;
    private double weight;

    private double radius;
    private DoubleVector2D acceleration;

    /**
     * Nastaví potřebné atributy, vypočítá poloměr podle váhy
     * @param name název
     * @param type typ
     * @param position pozice x, y středu planety
     * @param velocity rychlost x, y
     * @param weight váha
     */
    public Planeta(String name, String type, DoubleVector2D position, DoubleVector2D velocity, double weight){
        this.name = name;
        this.type = type;
        this.position = position;
        this.velocity = velocity;
        this.weight = weight;

        this.radius = Math.cbrt(6*Math.abs(weight)/Math.PI)/2;
     }

     public boolean intersectWith(Planeta otherPlanet){
        double posDiff = (otherPlanet.getPositionX() - this.getPositionX()) * (otherPlanet.getPositionX() - this.getPositionX()) + (otherPlanet.getPositionY() - this.getPositionY()) * (otherPlanet.getPositionY() - this.getPositionY());
        double radDiff = (otherPlanet.getRadius() + this.radius) * (otherPlanet.getRadius() + this.radius);
        return posDiff <= radDiff;
     }

    /**
     * @return název planety
     */
    public String getName(){
        return name;
    }

    /**
     * @return název planety
     */
    public String getType(){
        return type;
    }

    /**
     * @return pozice x středu planety
     */
    public double getPositionX() {
        return position.getX();
    }

    /**
     * @return pozice y středu planety
     */
    public double getPositionY() {
        return position.getY();
    }

    /**
     * @return levý okraj planety
     */
    public double getNegativeRadiusX(){
        return position.getX() - radius;
    }

    /**
     * @return pravý okraj planety
     */
    public double getPositiveRadiusX(){
        return position.getX() + radius;
    }

    /**
     * @return horní okraj planety
     */
    public double getNegativeRadiusY(){
        return position.getY() - radius;
    }

    /**
     * @return dolní okraj planety
     */
    public double getPositiveRadiusY(){
        return position.getY() + radius;
    }

    /**
     * @return intance {@code DoubleVector2D} pozice středu planety
     */
    public DoubleVector2D getPositionVector() {
        return position;
    }

    /**
     * @param x x
     * @param y y
     */
    public void setPosition(double x, double y) {
        this.position = new DoubleVector2D(x, y);
    }

    /**
     * @return rychlost v ose x
     */
    public double getVelocityX() {
        return velocity.getX();
    }

    /**
     * @return rychlost v ose y
     */
    public double getVelocityY() {
        return velocity.getY();
    }

    /**
     * @param x x
     * @param y y
     */
    public void setVelocity(double x, double y) {
        this.velocity = new DoubleVector2D(x, y);
    }

    /**
     * @return váha planety
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return vypočítaný poloměr
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @return zrychlení v ose x
     */
    public double getAccelerationX() {
        return acceleration.getX();
    }

    /**
     * @return zrychlení v ose y
     */
    public double getAccelerationY() {
        return acceleration.getY();
    }

    /**
     * @param x x
     * @param y y
     */
    public void setAcceleration(double x, double y){
        this.acceleration = new DoubleVector2D(x, y);
    }

    /**
     * @return {@code String} v podobě: [Nazev: name, Typ: type, Pozice: (x, y), Rychlost: (x, y), Hmotnost: weight]
     */
    @Override
    public String toString(){
        return "[Nazev: " +name+ ", Typ: " +type+ ", Pozice: " +position+", Rychlost " +velocity+ ", Hmotnost :" +weight+ "]";
    }
}
