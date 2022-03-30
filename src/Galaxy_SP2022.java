import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Galaxy_SP2022 {

	private static final String VSTUP_SOUBORU = "data/pulsar.csv";
	private static VstupDat VSTUP_DAT;
	private static long startTime = System.currentTimeMillis();

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
				vesmir.repaint();
				startTime = System.currentTimeMillis();
			}
		}, 0, 20);

	}

	public static void main(String[] args) {
		//VSTUP_DAT = new VstupDat(args[0]);
		VSTUP_DAT = new VstupDat(VSTUP_SOUBORU);

		vytvorOknoVizualizace();
	}
}