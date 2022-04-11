import java.util.Timer;
import java.util.TimerTask;

public class SimulationTimer {
    private long startTime;
    private long newSimulationTime;
    private long oldSimulatonTime;
    private long startPauseTime;
    private long endPauseTime;
    private long pause;
    private long allPauses;

    private boolean simulationRunning = true;

    public SimulationTimer(Simulace simulace, Vizualizace vizualizace, double casovySkok){
        startTime = System.currentTimeMillis();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                if(simulationRunning){
                    pause = endPauseTime - startPauseTime;
                    newSimulationTime = currentTime - startTime - pause;
                    simulace.updateSystem(casovySkok * (newSimulationTime - oldSimulatonTime)/1000);
                    oldSimulatonTime = newSimulationTime;

                    vizualizace.setSimulationTime(newSimulationTime * (long) casovySkok);
                    vizualizace.repaint();
                }
            }
        }, 0, 20);
    }

    public void runPauseSimulation(){
        if(simulationRunning)
            startPauseTime = System.currentTimeMillis() + allPauses;
        else
            endPauseTime = System.currentTimeMillis() + allPauses;
        allPauses += pause;
        simulationRunning = !simulationRunning;
    }
}
