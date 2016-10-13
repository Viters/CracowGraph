import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Map mapData = parseMapData("small.xml");

        mapData.filter();

        mapData.calculateDistances();

        mapData.getWaysArray().values().forEach(v -> System.out.println(v.getDistance()));
    }

    private static Map parseMapData(String inputName) {
        File input = new File(inputName);
        Map map = null;

        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            OSMHandler osmHandler = new OSMHandler();
            saxParser.parse(input, osmHandler);

            map = new Map(osmHandler.getFoundWays(), osmHandler.getFoundNodes());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
