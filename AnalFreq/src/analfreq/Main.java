package analfreq;

import analfreq.config.Config;
import analfreq.debug.Debug;
import analfreq.gui.UIFactory;
import analfreq.xml.XMLReader;
import analfreq.xml.XMLWriter;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.xml.stream.XMLStreamException;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        UIFactory.initStage(stage);
        try {
            XMLReader.readXML();
        } catch (FileNotFoundException ex) {
            Debug.debug("WARNING: No XML data found at: "+Config.XML_PATH+ " (try saving your project next time!)");
        } catch (XMLStreamException ex) {
            
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
