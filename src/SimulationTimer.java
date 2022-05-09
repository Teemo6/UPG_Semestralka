import java.util.Timer;
import java.util.TimerTask;

/**
 * Instance třídy {@code Simulace} představuje časovač simulace
 * @author Štěpán Faragula 29-04-2022
 * @version 1.22
 */
public class SimulationTimer {
    private long startTime;
    private long currentTime;
    private long newSimulationTime;
    private long oldSimulatonTime;

    private long startPauseTime;
    private long endPauseTime;
    private long pause;
    private long allPauses;

    public static boolean simulationRunning = true;

    /**
     * Nastaví časovač simulace
     * plánuje výpočty a překreslení plátna
     *
     * @param simulace simulace vesmíru
     * @param vizualizace vizualizace vesmíru
     * @param casovySkok kolik představuje jedna sekunda reálného času sekund simulačních
     */
    public SimulationTimer(Simulace simulace, Vizualizace vizualizace, double casovySkok){
        startTime = System.currentTimeMillis();
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentTime = System.currentTimeMillis();
                simulace.updateOldPlanetMap(currentTime - startTime);

                if(simulationRunning){
                    pause = endPauseTime - startPauseTime;

                    newSimulationTime = currentTime - startTime - pause;
                    simulace.updateSystem(casovySkok * (newSimulationTime - oldSimulatonTime)/1000);
                    simulace.checkAllCollisions();

                    oldSimulatonTime = newSimulationTime;
                    vizualizace.setSimulationTime(newSimulationTime * (long) casovySkok);
                    vizualizace.repaint();

                }
            }
        }, 0, 20);
    }

    /**
     * Umí pozastavit simulaci
     * sčítá všechny pauzy v minuosti
     */
    public void runPauseSimulation(){
        if(simulationRunning)
            startPauseTime = System.currentTimeMillis() + allPauses;
        else
            endPauseTime = System.currentTimeMillis() + allPauses;
        allPauses += pause;
        simulationRunning = !simulationRunning;
    }
}
