import java.util.List;

/**
 * Instance třídy {@code Simulace} představuje
 * @author Štěpán Faragula 01-04-2022
 */
public class Simulace {
    private List<Planeta> seznamPlanet;
    private final double casovySkok;
    private final double konstantaG;

    public Simulace(List<Planeta> seznamPlanet, double casovySkok, double konstantaG){
        this.seznamPlanet = seznamPlanet;
        this.casovySkok = casovySkok;
        this.konstantaG = konstantaG;
    }

    /**
     * 
     * @param t
     */
    public void updateSystem(double t){
        double velocityX, velocityY, positionX, positionY;
        double dt_min = seznamPlanet.size() * casovySkok/100000.0;

        while(t > 0){
            double dt = Math.min(t, dt_min);

            updateAllAcceleration(seznamPlanet);

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
     * Nastaví všem planetám z předaného seznamu zrychlení x, y
     */
    public void updateAllAcceleration(List<Planeta> seznamPlanet){
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
}
