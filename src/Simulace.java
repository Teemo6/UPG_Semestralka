import java.util.ArrayList;
import java.util.List;

/**
 * Instance třídy {@code Simulace} představuje potřebné výpočty pro simulaci vesmíru
 * @author Štěpán Faragula 10-04-2022
 * @version 1.21
 */
public class Simulace {
    private List<Planeta> seznamPlanet;
    private final double casovySkok;
    private final double konstantaG;

    /**
     * Nastaví potřebné atributy
     * @param seznamPlanet seznam planet
     * @param casovySkok časový skok ze vstupního souboru
     * @param konstantaG konstanta G ze vstupního souboru
     */
    public Simulace(List<Planeta> seznamPlanet, double casovySkok, double konstantaG){
        this.seznamPlanet = seznamPlanet;
        this.casovySkok = casovySkok;
        this.konstantaG = konstantaG;
    }

    /**
     * Nastaví všem planetám pozici, rychlost a zrychlení
     *
     * využívá služeb metody {@code updateAllAcceleration()}
     * @param t čas který uběhl od posledního volání metody {@code updateSystem()}
     */
    public void updateSystem(double t){
        double velocityX, velocityY, positionX, positionY;
        double dt_min = seznamPlanet.size() * casovySkok/100.0;

        while(t > 0){
            double dt = Math.min(t, dt_min);

            updateAllAcceleration();

            for(Planeta p : seznamPlanet){
                velocityX = p.getVelocityX() + 0.5 * dt * p.getAccelerationX();
                velocityY = p.getVelocityY() + 0.5 * dt * p.getAccelerationY();
                p.setVelocity(velocityX, velocityY);

                positionX = p.getPositionX() + dt * p.getVelocityX();
                positionY = p.getPositionY() + dt * p.getVelocityY();
                p.setPosition(positionX, positionY);

                velocityX = p.getVelocityX() + 0.5 * dt * p.getAccelerationX();
                velocityY = p.getVelocityY() + 0.5 * dt * p.getAccelerationY();
                p.setVelocity(velocityX, velocityY);
            }
            t -= dt;
        }
    }

    /**
     * Vypočítá všem planetám zrychlení x, y
     */
    public void updateAllAcceleration(){
        for(Planeta iPlaneta : seznamPlanet){
            double zrychleniX = 0;
            double zrychleniY = 0;

            for(Planeta jPlaneta : seznamPlanet){
                if(iPlaneta.equals(jPlaneta)) continue;

                double posXDiff = jPlaneta.getPositionX() - iPlaneta.getPositionX();
                double posYDiff = jPlaneta.getPositionY() - iPlaneta.getPositionY();

                double vektorDelka = iPlaneta.getPositionVector().computeDistance(jPlaneta.getPositionVector());
                double thirdPower = vektorDelka * vektorDelka * vektorDelka;

                zrychleniX += jPlaneta.getWeight() * (posXDiff / thirdPower);
                zrychleniY += jPlaneta.getWeight() * (posYDiff / thirdPower);
            }

            zrychleniX *= konstantaG;
            zrychleniY *= konstantaG;

            iPlaneta.setAcceleration(zrychleniX, zrychleniY);
        }
    }

    public void updateVelocityMap(Double time){
        seznamPlanet.forEach(p -> p.addRecordToMap(time));
    }

    public void checkAllCollisions(){
        for(int i = 0; i < seznamPlanet.size(); i++){
            for(int j = i+1; j < seznamPlanet.size(); j++){
                if(seznamPlanet.get(i).intersectWith(seznamPlanet.get(j))){
                    Planeta mensiPlaneta = seznamPlanet.get(i).getRadius() <= seznamPlanet.get(j).getRadius() ? seznamPlanet.get(i) : seznamPlanet.get(j);
                    Planeta vetsiPlaneta = seznamPlanet.get(i).getRadius() >  seznamPlanet.get(j).getRadius() ? seznamPlanet.get(i) : seznamPlanet.get(j);

                    double iPart = vetsiPlaneta.getRadius() / (mensiPlaneta.getRadius() + vetsiPlaneta.getRadius());

                    double posX = mensiPlaneta.getPositionX() + (vetsiPlaneta.getPositionX() - mensiPlaneta.getPositionX()) * iPart;
                    double posY = mensiPlaneta.getPositionY() + (vetsiPlaneta.getPositionY() - mensiPlaneta.getPositionY()) * iPart;

                    double velX = (mensiPlaneta.getWeight()*mensiPlaneta.getVelocityX() + vetsiPlaneta.getWeight()*vetsiPlaneta.getVelocityX())/(mensiPlaneta.getWeight() + vetsiPlaneta.getWeight());
                    double velY = (mensiPlaneta.getWeight()*mensiPlaneta.getVelocityY() + vetsiPlaneta.getWeight()*vetsiPlaneta.getVelocityY())/(mensiPlaneta.getWeight() + vetsiPlaneta.getWeight());

                    Planeta newPlaneta = new Planeta(
                            "C(" + mensiPlaneta.getName() + "_" + vetsiPlaneta.getName() + ")",
                            "Planeta",
                            new DoubleVector2D(posX, posY),
                            new DoubleVector2D(velX, velY),
                            vetsiPlaneta.getWeight() + mensiPlaneta.getWeight());

                    seznamPlanet.set(seznamPlanet.indexOf(seznamPlanet.get(i)), newPlaneta);
                    seznamPlanet.remove(seznamPlanet.get(j));
                    break;
                }
            }
        }
    }
}
