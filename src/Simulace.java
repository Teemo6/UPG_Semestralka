import java.util.List;

public class Simulace {
    public static double maxVzdalenost;

    private List<Planeta> seznamPlanet;
    private final double casovySkok;
    private final double konstantaG;
    private boolean simulationRunning = false;

    public Simulace(List<Planeta> seznamPlanet, double casovySkok, double konstantaG){
        this.seznamPlanet = seznamPlanet;
        this.casovySkok = casovySkok;
        this.konstantaG = konstantaG;
    }

    public void updateSystem(double t){
        double dt_min = t/100 * casovySkok;

        while(t > 0){
            double dt = Math.min(t, dt_min);

            updateAllAcceleration(seznamPlanet, konstantaG);

            for(Planeta p : seznamPlanet){
                //computeAcc(seznamPlanet, vstupDat.getKonstantaG());
                p.setVelocity(0.5 * dt * p.getAccelerationX(), 0.5 * dt * p.getAccelerationY());
                p.setPosition(dt * p.getVelocityX(), dt * p.getVelocityY());
                //computeAcc(seznamPlanet, vstupDat.getKonstantaG());
                p.setVelocity(0.5 * dt * p.getAccelerationX(), 0.5 * dt * p.getAccelerationY());
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
			/*
			if(Double.isNaN(zrychleniX)){
				zrychleniX = 0;
			}
			if(Double.isNaN(zrychleniY)){
				zrychleniY = 0;
			}
			 */
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
