import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Semestrální práce z UPG
 *
 * vizualizace simulace vesmíru
 * @author Štěpán Faragula 28-03-2022
 */
public class Galaxy_SP2022 {
	private static final String VSTUP_SOUBORU = "data/pulsar.csv";
	private static final VstupDat VSTUP_DAT = VstupDat.getInstance();

	/**
	 * Spustí simulaci
	 */
	public static void vytvorOknoVizualizace(){
		JFrame okno = new JFrame();
		okno.setTitle("A21B0119P - Stepan Faragula");
		okno.setSize(800, 600);

		Simulace simulaceVesmiru = new Simulace(VSTUP_DAT.getSeznamPlanet(), VSTUP_DAT.getCasovySkok(), VSTUP_DAT.getKonstantaG());
		Vizualizace vizualizaceVesmiru = new Vizualizace(VSTUP_DAT.getSeznamPlanet());

		okno.add(vizualizaceVesmiru);
		okno.pack();

		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null);
		okno.setVisible(true);

		SimulationTimer timer = new SimulationTimer(simulaceVesmiru, vizualizaceVesmiru, VSTUP_DAT.getCasovySkok());

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
			if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_SPACE) {
				timer.runPauseSimulation();
			}

			return false;
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

	/**
	 * Načte data a spustí simulaci
	 * @param args data simulace
	 */
	public static void main(String[] args) {
		//VSTUP_DAT.nactiData(args[0]);
		VSTUP_DAT.nactiData(VSTUP_SOUBORU);
		vytvorOknoVizualizace();
	}
}