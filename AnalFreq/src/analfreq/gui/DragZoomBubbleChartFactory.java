package analfreq.gui;

import analfreq.config.Config;
import javafx.scene.chart.NumberAxis;

public class DragZoomBubbleChartFactory {

    public static DragZoomBubbleChart createBubbleChart() {
        final NumberAxis xAxis = new NumberAxis(Config.MIN_SEC, Config.END_TIME, Config.STEP);
        final NumberAxis yAxis = new NumberAxis(Config.MIN_FREQ,Config.MAX_FREQ, Config.STEP_FREQ);
        xAxis.setLabel(Config.X_AXIS_LABEL);
        yAxis.setLabel(Config.Y_AXIS_LABEL);
        final DragZoomBubbleChart dzBubbleChart = new DragZoomBubbleChart(xAxis, yAxis);
        dzBubbleChart.setTitle(Config.BUBBLE_CHART_TITLE);
        dzBubbleChart.setAnimated(false);
        return dzBubbleChart;
    }
}
