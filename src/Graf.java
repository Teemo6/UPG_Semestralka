import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

/**
 * Knihovní třída {@code Graf} umí vykreslit graf rychlosti planety
 * @author Štěpán Faragula 30-04-2022
 * @version 1.24
 */
public class Graf {

    // Množina všech právě zobrazených grafů planet
    // Umožňuje pozorování rychlosti více planet najednou
    private static final Set<Planeta> grafyPlanet = new HashSet<>();

    /**
     * Vytvoří graf rychlosti v novém okně
     * @param planeta zobrazovaná planeta
     */
    public static void vytvorOknoGrafu(Planeta planeta){
        if(grafyPlanet.contains(planeta)){
            return;
        }
        grafyPlanet.add(planeta);

        JFrame okno = new JFrame();
        okno.setTitle("Rychlost planety " + planeta.getName());
        okno.setSize(700, 500);

        ChartPanel chartPanel = createChart(planeta);
        chartPanel.setPreferredSize(new Dimension(700, 500));
        okno.add(chartPanel);
        okno.pack();

        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                grafyPlanet.remove(planeta);
            }
        };
        okno.addWindowListener(exitListener);

        okno.setLocationRelativeTo(Galaxy_SP2022.okno);
        okno.setVisible(true);
    }

    // Vytvoří panel grafu
    private static ChartPanel createChart(Planeta p){

        // Vytvoří graf nashromážděných dat
        XYSeries series = new XYSeries("Rychlost");
        TreeMap<Long, OldPlanetTraits> mapCopy = (TreeMap<Long, OldPlanetTraits>) p.getOldPlanetMap().clone();
        mapCopy.forEach((t, v) -> {if (mapCopy.lastKey() - t <= 30000) series.add(t / 1000.0, v.getOldVelocity());});
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Rychlost planety " + p.getName(),
                "t [s]",
                "v [km/h]",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        // Časovač, aktualizuje data
        Timer plotTimer = new Timer();
        plotTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                     series.add(p.getLastTime() / 1000.0, p.getLastVelocity());
                     if(series.getMaxX() - series.getMinX() > 30) {
                        series.remove(0);
                    }
                });
            }}, 0, 20);

        // Stylování grafu
        chart.setBackgroundPaint(Color.LIGHT_GRAY);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.WHITE);

        XYLineAndShapeRenderer renderer0 = new XYLineAndShapeRenderer();
        plot.setRenderer(0, renderer0);
        renderer0.setSeriesShapesVisible(0,  false);
        renderer0.setSeriesPaint(0, Color.MAGENTA);

        return new ChartPanel(chart);
    }
}
