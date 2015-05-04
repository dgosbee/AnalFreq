/*
 * Copyright (C) 2015 Black River Audio Works (www.blackriveraudioworks.com)
 *
 * This file is part of AnalFreq.
 *
 * Main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AnalFreq.  If not, see <http://www.gnu.org/licenses/>.
 */

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
        Debug.debug(Debug.getCurrentMethodName()+": Starting Program...");
        UIManager.initStage(stage);
        try {
            XMLReader.readXML();
        } catch (FileNotFoundException ex) {
            Debug.debug("WARNING: No XML data found at: "+Config.XML_PATH+ " (try saving your project next time!)");
        } catch (XMLStreamException ex) {}  
    }

    public static void main(String[] args) {
         Debug.debug(Debug.getCurrentMethodName());
        launch(args);
    }
}
