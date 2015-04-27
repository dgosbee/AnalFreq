package analfreq.datamanager;

import analfreq.Main;
import analfreq.config.Config;
import analfreq.freqevent.FreqEvent;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Circle;

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
        FreqEvent freqEvent = new FreqEvent(name, Integer.parseInt(centerFreq));
        freqEvent.setDescription(description);
        freqEvent.setStartFreq(0); // will be set by GUI, hardcoded for now
        freqEvent.setEndFreq(100); // will be set by GUI. hardcoded for now
        XYChart.Series series = new XYChart.Series();
        series.setName(freqEvent.getInstrument());
        // params are: seconds, freq

        XYChart.Data data = new XYChart.Data(10, freqEvent.getCenterFreq());

        series.getData().add(data);
        Main.plotObject(series);
        Node node = data.getNode();
        node.setScaleX(10);
        node.setScaleY((freqEvent.getStartFreq()+freqEvent.getEndFreq())/2);
       
       

        Config.debug(freqEvent.toString());
    }
}
