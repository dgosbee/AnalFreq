package analfreq.datamanager;

import analfreq.debug.Debug;
import analfreq.freqevent.FreqEvent;
import analfreq.gui.DragZoomBubbleChart;
import analfreq.gui.UIManager;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javafx.collections.ObservableList;
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

    private static DragZoomBubbleChart chart;
    private static final List<FreqEvent> freqEvents = new ArrayList<>();
    private static FreqEvent freqEvent;
    private static StringTokenizer tokenizer;
    private static String freqEventToken;
    private static XYChart.Data data;
    private static double scaleX;
    private static double scaleY;
    private static Node node;

    public static void plotFreqEvent(String name, String minFreq, String maxFreq,
            String startTime, String endTime, String description) {
        freqEvent = new FreqEvent(name, Integer.parseInt(minFreq), Integer.parseInt(maxFreq),
                Integer.parseInt(startTime), Integer.parseInt(endTime));
        freqEvent.setDescription(description);
        freqEvents.add(freqEvent);
        tokenizer = new StringTokenizer(freqEvent.getName());
        freqEventToken = tokenizer.nextToken();
        data = new XYChart.Data(freqEvent.getMidTime(), freqEvent.getCenterFreq());
        scaleX = freqEvent.getMidTime() - freqEvent.getStartTime();
        scaleY = freqEvent.getCenterFreq() - freqEvent.getMinFreq();
        chart = UIManager.getChart();
        ObservableList<XYChart.Series> seriesList = chart.getData();
        if (seriesList.isEmpty()) {
            createNewSeries();
        } else {
            doIfSeriesExists();
        }
    }
    
    private static void doIfSeriesExists() {
        boolean matchesExistingSeries = false;
        ObservableList<XYChart.Series> seriesList = chart.getData();
        for (XYChart.Series series : seriesList) {
            if (freqEventToken.equals(series.getName())) {
                matchesExistingSeries = true;
                series.getData().add(data);
                scaleNode();
                installTooltip();
            }
        }
        if (!matchesExistingSeries) {
            createNewSeries();
        }
    }

    private static void createNewSeries() {
        XYChart.Series series = new XYChart.Series();
        series.setName(freqEventToken);
        series.getData().add(data);
        UIManager.plotObject(series); // node is null until this call!
        scaleNode();
        installTooltip();
    }

    private static void installTooltip(){
         Tooltip.install(node, new Tooltip(freqEvent.toString()));
        node.setOnMouseClicked((MouseEvent) -> {
            Debug.debug(Debug.getCurrentMethodName()+": CLICKED ON: " + freqEvent.toString());
        });
    }
    
    private static void scaleNode() {
        node = data.getNode();
        node.setScaleX(scaleX);
        node.setScaleY(scaleY);
    }
    
    public static List<FreqEvent> getFreqEvents() {
        return freqEvents;
    }
}