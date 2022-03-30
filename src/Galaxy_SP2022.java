import javax.swing.*;

public class Galaxy_SP2022 {

	private static final String VSTUP_SOUBORU = "data/collision.csv";
	private static VstupDat VSTUP_DAT;

	public static void vytvorOknoVizualizace(){
		JFrame okno = new JFrame();
		okno.setTitle("Vesmir");
		okno.setSize(800, 600);

		VizualizaceVesmiru vesmir = new VizualizaceVesmiru(VSTUP_DAT.getSeznamPlanet());

		okno.add(vesmir); //prida komponentu
		okno.pack(); //udela resize okna dle komponent

		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null); //vycentrovat na obrazovce
		okno.setVisible(true);

	}

	public static void main(String[] args) {
		//VSTUP_DAT = new VstupDat(args[0]);
		VSTUP_DAT = new VstupDat(VSTUP_SOUBORU);

		vytvorOknoVizualizace();

		for(Planeta p : VSTUP_DAT.getSeznamPlanet()){
			System.out.println(p);
		}
	}
}