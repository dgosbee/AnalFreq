package analfreq.datamanager;

import analfreq.Main;
import analfreq.freqevent.FreqEvent;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

/**
 * This class forms the communication mechanism between the input GUI and the
 * underlying data that gets created. When a user enters data into the GUI
 * (property editor or new instrument creation wizard) and presses submit, the
 * collected data will be passed to this class for processing.
 */
public class DataManager {

    public static void processDataFromGUI(String name,
            String minFreq, String maxFreq,
            String startTime, String endTime,
            String description) {
        plotInstrument(name, minFreq, maxFreq, startTime, endTime, description);
    }

    private static void plotInstrument(String name,
            String minFreq, String maxFreq,
            String startTime, String endTime,
            String description) {
        FreqEvent freqEvent
                = new FreqEvent(name,
                        Integer.parseInt(minFreq), Integer.parseInt(maxFreq),
                        Integer.parseInt(startTime), Integer.parseInt(endTime));

        freqEvent.setDescription(description);
         Main.addFreqEvent(freqEvent);
        
        XYChart.Series series = new XYChart.Series();
        series.setName(freqEvent.getInstrument());
        XYChart.Data data = new XYChart.Data(freqEvent.getMidTime(), freqEvent.getCenterFreq());
        series.getData().add(data);
        Main.plotObject(series);
        Node node = data.getNode();
        double scaleX = freqEvent.getMidTime() - freqEvent.getStartTime();
        double scaleY = freqEvent.getCenterFreq() - freqEvent.getMinFreq();
        node.setScaleX(scaleX);
        node.setScaleY(scaleY);
        Tooltip.install(node, new Tooltip(freqEvent.toString())); 
    }
}
