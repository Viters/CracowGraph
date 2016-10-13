import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Viters on 2016-10-13.
 */
public class OSMHandler extends DefaultHandler {
    String currentKey;
    private HashMap<Long, Node> foundNodes;
    private HashMap<Long, ArrayList<Long>> foundWays;
    private boolean wayCurrentlyProcessed;
    private long wayCurrentId;

    public OSMHandler() {
        foundNodes = new HashMap<>();
        foundWays = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        boolean foundNode = qName.equalsIgnoreCase("node");
        boolean foundWay = qName.equalsIgnoreCase("way");

        if (foundNode) {
            processNode(attributes);
        } else if (foundWay) {
            processWay(attributes);
        }

        if (wayCurrentlyProcessed) {
            boolean foundWayChild = qName.equalsIgnoreCase("nd");
            if (foundWayChild) {
                processWayChild(attributes);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("way"))
            wayCurrentlyProcessed = false;
    }

    public HashMap<Long, Node> getFoundNodes() {
        return foundNodes;
    }

    public HashMap<Long, ArrayList<Long>> getFoundWays() {
        return foundWays;
    }

    private void processNode(Attributes attributes) {
        Node node = new Node();
        node.setId(Long.parseLong(attributes.getValue("id")));
        node.setLat(Double.parseDouble(attributes.getValue("lat")));
        node.setLon(Double.parseDouble(attributes.getValue("lon")));
        foundNodes.put(node.getId(), node);
    }

    private void processWay(Attributes attributes) {
        wayCurrentlyProcessed = true;
        wayCurrentId = Long.parseLong(attributes.getValue("id"));
        ArrayList<Long> arrayList = new ArrayList<>();
        foundWays.put(wayCurrentId, arrayList);
    }

    private void processWayChild(Attributes attributes) {
        long currentWayChildId = Long.parseLong(attributes.getValue("ref"));
        foundWays.get(wayCurrentId).add(currentWayChildId);
    }
}
