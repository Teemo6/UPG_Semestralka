import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Semestrální práce z UPG
 *
 * vizualizace simulace vesmíru
 * @author Štěpán Faragula 10-04-2022
 * @version 1.21
 */
public class Galaxy_SP2022 {
	// Testovací atribut
	private static final String VSTUP_SOUBORU = "data/collision.csv";

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
				vizualizaceVesmiru.showHitPlanet(e.getX(), e.getY());
				vizualizaceVesmiru.repaint();
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