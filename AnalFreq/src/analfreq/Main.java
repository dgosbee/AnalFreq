package analfreq;

import analfreq.gui.UIFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        UIFactory.initStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
