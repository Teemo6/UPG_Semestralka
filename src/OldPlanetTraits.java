/**
 * Instance třídy {@code OldPlanetTraits} představuje přepravku pro uložení potřebných dat k zobrazení grafu a trajektorií
 * @author Štěpán Faragula 09-05-2022
 * @version 1.29
 */
public class OldPlanetTraits {

    // Rychlost, pozice
    private double oldVelocity;
    private DoubleVector2D oldPosition;

    /**
     * Nastaví atributy
     * @param oldVelocity stará rychlost
     * @param oldPosition stará pozice
     */
    public OldPlanetTraits(double oldVelocity, DoubleVector2D oldPosition){
        this.oldVelocity = oldVelocity;
        this.oldPosition = oldPosition;
    }

    /**
     * @return rychlost v km/h
     */
    public double getOldVelocity() {
        return oldVelocity;
    }

    /**
     * @return pozice planety
     */
    public DoubleVector2D getOldPosition() {
        return oldPosition;
    }
}
