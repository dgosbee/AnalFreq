package analfreq.gui;

import analfreq.config.Config;
import javafx.scene.chart.BubbleChart;
import javafx.scene.input.ScrollEvent;

public class ZoomHandler {

    private static int scrollPos = 0;

    // first step in zoom.. not finished yet
    public static void handleZoom(BubbleChart bubbleChart) {
        bubbleChart.setOnScroll((ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {
                Config.debug("ZoomHandler: "+Integer.toString(++scrollPos));
            } else {
                Config.debug("ZoomHandler: "+Integer.toString(--scrollPos));
            }
        });
    }
}
