import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) {
        Map mapData = parseMapData("map.xml");

        mapData.filter();

        mapData.calculateDistances();

        try {
            mapData.export();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
