/**
 * This class is the communication mechanism between FreqEvent and the GUI
 * display of that information. It sits between the property editor, i.e. the
 * right hand side of the flowpane
 */
package analfreq.datamanager;

import analfreq.Main;
import analfreq.freqevent.FreqEvent;
import javafx.scene.chart.XYChart;

public class DataManager {

    /**
     * This method is going to be called by the property editor when user
     * clicked the submit button
     */
    public static void processData(String instrumentName,
            String centerFrequency, String instrumentDescription) {

        createPlottedInstrument(instrumentName, centerFrequency, 
                instrumentDescription);

    }

    private static void debug(String s) {
        System.out.println(s);

    }

    private static void createPlottedInstrument(String instrumentName,
            String centerFrequency, String instrumentDescription) {

        FreqEvent plottedInstrument
                = new FreqEvent(Integer.parseInt(centerFrequency),
                        instrumentName);
        plottedInstrument.setDescription(instrumentDescription);
        XYChart.Series series = new XYChart.Series();
        series.setName(plottedInstrument.getInstrument());
        series.getData().add(new XYChart.Data(7, plottedInstrument.getCenterFreq()));
        Main.plotObject(series);
        debug(plottedInstrument.toString());
    }

}
