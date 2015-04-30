package analfreq;

import analfreq.config.Config;
import analfreq.freqevent.FreqEvent;
import analfreq.freqevent.MaskEvent;
import analfreq.gui.UIFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private static final List<FreqEvent> allFreqEvents = new ArrayList<>();

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
      UIFactory.initStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
