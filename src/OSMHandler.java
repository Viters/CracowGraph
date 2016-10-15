import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Viters on 2016-10-13.
 */
class OSMHandler extends DefaultHandler {
    String currentKey;
    private HashMap<Long, Node> foundNodes;
    private HashMap<Long, Way> foundWays;
    private boolean wayCurrentlyProcessed;
    private long wayCurrentId;

    OSMHandler() {
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
            boolean foundWayChildNode = qName.equalsIgnoreCase("nd");
            boolean foundWayChildTag = qName.equalsIgnoreCase("tag");
            if (foundWayChildNode) {
                processWayChildNode(attributes);
            } else if (foundWayChildTag) {
                processWayChildTag(attributes);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("way"))
            wayCurrentlyProcessed = false;
    }

    HashMap<Long, Node> getFoundNodes() {
        return foundNodes;
    }

    HashMap<Long, Way> getFoundWays() {
        return foundWays;
    }

    private void processWay(Attributes attributes) {
        wayCurrentlyProcessed = true;
        wayCurrentId = Long.parseLong(attributes.getValue("id"));
        Way way = new Way();
        way.setId(wayCurrentId);
        foundWays.put(wayCurrentId, way);
    }

    private void processNode (Attributes attributes){
        Node node = new Node();
        node.setId(Long.parseLong(attributes.getValue("id")));
        node.setLat(Double.parseDouble(attributes.getValue("lat")));
        node.setLon(Double.parseDouble(attributes.getValue("lon")));
        foundNodes.put(node.getId(), node);
    }

    private void processWayChildNode(Attributes attributes) {
        long currentWayChildId = Long.parseLong(attributes.getValue("ref"));
        foundWays.get(wayCurrentId).getConnectedNodes().add(currentWayChildId);
    }

    private void processWayChildTag(Attributes attributes){
        if (!Objects.equals(attributes.getValue("k"), "highway"))
            return;

        String currentWayType = attributes.getValue("v");
        foundWays.get(wayCurrentId).setType(currentWayType);
    }

}
