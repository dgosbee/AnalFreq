package analfreq;

import analfreq.config.Config;
import analfreq.gui.DragZoomBubbleChartFactory;
import analfreq.gui.DragZoomBubbleChart;
import analfreq.gui.UIControlFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static final DragZoomBubbleChart chart
            = DragZoomBubbleChartFactory.createBubbleChart();

    /**
     * Plots a new object onto the bubble chart. This method is called after the
     * user clicks the submit button when adding a new instrument.
     *
     * @param series
     */
    public static void plotObject(XYChart.Series series) {
        // Add data to the chart
        chart.getData().addAll(series);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(Config.STAGE_TITLE);
        HBox root = new HBox();
        root.getChildren().addAll(chart, UIControlFactory.createUIControls());
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("icon/Asshole.jpg"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
