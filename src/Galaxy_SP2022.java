public class Galaxy_SP2022 {

	private static final String VSTUP_SOUBORU = "data/pulsar.csv";
	private static final VstupDat VSTUP_DAT = new VstupDat(VSTUP_SOUBORU);

	public static void main(String[] args) {
		System.out.println(VSTUP_DAT.getKonstantaG());
		System.out.println(VSTUP_DAT.getCasovySkok());
		for(Planeta p : VSTUP_DAT.getSeznamPlanet()){
			System.out.println(p);
		}
	}
}