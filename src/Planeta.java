public class Planeta {
    private String name;
    private String type;
    private DoubleVector2D position;
    private DoubleVector2D velocity;
    private double weight;

    private double radius;
    private DoubleVector2D acceleration;

    public Planeta(String name, String type, DoubleVector2D position, DoubleVector2D velocity, double weight){
        this.name = name;
        this.type = type;
        this.position = position;
        this.velocity = velocity;
        this.weight = weight;

        this.radius = Math.cbrt(6*weight/Math.PI)/2;
        this.acceleration = new DoubleVector2D(0, 0);
     }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public double getPositionX() {
        return position.getX();
    }

    public double getPositionY() {
        return position.getY();
    }

    public DoubleVector2D getPositionVector() {
        return position;
    }

    public void setPosition(double x, double y) {
        this.position = new DoubleVector2D(x, y);
    }

    public double getVelocityX() {
        return velocity.getX();
    }

    public double getVelocityY() {
        return velocity.getY();
    }

    public void setVelocity(double x, double y) {
        this.velocity = new DoubleVector2D(x, y);
    }

    public double getWeight() {
        return weight;
    }

    public double getRadius() {
        return radius;
    }

    public double getAccelerationX() {
        return acceleration.getX();
    }

    public double getAccelerationY() {
        return acceleration.getY();
    }

    public void setAcceleration(double accX, double accY){
        this.acceleration = new DoubleVector2D(accX, accY);
    }

    public void setAcceleration(DoubleVector2D acc){
        this.acceleration = acc;
    }

    @Override
    public String toString(){
        return "[Nazev: " +name+ ", Typ: " +type+ ", Pozice: " +position+", Rychlost " +velocity+ ", Hmotnost :" +weight+ "]";
    }
}
