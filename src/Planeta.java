import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    // Mapa posledních planet
    private TreeMap<Long, OldPlanetTraits> oldPlanetMap;
    private List<DoubleVector2D> oldPositionList;
    private Long lastTime;
    private Double lastVelocity;
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

        this.oldPlanetMap = new TreeMap<>();
        this.oldPositionList = new ArrayList<>();
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
     * Přidá záznamy do mapy
     * převede rychlost na skalár s jednotkama v km/h
     * poslední pozice se bere jako levý horní roh pro budoucí vykreslení
     * @param time čas v ms
     */
    public void addRecordToMap(Long time){
        lastTime = time;
        lastVelocity = Math.sqrt(getVelocityX() * getVelocityX() + getVelocityY() * getVelocityY()) * 3.6;
        lastPosition = new DoubleVector2D(getNegativeRadiusX(), getNegativeRadiusY());

        oldPlanetMap.put(lastTime, new OldPlanetTraits(lastVelocity, lastPosition));

        if(oldPlanetMap.lastKey() - oldPlanetMap.firstKey() > 30000){
            oldPlanetMap.pollFirstEntry();
        }

        oldPositionList = oldPlanetMap.entrySet().stream()
                .filter(a -> (oldPlanetMap.lastKey() - a.getKey() <= 1000))
                .map(e -> e.getValue().getOldPosition()).collect(Collectors.toList());
    }

    public List<DoubleVector2D> getOldPositionList(){
        return oldPositionList;
    }

    /**
     * @return mapa starých atributů
     */
    public TreeMap<Long, OldPlanetTraits> getOldPlanetMap(){
        return oldPlanetMap;
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
