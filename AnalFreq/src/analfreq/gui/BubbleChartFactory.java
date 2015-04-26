package analfreq.gui;

import analfreq.config.Config;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;

public class BubbleChartFactory {

    public static BubbleChart createBubbleChart() {
        final NumberAxis xAxis = new NumberAxis(Config.MIN_SEC,
                Config.MAX_SEC, Config.STEP_SEC);
        final NumberAxis yAxis = new NumberAxis(Config.MIN_FREQ,Config.MAX_FREQ, Config.STEP_FREQ);
        final BubbleChart<Number, Number> bubbleChart
                = new BubbleChart<>(xAxis, yAxis);
        xAxis.setLabel(Config.X_AXIS_LABEL);
        yAxis.setLabel(Config.Y_AXIS_LABEL);
        bubbleChart.setTitle(Config.BUBBLE_CHART_TITLE);
        bubbleChart.setAnimated(false);
        return bubbleChart;
    }
}
