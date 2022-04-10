import java.util.List;

public class Simulace {
    public static double maxVzdalenost;
    public long simulacniCas;
    public long starySimulacniCas;

    private List<Planeta> seznamPlanet;
    private final double casovySkok;
    private final double konstantaG;
    private boolean simulationRunning = true;

    public Simulace(List<Planeta> seznamPlanet, double casovySkok, double konstantaG){
        this.seznamPlanet = seznamPlanet;
        this.casovySkok = casovySkok;
        this.konstantaG = konstantaG;
    }

    public void updateSystem(double t){
        double velocityX, velocityY, positionX, positionY;
        double dt_min = casovySkok/1000000;

        while(t > 0){
            double dt = Math.min(t, dt_min);

            updateAllAcceleration(seznamPlanet, konstantaG);

            for(Planeta p : seznamPlanet){
                //updateAllAcceleration(seznamPlanet, konstantaG);

                velocityX = p.getVelocityX() + 0.5 * dt * p.getAccelerationX();
                velocityY = p.getVelocityY() + 0.5 * dt * p.getAccelerationY();
                p.setVelocity(velocityX, velocityY);

                positionX = p.getPositionX() + dt * p.getVelocityX();
                positionY = p.getPositionY() + dt * p.getVelocityY();
                p.setPosition(positionX, positionY);

                //updateAllAcceleration(seznamPlanet, konstantaG);

                velocityX = p.getVelocityX() + 0.5 * dt * p.getAccelerationX();
                velocityY = p.getVelocityY() + 0.5 * dt * p.getAccelerationY();
                p.setVelocity(velocityX, velocityY);
            }
            t -= dt;
        }
    }

    public void updateAllAcceleration(List<Planeta> seznamPlanet, double G){
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

                if(maxVzdalenost < vektorDelka){
                    maxVzdalenost = vektorDelka;
                }

                //System.out.println(maxVzdalenost);
            }

            zrychleniX *= G;
            zrychleniY *= G;

            iPlaneta.setAcceleration(zrychleniX, zrychleniY);
        }
    }

    public boolean isSimulationRunning() {
        return simulationRunning;
    }

    public void runPauseSimulation(){
        simulationRunning = !simulationRunning;
    }

}
