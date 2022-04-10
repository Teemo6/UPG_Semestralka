import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Galaxy_SP2022 {

	private static final String VSTUP_SOUBORU = "data/pulsar.csv";
	private static final VstupDat VSTUP_DAT = VstupDat.getInstance();
	private static long startTime = System.currentTimeMillis();

	public static void vytvorOknoVizualizace(){
		JFrame okno = new JFrame();
		okno.setTitle("A21B0119P - Stepan Faragula");
		okno.setSize(800, 600);

		Simulace simulaceVesmiru = new Simulace(VSTUP_DAT.getSeznamPlanet(), VSTUP_DAT.getCasovySkok(), VSTUP_DAT.getKonstantaG());
		Vizualizace vizualizaceVesmiru = new Vizualizace(VSTUP_DAT.getSeznamPlanet(), startTime);

		okno.add(vizualizaceVesmiru);
		okno.pack();

		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null);
		okno.setVisible(true);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				long currentTime = System.currentTimeMillis();
				vizualizaceVesmiru.simulationTime += VSTUP_DAT.getCasovySkok();

				if(simulaceVesmiru.isSimulationRunning()){
					simulaceVesmiru.updateSystem(4);
					vizualizaceVesmiru.repaint();
				}

				startTime = System.currentTimeMillis();
			}
		}, 0, 20);

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (e.getID() == KeyEvent.KEY_PRESSED
								&& e.getKeyCode() == KeyEvent.VK_SPACE) {
							simulaceVesmiru.runPauseSimulation();
						}

						return false;
					}
				});

		vizualizaceVesmiru.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Planeta planetHit = vizualizaceVesmiru.getHitPlanet(e.getX(), e.getY());
				if(planetHit != null){
					vizualizaceVesmiru.showSelectedPlanet(planetHit);
				}
			}

		});
	}

	public static void main(String[] args) {
		//VSTUP_DAT.nactiData(args[0]);
		VSTUP_DAT.nactiData(VSTUP_SOUBORU);

		vytvorOknoVizualizace();
	}
}