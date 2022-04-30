import java.util.TreeMap;

/**
 * Instance třídy {@code Planeta} představuje vesmírné těleso
 * @author Štěpán Faragula 30-04-2022
 * @version 1.24
 */
public class Planeta {
    // Nastavené hodnoty
    private String name;
    private String type;
    private DoubleVector2D position;
    private DoubleVector2D velocity;
    private double weight;

    // Vypočítané hodnoty
    private double radius;
    private DoubleVector2D acceleration;

    // Poslední uložený čas
    private Long lastTime;

    // Rychlost v čase
    private TreeMap<Long, Double> velocityMap;
    private Double lastVelocity;

    // Pozice v čase
    private TreeMap<Long, DoubleVector2D> positionMap;
    private DoubleVector2D lastPosition;

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

        this.velocityMap = new TreeMap<>();
        this.positionMap = new TreeMap<>();
     }

    /**
     * Vyhodnotí jestli instance koliduje s jinou planetou
     * @param otherPlanet druhá planeta
     * @return true pokud spolu kolidují
     */
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
     * Přidá záznamy do map rychlosti a pozice
     * převede rychlost na skalár s jednotkama v km/h
     * poslední pozice se bere jako levý horní roh pro budoucí vykreslení
     * @param time čas v ms
     */
    public void addRecordToMap(Long time){
        lastTime = time;
        lastVelocity = Math.sqrt(getVelocityX() * getVelocityX() + getVelocityY() * getVelocityY()) * 3.6;
        lastPosition = new DoubleVector2D(getNegativeRadiusX(), getNegativeRadiusY());

        velocityMap.put(lastTime, lastVelocity);
        positionMap.put(lastTime, lastPosition);

        if(velocityMap.lastKey() - velocityMap.firstKey() > 30000){
            velocityMap.remove(velocityMap.firstKey());
        }

        if(positionMap.lastKey() - positionMap.firstKey() > 1000){
            positionMap.remove(positionMap.firstKey());
        }
    }

    /**
     * @return mapa rychlostí
     */
    public TreeMap<Long, Double> getVelociyMap(){
        return velocityMap;
    }

    /**
     * @return mapa pozic
     */
    public TreeMap<Long, DoubleVector2D> getPositionMap(){
        return positionMap;
    }

    /**
     * @return poslední čas uložený do mapy
     */
    public Long getLastTime(){
        return lastTime;
    }

    /**
     * @return poslední rychlost uložená do mapy
     */
    public Double getLastVelocity(){
        return lastVelocity;
    }

    /**
     * @return poslední pozice uložená do mapy
     */
    public DoubleVector2D getLastPosition(){
        return lastPosition;
    }

    /**
     * @return {@code String} v podobě: [Nazev: name, Typ: type, Pozice: (x, y), Rychlost: (x, y), Hmotnost: weight]
     */
    @Override
    public String toString(){
        return "[Nazev: " +name+ ", Typ: " +type+ ", Pozice: " +position+", Rychlost " +velocity+ ", Hmotnost :" +weight+ "]";
    }
}
