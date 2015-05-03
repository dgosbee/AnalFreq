package analfreq.xml;

import analfreq.config.Config;
import analfreq.datamanager.DataManager;
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

    public static void readXML() throws FileNotFoundException, XMLStreamException {
        inputFactory = XMLInputFactory.newInstance();
        in = new FileInputStream(Config.XML_PATH);
        eventReader = inputFactory.createXMLEventReader(in);
        while (eventReader.hasNext()) {
            event = eventReader.nextEvent();
            processElements();     
        } 
    }

    private static void processElements() throws XMLStreamException {

        if(event.isEndElement()){
           EndElement endElement = event.asEndElement();
            String endElementTag = endElement.getName().getLocalPart();
            if(endElementTag.equalsIgnoreCase("FreqEvent")){
                DataManager.plotFreqEvent(eventName, minFreq, 
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
