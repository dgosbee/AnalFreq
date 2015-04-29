package analfreq;

import analfreq.config.Config;
import analfreq.freqevent.FreqEvent;
import analfreq.freqevent.MaskEvent;
import analfreq.gui.DragZoomBubbleChartFactory;
import analfreq.gui.DragZoomBubbleChart;
import analfreq.gui.UIControlFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private static final List<FreqEvent> allFreqEvents = new ArrayList<>();

    private static final DragZoomBubbleChart chart
            = DragZoomBubbleChartFactory.createBubbleChart();

    public static void plotObject(XYChart.Series series) {
        chart.getData().addAll(series);
    }

    public static void addFreqEvent(FreqEvent freqEvent) {
        allFreqEvents.add(freqEvent);
        findAllMaskEvents();
    }

    private static void findAllMaskEvents() {
        Config.debug("Finding all mask events...");
        allFreqEvents.forEach((outerFreqEvent) -> {
            allFreqEvents.forEach((innerFreqEvent) -> {
                if (!outerFreqEvent.getInstrument()
                        .equals(innerFreqEvent.getInstrument())) {
                    Rectangle outerBounds = new Rectangle(outerFreqEvent.getStartTime(), outerFreqEvent.getMinFreq(),
                            outerFreqEvent.getEndTime() - outerFreqEvent.getStartTime(),
                            outerFreqEvent.getMaxFreq() - outerFreqEvent.getMinFreq());
                    Rectangle innerBounds = new Rectangle(innerFreqEvent.getStartTime(), innerFreqEvent.getMinFreq(),
                            innerFreqEvent.getEndTime() - innerFreqEvent.getStartTime(),
                            innerFreqEvent.getMaxFreq() - innerFreqEvent.getMinFreq());
                    if (outerBounds.intersects(innerBounds.getX(),
                            innerBounds.getY(), innerBounds.getWidth(),
                            innerBounds.getHeight())) {
                        MaskEvent maskEvent = new MaskEvent(outerFreqEvent, innerFreqEvent);
                        Config.debug(maskEvent.toString());
                    }
                }
            });
        });
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(Config.STAGE_TITLE);
        BorderPane borderpane = new BorderPane();
        borderpane.setCenter(chart);
        borderpane.setRight(UIControlFactory.createUIControls());
        stage.setScene(new Scene(borderpane));
        stage.getIcons().add(new Image("icon/Asshole.jpg"));

        stage.getScene().setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.SHIFT)) {
                chart.setShiftPressed(true);
            }
        });

        stage.getScene().setOnKeyReleased((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.SHIFT)) {
                chart.setShiftPressed(false);
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
