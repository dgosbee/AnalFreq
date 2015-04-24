package analfreq;

import analfreq.config.Config;
import analfreq.freqevent.FreqEvent;
import analfreq.gui.DisplayChart;
import analfreq.gui.UIControls;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static final BubbleChart bubbleChart = DisplayChart.createDisplayChart();

    public static void plotObject(XYChart.Series series) {
        bubbleChart.getData().addAll(series);
    }

    @Override
    public void start(Stage stage) {
        FlowPane uiControls = UIControls.createUIControls();
        stage.setTitle(Config.stageTitle);
        HBox root = new HBox();
        root.getChildren().addAll(bubbleChart, uiControls);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("icon/Asshole.jpg"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
