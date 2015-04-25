package analfreq.datamanager;

import analfreq.Main;
import analfreq.config.Config;
import analfreq.freqevent.FreqEvent;
import javafx.scene.chart.XYChart;

/**
 * This class forms the communication mechanism between the input GUI and the
 * underlying data that gets created. When a user enters data into the GUI
 * (property editor or new instrument creation wizard) and presses submit, the
 * collected data will be passed to this class for processing.
 */
public class DataManager {

    /**
     * This method is called by the GUI whenever the user enters new data.
     *
     * @param name the instrument name
     * @param centerFreq the center frequency
     * @param description a plain text description of the instrument
     */
    public static void processData(String name,
            String centerFreq, String description) {
        plotInstrument(name, centerFreq, description);
    }
    
    private static void plotInstrument(String name, String centerFreq, String description) {
        FreqEvent freqEvent = new FreqEvent(name,Integer.parseInt(centerFreq));
        freqEvent.setDescription(description);
        XYChart.Series series = new XYChart.Series();
        series.setName(freqEvent.getInstrument());
        series.getData().add(new XYChart.Data(7, freqEvent.getCenterFreq()));
        Main.plotObject(series);
        Config.debug(freqEvent.toString());
    }
}