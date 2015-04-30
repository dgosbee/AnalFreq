package analfreq;

import analfreq.config.Config;
import analfreq.freqevent.FreqEvent;
import analfreq.freqevent.MaskEvent;
import analfreq.gui.DragZoomBubbleChartFactory;
import analfreq.gui.DragZoomBubbleChart;
import analfreq.gui.UIControlFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private BorderPane rootNode;
    private MenuBar menuBar;

    private static final List<FreqEvent> allFreqEvents = new ArrayList<>();

    private static final DragZoomBubbleChart chart
            = DragZoomBubbleChartFactory.createBubbleChart();

    /**
     * This functionality probably belongs in a different class.
     */
    public static void plotObject(XYChart.Series series) {
        chart.getData().addAll(series);
    }

    /**
     * This functionality probably belongs in a different class.
     */
    public static void addFreqEvent(FreqEvent freqEvent) {
        allFreqEvents.add(freqEvent);
        findAllMaskEvents();
    }

    /**
     * This functionality probably belongs in a different class.
     */
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
        createRootNode();
        initStage(stage, new Scene(rootNode));
    }

    private void createRootNode() {
        rootNode = new BorderPane();
        rootNode.getStylesheets().add("css/Skin.css");
        rootNode.setCenter(chart);
        rootNode.setRight(UIControlFactory.createUIControls());
        initMenuBar();
        rootNode.setTop(menuBar);
    }

    
    private void initMenuBar(){
        menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem save = new MenuItem("Save");
        menuFile.getItems().add(save);
        menuBar.getMenus().addAll(menuFile);
    }
    
    
    private void initStage(Stage stage, Scene scene) {
        stage.setTitle(Config.STAGE_TITLE);
        stage.setScene(scene);
        stage.getIcons().add(new Image("icon/icon.png"));
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
