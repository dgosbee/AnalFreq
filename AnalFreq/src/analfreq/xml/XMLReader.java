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
import analfreq.datamanager.DataManager;
import analfreq.debug.Debug;
import analfreq.freqevent.FreqEventType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLReader {

    private static XMLInputFactory inputFactory;
    private static InputStream in;
    private static XMLEventReader eventReader;
    private static XMLEvent event;
    private static EndElement endElement;

    // Current data from XML file
    private static String eventName, description, maxFreq, centerFreq, minFreq, endTime, midTime, startTime;
    private static FreqEventType freqEventType;

    public static void readXML() throws FileNotFoundException, XMLStreamException {
        Debug.debug(Debug.getCurrentMethodName());
        inputFactory = XMLInputFactory.newInstance();
        in = new FileInputStream(Config.XML_PATH);
        eventReader = inputFactory.createXMLEventReader(in);
        while (eventReader.hasNext()) {
            event = eventReader.nextEvent();
            processElements();     
        } 
    }

    private static void processElements() throws XMLStreamException {
        Debug.debug(Debug.getCurrentMethodName());

        if(event.isEndElement()){
           EndElement endElement = event.asEndElement();
            String endElementTag = endElement.getName().getLocalPart();
            if(endElementTag.equalsIgnoreCase("FreqEvent")){
                DataManager.plotFreqEvent(eventName, freqEventType, minFreq, 
                        maxFreq, startTime, endTime, description); 
            }
        }
        
        if (event.isStartElement()) { 
            StartElement startElement = event.asStartElement();
            String startElementTag = startElement.getName().getLocalPart();
            switch (startElementTag) {

                case "EventName":
                    event = eventReader.nextEvent();
                    eventName = event.asCharacters().getData();
                    break;
                    
                case "EventType":
                    event = eventReader.nextEvent();
                    String fe = event.asCharacters().getData();
                    freqEventType = FreqEventType.valueOf(fe);
                    break;
                        
                case "MaxFreq":
                    event = eventReader.nextEvent();
                    maxFreq = event.asCharacters().getData();
                    break;

                case "CenterFreq":
                    event = eventReader.nextEvent();
                    centerFreq = event.asCharacters().getData();
                    break;

                case "MinFreq":
                    event = eventReader.nextEvent();
                    minFreq = event.asCharacters().getData();
                    break;

                case "EndTime":
                    event = eventReader.nextEvent();
                    endTime = event.asCharacters().getData();
                    break;

                case "MidTime":
                    event = eventReader.nextEvent();
                    midTime = event.asCharacters().getData();
                    break;

                case "StartTime":
                    event = eventReader.nextEvent();
                    startTime = event.asCharacters().getData();
                    break;

                case "Description":
                    event = eventReader.nextEvent();
                    description = event.asCharacters().getData();
                    break;
            }
        }
    }
}
