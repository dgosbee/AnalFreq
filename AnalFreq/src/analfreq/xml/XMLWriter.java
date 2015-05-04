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


package analfreq.xml;

import analfreq.config.Config;
import analfreq.freqevent.FreqEvent;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter {

    public static void writeXML(List<FreqEvent> freqEvents) {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document doc = docBuilder.newDocument();

        // Create root XML element
        Element rootElement = doc.createElement("ProjectData");
        doc.appendChild(rootElement);

        // Convert FreqEvent data to XML elements
        freqEvents.forEach((e) -> {
            
            Element freqEvent = doc.createElement("FreqEvent");  
            rootElement.appendChild(freqEvent);
            
            Element name = doc.createElement("EventName");
            name.appendChild(doc.createTextNode(e.getName()));
            freqEvent.appendChild(name);
            
            Element type = doc.createElement("EventType");
            type.appendChild(doc.createTextNode(e.getType().toString()));
            freqEvent.appendChild(type);
            
            Element maxFreq = doc.createElement("MaxFreq");
            maxFreq.appendChild(doc.createTextNode(Integer.toString(e.getMaxFreq())));
            freqEvent.appendChild(maxFreq);
            
            Element centerFreq = doc.createElement("CenterFreq");
            centerFreq.appendChild(doc.createTextNode(Integer.toString(e.getCenterFreq())));
            freqEvent.appendChild(centerFreq);
            
            Element minFreq = doc.createElement("MinFreq");
            minFreq.appendChild(doc.createTextNode(Integer.toString(e.getMinFreq())));
            freqEvent.appendChild(minFreq);
            
            Element endTime = doc.createElement("EndTime");
            endTime.appendChild(doc.createTextNode(Integer.toString(e.getEndTime())));
            freqEvent.appendChild(endTime);
            
            Element centerTime = doc.createElement("MidTime");
            centerTime.appendChild(doc.createTextNode(Integer.toString(e.getMidTime())));
            freqEvent.appendChild(centerTime);
            
            Element startTime = doc.createElement("StartTime");
            startTime.appendChild(doc.createTextNode(Integer.toString(e.getStartTime())));
            freqEvent.appendChild(startTime);
            
            Element description = doc.createElement("Description");
            description.appendChild(doc.createTextNode(e.getDescription()));
            freqEvent.appendChild(description);
        });

        // Write results to file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(Config.XML_PATH));
        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Project data saved! " + Config.XML_PATH);
    }
}
