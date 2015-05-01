package analfreq.datamanager;

import analfreq.config.Config;
import analfreq.freqevent.FreqEvent;
import analfreq.freqevent.MaskEvent;
import analfreq.gui.UIFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;

/**
 * This class forms the communication mechanism between the input GUI and the
 * underlying data that gets created. When a user enters data into the GUI
 * (property editor or new instrument creation wizard) and presses submit, the
 * collected data will be passed to this class for processing.
 */
public class DataManager {

    private static final List<FreqEvent> allFreqEvents = new ArrayList<>();

    public static void addFreqEvent(FreqEvent freqEvent) {
        allFreqEvents.add(freqEvent);
        findAllMaskEvents();
    }

    private static void findAllMaskEvents() {
        Config.debug("Finding all mask events...");
        allFreqEvents.forEach((outerFreqEvent) -> {
            allFreqEvents.forEach((innerFreqEvent) -> {
                if (!outerFreqEvent.getInstrument()
                        .equals(innerFreqEvent.getInstrument())) {
                    Rectangle outerBounds = new Rectangle(outerFreqEvent.getStartTime(), outerFreqEvent.getMinFreq(),
                            outerFreqEvent.getEndTime() - outerFreqEvent.getStartTime(),
                            outerFreqEvent.getMaxFreq() - outerFreqEvent.getMinFreq());
                    Rectangle innerBounds = new Rectangle(innerFreqEvent.getStartTime(), innerFreqEvent.getMinFreq(),
                            innerFreqEvent.getEndTime() - innerFreqEvent.getStartTime(),
                            innerFreqEvent.getMaxFreq() - innerFreqEvent.getMinFreq());
                    if (outerBounds.intersects(innerBounds.getX(),
                            innerBounds.getY(), innerBounds.getWidth(),
                            innerBounds.getHeight())) {
                        MaskEvent maskEvent = new MaskEvent(outerFreqEvent, innerFreqEvent);
                        Config.debug(maskEvent.toString());
                    }
                }
            });
        });
    }

    public static void processDataFromGUI(String name,
            String minFreq, String maxFreq,
            String startTime, String endTime,
            String description) {
        plotFreqEvent(name, minFreq, maxFreq, startTime, endTime, description);
    }

    private static void plotFreqEvent(String name,
            String minFreq, String maxFreq,
            String startTime, String endTime,
            String description) {
        FreqEvent freqEvent
                = new FreqEvent(name,
                        Integer.parseInt(minFreq), Integer.parseInt(maxFreq),
                        Integer.parseInt(startTime), Integer.parseInt(endTime));

        freqEvent.setDescription(description);
        addFreqEvent(freqEvent);
        XYChart.Series series = new XYChart.Series();
        series.setName(freqEvent.getInstrument());
        XYChart.Data data = new XYChart.Data(freqEvent.getMidTime(), freqEvent.getCenterFreq());
        series.getData().add(data);
        UIFactory.plotObject(series);
        Node node = data.getNode();
        double scaleX = freqEvent.getMidTime() - freqEvent.getStartTime();
        double scaleY = freqEvent.getCenterFreq() - freqEvent.getMinFreq();
        node.setScaleX(scaleX);
        node.setScaleY(scaleY);
        Tooltip.install(node, new Tooltip(freqEvent.toString()));

        node.setOnMouseClicked((MouseEvent) -> {

            Config.debug("CLICKED ON: "+freqEvent.toString());
            
        });

    }
}
