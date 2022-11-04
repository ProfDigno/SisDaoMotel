/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Grafico;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Insets;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * A simple demonstration application showing how to create a thermometer.
 *
 */
public class ThermometerDemo2 extends ApplicationFrame {

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public ThermometerDemo2(final String title) {

        super(title);

        // create a dataset...
        final DefaultValueDataset dataset = new DefaultValueDataset(new Double(43.0));

        // create the chart...
        final ThermometerPlot plot = new ThermometerPlot(dataset);
        final JFreeChart chart = new JFreeChart("Thermometer Demo 2",  // chart title
                                          JFreeChart.DEFAULT_TITLE_FONT,
                                          plot,                 // plot
                                          false);               // include legend


        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
    //    plot.setInsets(new Insets(5, 5, 5, 5));
        //plot.setRangeInfo(ThermometerPlot.NORMAL, 0.0, 55.0, 0.0, 100.0);
        //plot.setRangeInfo(ThermometerPlot.WARNING, 55.0, 75.0, 0.0, 100.0);
        //plot.setRangeInfo(ThermometerPlot.CRITICAL, 75.0, 100.0, 0.0, 100.0);

        plot.setThermometerStroke(new BasicStroke(2.0f));
        plot.setThermometerPaint(Color.lightGray);
        // OPTIONAL CUSTOMISATION COMPLETED.

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);

    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final ThermometerDemo2 demo = new ThermometerDemo2("Thermometer Demo 2");
        demo.pack();
        demo.setVisible(true);

    }

}