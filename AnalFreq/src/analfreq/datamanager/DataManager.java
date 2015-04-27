package analfreq.datamanager;

import analfreq.Main;
import analfreq.config.Config;
import analfreq.freqevent.FreqEvent;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
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
     * @param minFreq the minimum frequency
     * @param maxFreq the maximum frequency
     * @param description a plain text description of the instrument
     */
    public static void processData(String name, String minFreq, 
            String maxFreq, String description) {
        plotInstrument(name, minFreq, maxFreq, description);
    }

    private static void plotInstrument(String name, String minFreq, String maxFreq, String description) {
        FreqEvent freqEvent = 
                new FreqEvent(name, Integer.parseInt(minFreq),Integer.parseInt(maxFreq));
        freqEvent.setDescription(description);
        Config.debug("Setting minimum frequency");
        freqEvent.setMinFreq(Integer.parseInt(minFreq)); // will be set by GUI, hardcoded for now
        freqEvent.setMaxFreq(Integer.parseInt(maxFreq)); // will be set by GUI. hardcoded for now
        XYChart.Series series = new XYChart.Series();
        series.setName(freqEvent.getInstrument());
        // params are: seconds, freq

        XYChart.Data data = new XYChart.Data(30, freqEvent.getCenterFreq());
        series.getData().add(data);
        Main.plotObject(series);
        Node node = data.getNode();
        node.setScaleX(10);
        double scaleY = freqEvent.getCenterFreq()-freqEvent.getMinFreq();
        node.setScaleY(scaleY);
     
        Tooltip.install(node, new Tooltip(freqEvent.toString()));
       
        Config.debug("FreqEvent Max: "+freqEvent.getMaxFreq());
        Config.debug("FreqEvent Min: "+freqEvent.getMinFreq());
        Config.debug("Calculated scaleY: "+scaleY);

        
    }
}
