public class Galaxy_SP2022 {

	private static final String VSTUP_SOUBORU = "data/pulsar.csv";
	private static VstupDat VSTUP_DAT;

	public static void main(String[] args) {
		//VSTUP_DAT = new VstupDat(args[0]);
		VSTUP_DAT = new VstupDat(VSTUP_SOUBORU);
		System.out.println(VSTUP_DAT.getKonstantaG());
		System.out.println(VSTUP_DAT.getCasovySkok());
		for(Planeta p : VSTUP_DAT.getSeznamPlanet()){
			System.out.println(p);
		}
	}
}