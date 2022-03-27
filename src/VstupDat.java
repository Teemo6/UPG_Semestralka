import java.util.*;
import java.io.*;

/**
 * Instance třídy {@code VstupDat} představuje jedináčka který čte vstupní soubor a vytváří požadovaný seznam objektů
 * @author Štěpán Faragula 2021-12-09
 */
public class VstupDat{

    /** Jediná instance tříy {@code VstupDat} */
    private static final VstupDat INSTANCE = new VstupDat();

    /**
     * Vrací instanci {@code VstupDat}
     * @return instance jedináčka {@code VstupDat}
     */
    public static VstupDat getInstance(){
        return INSTANCE;
    }

    /**
     * Načte z textového souboru údaje o všech rozvrhových akcích
     * a vrátí je jako seznam objektů třídy {@code RozvrhovaAkce}
     * využívá služeb metody {@code vytvorRozvrhovouAkci()}
     *
     * @param celeJmenoSouboru úplné jméno souboru včetně cesty
     * @return seznam rozvrhových akcí
     */
    public List<Planeta> nactiPlanety(String celeJmenoSouboru) {
        String read;
        List<Planeta> seznam = new ArrayList<>();

        try {
            FileReader fr = new FileReader(celeJmenoSouboru);
            BufferedReader br = new BufferedReader(fr);

            br.readLine();

            while ((read = br.readLine()) != null) {
                seznam.add(vytvorPlanetu(read));
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return seznam;
    }

    /**
     * Z řetězce, ve kterém je uložena informace o rozvrhové akci, vyrobí
     * objekt {@code RozvrhovaAkce}
     * Formát řetězce je:
     * jméno katedry;jméno předmětu;typ předmětu;semestr
     * např.:
     * KAE;CESA;Pr;LS
     *
     * @param csvRadek řádek rozvrhové akce - údaje jsou odděleny středníky
     * @return objekt aktuální RozvrhovaAkce
     */
    public Planeta vytvorPlanetu(String csvRadek){
        Planeta novaPlaneta = null;
        String[] part = csvRadek.split(",");
        try{
            novaPlaneta = new Planeta(
                    part[0],
                    part[1],
                    new Pozice(Double.parseDouble(part[2]), Double.parseDouble(part[3])),
                    new Rychlost(Double.parseDouble(part[4]), Double.parseDouble(part[5])),
                    Double.parseDouble(part[6])
            );
        } catch(Exception e){
            e.printStackTrace();
        }

        return novaPlaneta;
    }
}
