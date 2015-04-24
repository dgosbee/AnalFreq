package analfreq.gui;

import analfreq.config.Config;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;

public class DisplayChart {

    public static BubbleChart createDisplayChart() {

        // Chart
        final NumberAxis xAxis = new NumberAxis(Config.minXAxisLabel,
                Config.maxXAxisLabel, Config.stepXAxis);
        final NumberAxis yAxis = new NumberAxis(Config.minYAxisLabel,
                Config.maxYAxisLabel, Config.stepYAxis);
        final BubbleChart<Number, Number> bubbleChart
                = new BubbleChart<>(xAxis, yAxis);
        xAxis.setLabel(Config.xAxisLabel);
        yAxis.setLabel(Config.yAxisLabel);
        bubbleChart.setTitle(Config.blcTitle);
        // End chart

        return bubbleChart;
    }

}
