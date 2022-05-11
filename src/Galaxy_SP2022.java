import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Semestrální práce z UPG
 *
 * vizualizace simulace vesmíru
 * @author Štěpán Faragula 29-04-2022
 * @version 1.24
 */
public class Galaxy_SP2022 {
	// Testovací atribut
	private static final String VSTUP_SOUBORU = "data/pulsar.csv";

	private static final VstupDat VSTUP_DAT = VstupDat.getInstance();

	public static JFrame okno;

	/**
	 * Spustí simulaci
	 */
	public static void vytvorOknoVizualizace(){
		okno = new JFrame();
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
				// Levé kliknutí
				if(SwingUtilities.isLeftMouseButton(e)){
					Planeta planetHit = vizualizaceVesmiru.getPlanetHit(e.getX(), e.getY());
					vizualizaceVesmiru.showSelectedPlanet(planetHit);
					vizualizaceVesmiru.repaint();
				}

				// Pravé kliknutí
				if(SwingUtilities.isRightMouseButton(e)){
					Planeta planetHit = vizualizaceVesmiru.getPlanetHit(e.getX(), e.getY());
					if(planetHit != null) Graf.vytvorOknoGrafu(planetHit);
					vizualizaceVesmiru.repaint();
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

		// Testovací spuštění
		VSTUP_DAT.nactiData(VSTUP_SOUBORU);
		vytvorOknoVizualizace();
	}
}