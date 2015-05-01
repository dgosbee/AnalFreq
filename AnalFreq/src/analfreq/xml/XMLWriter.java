package analfreq.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This is just a first pass at saving a FreqEvent as XML.
 * The code so far just sets up the mechanics. Next it should be
 * converted to use an actual FreqEvent object instead of String literals.
 * Eventually this code will write the data from a real session.
 */
public class XMLWriter {

    private static final String xmlPath = "src/analfreq/xml/project-data.xml";

    public static void writeXML() {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("FreqEvent");
            doc.appendChild(rootElement);

            Element name = doc.createElement("EventName");
            name.appendChild(doc.createTextNode("KICK PUNCH"));
            rootElement.appendChild(name);
        
            Element maxFreq = doc.createElement("MaxFreq");
            maxFreq.appendChild(doc.createTextNode("100"));
            rootElement.appendChild(maxFreq);
            
            Element centerFreq = doc.createElement("CenterFreq");
            centerFreq.appendChild(doc.createTextNode("90"));
            rootElement.appendChild(centerFreq);
            
            Element minFreq = doc.createElement("MinFreq");
            minFreq.appendChild(doc.createTextNode("80"));
            rootElement.appendChild(minFreq);
            
            Element endTime = doc.createElement("EndTime");
            endTime.appendChild(doc.createTextNode("60"));
            rootElement.appendChild(endTime);
            
            Element centerTime = doc.createElement("CenterTime");
            centerTime.appendChild(doc.createTextNode("30"));
            rootElement.appendChild(centerTime);
            
            Element startTime = doc.createElement("StartTime");
            startTime.appendChild(doc.createTextNode("1"));
            rootElement.appendChild(startTime);
        
            Element description = doc.createElement("Description");
            description.appendChild(doc.createTextNode("Lots of kick power here"));
            rootElement.appendChild(description);
           
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlPath));

		// Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

            System.out.println("Sample file saved! "+xmlPath);

        } catch (ParserConfigurationException | TransformerException pce) {}

    }

}
