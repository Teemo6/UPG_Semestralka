import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Graf {
    public static void vytvorOknoGrafu(Planeta planeta) {
        JFrame okno = new JFrame();
        okno.setTitle("Rychlost planety " + planeta.getName());
        okno.setSize(800, 600);

        JFreeChart chart = ChartFactory.createLineChart(
                "Rychlost planety " + planeta.getName(),
                "t [s]",
                "v [km/h]",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 367));
        okno.add(chartPanel);
        okno.pack();

        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setLocationRelativeTo(null);
        okno.setVisible(true);
    }

    private static DefaultCategoryDataset createDataset(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        dataset.addValue( 15 , "schools" , "1970" );
        dataset.addValue( 30 , "schools" , "1980" );
        dataset.addValue( 60 , "schools" ,  "1990" );
        dataset.addValue( 120 , "schools" , "2000" );
        dataset.addValue( 240 , "schools" , "2010" );
        dataset.addValue( 300 , "schools" , "2014" );
        return dataset;
    }
}
