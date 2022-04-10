import java.util.*;
import java.io.*;

/**
 * Instance třídy {@code VstupDat} představuje jedináčka, který umí přečíst vstupní soubor a vybrat potřebná data
 * @author Štěpán Faragula 28-03-2022
 */
public class VstupDat{
    /** Instance jedináčka */
    private static final VstupDat INSTANCE = new VstupDat();

    /** Načtené hodnoty */
    private double konstantaG;
    private double casovySkok;
    private List<Planeta> seznamPlanet;

    /**
     * @return instance jedináčka
     */
    public static VstupDat getInstance(){
        return INSTANCE;
    }

    /**
     * @return konstanta G
     */
    public double getKonstantaG() {
        return konstantaG;
    }

    /**
     * @return časový skok
     */
    public double getCasovySkok() {
        return casovySkok;
    }

    /**
     * @return seznam planet
     */
    public List<Planeta> getSeznamPlanet() {
        return seznamPlanet;
    }

    /**
     * Načte konstanty a vytvoří planety ze souboru
     * využívá služeb metod {@code nactiKonstanty()} a {@code vytvorPlanetu()}
     *
     * @param vstupniSoubor soubor k načtení
     */
    public void nactiData(String vstupniSoubor) {
        seznamPlanet = new ArrayList<>();
        String read;

        try {
            FileReader fr = new FileReader(vstupniSoubor);
            BufferedReader br = new BufferedReader(fr);

            nactiKonstanty(br.readLine());

            while ((read = br.readLine()) != null) {
                seznamPlanet.add(vytvorPlanetu(read));
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Z příslušného řetězce si uloží hodnoy kosntant
     * @param csvRadek {@code String} s konstantama, dvě čísla oddělena čárkou
     */
    public void nactiKonstanty(String csvRadek){
        String[] part = csvRadek.split(",");
        konstantaG = Double.parseDouble(part[0]);
        casovySkok = Double.parseDouble(part[1]);
    }

    /**
     * Z příslušného řetězce vytvoří instanci {@code Planeta}
     * @param csvRadek {@code String} s planetou, údaje odděleny čárkou
     * @return isntance {@code Planeta}
     */
    public Planeta vytvorPlanetu(String csvRadek){
        String[] part = csvRadek.split(",");
        return new Planeta(
                part[0],
                part[1],
                new DoubleVector2D(Double.parseDouble(part[2]), Double.parseDouble(part[3])),
                new DoubleVector2D(Double.parseDouble(part[4]), Double.parseDouble(part[5])),
                Double.parseDouble(part[6])
            );
    }
}