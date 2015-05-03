package analfreq;

import analfreq.config.Config;
import analfreq.debug.Debug;
import analfreq.gui.UIManager;
import analfreq.xml.XMLReader;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.xml.stream.XMLStreamException;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        UIManager.initStage(stage);
        
        
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
