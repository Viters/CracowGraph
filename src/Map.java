import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.HashMap;

/**
 * Main data holding structure. It holds all Ways and Nodes object extracted from XML.
 * <p>
 * Putting them into HashMaps is useful for achieving small searching complexity.
 * Also this class will perform all operations to process all data into requested output structure.
 *
 * @author Łukasz Szcześniak
 * @version 20161016
 */
class Map {
    private HashMap<Long, Way> waysArray;
    private HashMap<Long, Node> nodesArray;

    private Map(HashMap<Long, Way> waysArray, HashMap<Long, Node> nodesArray) {
        this.waysArray = waysArray;
        this.nodesArray = nodesArray;
    }

    /**
     * Filter data using strict predicates.
     */
    void filter() {
        // Remove all non allowed street types
        waysArray.values().removeIf(v -> !(Way.getAllowedTypes().contains(v.getType()) || v.isRoundabout()));

        // Get nodes from waysArray and confirm their occurences in nodesArray
        waysArray.values().forEach(v -> v.getConnectedNodes().forEach(Node::addOccurence));

        // Get first and last segment of each street and mark them as edges
        waysArray.values().forEach(v -> {
            v.getFirstNode().confirmEdge();
            v.getLastNode().confirmEdge();
        });

        // Remove all non-confirmed nodes from nodesArray
        nodesArray.values().removeIf(v -> !v.isConfirmed());

        // Remove all non-existent nodes from Ways objects
        waysArray.values().forEach(v -> v.getConnectedNodes().removeIf(i -> !i.isConfirmed()));

        waysArray.values().removeIf(v -> v.getFirstNode() == v.getLastNode() && !v.isRoundabout());

        System.out.println("Processed: " + waysArray.size());
    }

    /**
     * Calculate way for each Way object in waysArray.
     */
    void calculateDistances() {
        waysArray.values().forEach(v -> v.setDistance(v.getFirstNode(), v.getLastNode()));
    }

    /**
     * Export Map data into requested JSON structure.
     *
     * @param name - output file path
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    void export(String name) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(name, "UTF-8");
        writer.println("[");

        int i = 1;
        for (Way v : waysArray.values()) {
            JSONWay data = new JSONWay(v);
            writer.println("\t{");

            writer.print("\t\t\"startLat\": ");
            writer.print("\"" + data.getStartLat() + "\",\n");

            writer.print("\t\t\"startLon\": ");
            writer.print("\"" + data.getStartLon() + "\",\n");

            writer.print("\t\t\"endLat\": ");
            writer.print("\"" + data.getEndLat() + "\",\n");

            writer.print("\t\t\"endLon\": ");
            writer.print("\"" + data.getEndLon() + "\",\n");

            writer.print("\t\t\"distance\": ");
            writer.print("\"" + data.getDistance() + "\",\n");

            writer.print("\t\t\"type\": ");
            writer.print("\"" + data.getType() + ",\"\n");

            writer.print("\t\t\"name\": ");
            writer.print("\"" + data.getName() + ",\"\n");

            writer.print("\t\t\"roundabout\": ");
            writer.print("\"" + data.isRoundabout() + "\"\n");

            if (i++ != waysArray.values().size())
                writer.println("\t},");
            else
                writer.println("\t}");
        }

        writer.println("]");
        writer.close();
    }

    /**
     * Parse XML file to retrive requested informations and store them into Map object.
     *
     * @param inputName - XML Open Street Map file path
     * @return Map data structure holding all nodes and ways extracted from provided XML file
     */
    static Map parseMapData(String inputName) {
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
