import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class Galaxy_SP2022 {

	private static final String VSTUP_SOUBORU = "data/pulsar.csv";
	private static VstupDat VSTUP_DAT;
	private static long startTime = System.currentTimeMillis();
	private static boolean simulationRunning = true;

	public static void vytvorOknoVizualizace(){
		JFrame okno = new JFrame();
		okno.setTitle("Vesmir");
		okno.setSize(800, 600);

		VizualizaceVesmiru vesmir = new VizualizaceVesmiru(VSTUP_DAT, startTime);

		okno.add(vesmir); //prida komponentu
		okno.pack(); //udela resize okna dle komponent

		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null); //vycentrovat na obrazovce
		okno.setVisible(true);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				long currentTime = System.currentTimeMillis();
				vesmir.simulationTime += VSTUP_DAT.getCasovySkok();

				if(simulationRunning){
					vesmir.updateSystem(VSTUP_DAT.getCasovySkok());
					vesmir.repaint();
				}

				startTime = System.currentTimeMillis();
			}
		}, 0, 20);

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (e.getID() == KeyEvent.KEY_PRESSED
								&& e.getKeyCode() == KeyEvent.VK_SPACE) {
							runPauseSimulation();
						}

						return false;
					}
				});

		vesmir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(vesmir.isPlanetHit(e.getX(), e.getY())){
					System.out.println("ahoj");
				}
			}

		});
	}

	public static void runPauseSimulation(){
		simulationRunning = !simulationRunning;
	}

	public static void main(String[] args) {
		//VSTUP_DAT = new VstupDat(args[0]);
		VSTUP_DAT = new VstupDat(VSTUP_SOUBORU);

		vytvorOknoVizualizace();
	}
}