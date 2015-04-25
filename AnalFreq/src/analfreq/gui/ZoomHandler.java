package analfreq.gui;

import analfreq.config.Config;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.ScrollEvent;

/**
 * Provides zoom capability for the Frequency(Hz) axis of the BubbleChart.
 * Use the mouse scroll wheel to zoom in/out.
 */
public class ZoomHandler {

    private static BubbleChart bubbleChart;
    private static int scrollPos = 0;
    private static int currMaxY = Config.MAX_FREQ;

    // first step in zoom.. not finished yet
    public static void handleZoom(BubbleChart bc) {
        bubbleChart = bc;
        bubbleChart.setOnScroll((ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {
                zoomIn();
            } else {
                zoomOut();
            }
        });
    }

    private static void zoomIn() {
        int newMaxY = (int) (currMaxY * .5);
        if (newMaxY <= 20) {
            newMaxY = 20;
        }
        currMaxY = newMaxY;
        NumberAxis yAxis = (NumberAxis) bubbleChart.getYAxis();
        yAxis.setAnimated(true);
        yAxis.setUpperBound(newMaxY);
    }

    private static void zoomOut() {
        int newMaxY = (int) (currMaxY * 1.5);
        if (newMaxY >= 20000) {
            newMaxY = 20000;
        }
        currMaxY = newMaxY;
        NumberAxis yAxis = (NumberAxis) bubbleChart.getYAxis();
        yAxis.setAnimated(true);
        yAxis.setUpperBound(newMaxY);
    }
}
