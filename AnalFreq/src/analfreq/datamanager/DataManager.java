package analfreq.datamanager;

import analfreq.config.Config;
import analfreq.debug.Debug;
import analfreq.freqevent.FreqEvent;
import analfreq.freqevent.MaskEvent;
import analfreq.gui.DragZoomBubbleChart;
import analfreq.gui.UIManager;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javafx.collections.ObservableList;
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

    private static List<FreqEvent> freqEvents = new ArrayList<FreqEvent>();

    public static List<FreqEvent> getFreqEvents() {
        return freqEvents;
    }


    private static void doIfSeriesExists(DragZoomBubbleChart chart,FreqEvent freqEvent) {
        boolean matchesExistingSeries = false;
        StringTokenizer st = new StringTokenizer(freqEvent.getName());
        String freqEventToken = st.nextToken();
        ObservableList<XYChart.Series> seriesList = chart.getData();
        for (XYChart.Series series : seriesList) {
            if (freqEventToken.equals(series.getName())) {
                matchesExistingSeries = true;
                XYChart.Data data = new XYChart.Data(freqEvent.getMidTime(),
                        freqEvent.getCenterFreq());
                series.getData().add(data);
                Node node = data.getNode();
                double scaleX = freqEvent.getMidTime() - freqEvent.getStartTime();
                double scaleY = freqEvent.getCenterFreq() - freqEvent.getMinFreq();
                node.setScaleX(scaleX);
                node.setScaleY(scaleY);
                Tooltip.install(node, new Tooltip(freqEvent.toString()));
                node.setOnMouseClicked((MouseEvent) -> {
                    Debug.debug("CLICKED ON: " + freqEvent.toString());
                });
            }
        }
        if (!matchesExistingSeries) {
            createNewSeries(chart,freqEvent);
        }

    }

    private static void createNewSeries(DragZoomBubbleChart chart, FreqEvent freqEvent) {
        XYChart.Series series = new XYChart.Series();
        StringTokenizer st1 = new StringTokenizer(freqEvent.getName());
        series.setName(st1.nextToken());
        XYChart.Data data = new XYChart.Data(freqEvent.getMidTime(), freqEvent.getCenterFreq());
        series.getData().add(data);
        UIManager.plotObject(series);
        Node node = data.getNode();
        double scaleX = freqEvent.getMidTime() - freqEvent.getStartTime();
        double scaleY = freqEvent.getCenterFreq() - freqEvent.getMinFreq();
        node.setScaleX(scaleX);
        node.setScaleY(scaleY);
        Tooltip.install(node, new Tooltip(freqEvent.toString()));
        node.setOnMouseClicked((MouseEvent) -> {
            Debug.debug("CLICKED ON: " + freqEvent.toString());
        });
    }

    public static void plotFreqEvent(String name,
            String minFreq, String maxFreq,
            String startTime, String endTime,
            String description) {

        FreqEvent freqEvent
                = new FreqEvent(name,
                        Integer.parseInt(minFreq), Integer.parseInt(maxFreq),
                        Integer.parseInt(startTime), Integer.parseInt(endTime));
        freqEvent.setDescription(description);
        freqEvents.add(freqEvent);

        DragZoomBubbleChart chart = UIManager.getChart();
        ObservableList<XYChart.Series> seriesList = chart.getData();
        if (seriesList.isEmpty()) {
            createNewSeries(chart, freqEvent);
        } else {
            doIfSeriesExists(chart, freqEvent);
        }

        /*
         // Do we have any series data already in the chart?
         DragZoomBubbleChart chart = UIManager.getChart();
         ObservableList<XYChart.Series> seriesList = chart.getData();

         seriesList.forEach((s) -> {
         StringTokenizer st1 = new StringTokenizer(s.getName());
         StringTokenizer st2 = new StringTokenizer(freqEvent.getName());
         String seriesNamePrefix = st1.nextToken(); // first token "KICK" etc.
         String freqEventNamePrefix = st2.nextToken();
         // Should the new FreqEvent be part of an existing series?
         if (freqEventNamePrefix.equals(seriesNamePrefix)) {
         XYChart.Data data = new XYChart.Data(freqEvent.getMidTime(),
         freqEvent.getCenterFreq());
         s.getData().add(data);
         Node node = data.getNode();
         double scaleX = freqEvent.getMidTime() - freqEvent.getStartTime();
         double scaleY = freqEvent.getCenterFreq() - freqEvent.getMinFreq();
         node.setScaleX(scaleX);
         node.setScaleY(scaleY);
         Tooltip.install(node, new Tooltip(freqEvent.toString()));
         node.setOnMouseClicked((MouseEvent) -> {
         Debug.debug("CLICKED ON: " + freqEvent.toString());
         });
         } else {
         Debug.debug("=========>");
         // There was at least one series already in the chart, but 
         // now we are adding a new series

         XYChart.Series series = new XYChart.Series();
         series.setName(freqEventNamePrefix);
         XYChart.Data data = new XYChart.Data(freqEvent.getMidTime(), freqEvent.getCenterFreq());
         series.getData().add(data);
         UIManager.plotObject(series);
         Node node = data.getNode();
         double scaleX = freqEvent.getMidTime() - freqEvent.getStartTime();
         double scaleY = freqEvent.getCenterFreq() - freqEvent.getMinFreq();
         node.setScaleX(scaleX);
         node.setScaleY(scaleY);
         Tooltip.install(node, new Tooltip(freqEvent.toString()));
         node.setOnMouseClicked((MouseEvent) -> {
         Debug.debug("CLICKED ON: " + freqEvent.toString());
         });

         }
         });

         // No series exist in the chart whatsoever. So add the first one.
         if (seriesList.size() == 0) {
         XYChart.Series series = new XYChart.Series();
         StringTokenizer st1 = new StringTokenizer(freqEvent.getName());
         series.setName(st1.nextToken());
         XYChart.Data data = new XYChart.Data(freqEvent.getMidTime(), freqEvent.getCenterFreq());
         series.getData().add(data);
         UIManager.plotObject(series);
         Node node = data.getNode();
         double scaleX = freqEvent.getMidTime() - freqEvent.getStartTime();
         double scaleY = freqEvent.getCenterFreq() - freqEvent.getMinFreq();
         node.setScaleX(scaleX);
         node.setScaleY(scaleY);
         Tooltip.install(node, new Tooltip(freqEvent.toString()));
         node.setOnMouseClicked((MouseEvent) -> {
         Debug.debug("CLICKED ON: " + freqEvent.toString());
         });
         }

         */
    }
}
