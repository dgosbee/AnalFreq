package analfreq.gui;

import analfreq.config.Config;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;

public class BubbleChartFactory {

    public static BubbleChart createBubbleChart() {
        final NumberAxis xAxis = new NumberAxis(Config.MIN_X_AXIS_LABEL,
                Config.MAX_X_AXIS_LABEL, Config.STEP_X_AXIS);
        final NumberAxis yAxis = new NumberAxis(Config.MIN_Y_AXIS_LABEL,
                Config.MAX_Y_AXIS_LABEL, Config.STEP_Y_AXIS);
        final BubbleChart<Number, Number> bubbleChart
                = new BubbleChart<>(xAxis, yAxis);
        xAxis.setLabel(Config.X_AXIS_LABEL);
        yAxis.setLabel(Config.Y_AXIS_LABEL);
        bubbleChart.setTitle(Config.BUBBLE_CHART_TITLE);
        return bubbleChart;
    }
}
