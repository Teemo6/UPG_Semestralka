import java.util.*;
import java.io.*;

/**
 * Instance třídy {@code VstupDat} představuje přepravku, která přečte vstupní soubor a uloží si k sobě potřebná data
 * @author Štěpán Faragula 28-03-2022
 */
public class VstupDat{
    /** Řetězec s cestou k souboru ke čtení */
    private String cestaSouboru;

    /** Načtené hodnoty */
    private double konstantaG;
    private double casovySkok;
    private List<Planeta> seznamPlanet = new ArrayList<>();

    /**
     * Konstruktor třídy {@code VstupDat()}
     * rovnou přečte a uloží data ze souboru
     * @param cestaSouboru cesta k souboru na čtení
     */
    public VstupDat(String cestaSouboru){
        this.cestaSouboru = cestaSouboru;
        nactiData();
    }

    /**
     * Vrátí konstantu G
     * @return konstanta G
     */
    public double getKonstantaG() {
        return konstantaG;
    }

    /**
     * vrátí časový skok
     * @return časový skok
     */
    public double getCasovySkok() {
        return casovySkok;
    }

    /**
     * Vrátí seznam planet
     * @return seznam planet
     */
    public List<Planeta> getSeznamPlanet() {
        return seznamPlanet;
    }

    /**
     * Načte konstanty a seznem planet ze souboru
     * využívá služeb metod {@code nactiKonstanty()} a {@code vytvorPlanetu()}
     */
    private void nactiData() {
        String read;

        try {
            FileReader fr = new FileReader(cestaSouboru);
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
     * Z příslušného řetězce přiřadí konstantám hodnoty
     *
     * @param csvRadek řádek s konstantama, čísla oddělena čárkou
     */
    private void nactiKonstanty(String csvRadek){
        String[] part = csvRadek.split(",");
        konstantaG = Double.parseDouble(part[0]);
        casovySkok = Double.parseDouble(part[1]);
    }

    /**
     * Z příslušného řetězce vyrobí objekt {@code Planeta}
     *
     * @param csvRadek řádek planety, údaje odděleny čárkou
     * @return objekt {@code Planeta}
     */
    private Planeta vytvorPlanetu(String csvRadek){
        String[] part = csvRadek.split(",");
        return new Planeta(
                part[0],
                part[1],
                Double.parseDouble(part[2]),
                Double.parseDouble(part[3]),
                Double.parseDouble(part[4]),
                Double.parseDouble(part[5]),
                Double.parseDouble(part[6])
            );
    }
}