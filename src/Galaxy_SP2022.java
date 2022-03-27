import java.util.List;

public class Galaxy_SP2022 {


	/**
	 * Precte data na zpracovani a zapise zpracovana data do souboru podle volby
	 * @param vstupniSoubor jaky soubor se ma cist
	 */
	private static void zpracujDataAZapisVysledky(String vstupniSoubor){
		VstupDat vstupDat = VstupDat.getInstance();
		List<Planeta> seznam = vstupDat.nactiPlanety(vstupniSoubor);

		for(Planeta planeta : seznam){
			System.out.println(planeta);
		}
	}

	public static void main(String[] args) {
		zpracujDataAZapisVysledky("data/pulsar.csv");
	}
}
