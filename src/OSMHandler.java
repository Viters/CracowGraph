import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Objects;


/**
 * Class for handling Open Street Map XML files.
 * It will search through them and create two HashMaps,
 * one for "node' tags, and one for "way" tags.
 *
 * @author Łukasz Szcześniak
 * @version 20161016
 */
class OSMHandler extends DefaultHandler {
    private HashMap<Long, Node> foundNodes;
    private HashMap<Long, Way> foundWays;
    /**
     * Checks if "way" tag is currently processed, to
     * communicate between startElement and endElement functions.
     */
    private boolean wayCurrentlyProcessed;
    /**
     * Stores currently processed "way" tag ID, to push nodes IDs to
     * proper HashMap element.
     */
    private long wayCurrentId;

    OSMHandler() {
        foundNodes = new HashMap<>();
        foundWays = new HashMap<>();
    }

    /**
     * Method that is called after each opened tag in XML file.
     * <p>
     * It will check for:
     * <ul>
     * <li>node - contains information about one street segment</li>
     * <li>way - contains information about street and a list of its segments</li>
     * <li>nd - one element of list segments</li>
     * <li>tag - specific data about street</li>
     * </ul>
     * and delegate found data to proper method.
     *
     * @param uri        - The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being performed.
     * @param localName  - The local name (without prefix), or the empty string if Namespace processing is not being performed.
     * @param qName      - The qualified name (with prefix), or the empty string if qualified names are not available.
     * @param attributes - The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
     * @throws SAXException
     */
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

    /**
     * Creates new Way object with proper ID and puts it into foundWays HashMap.
     *
     * @param attributes - The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
     */
    private void processWay(Attributes attributes) {
        wayCurrentlyProcessed = true;
        wayCurrentId = Long.parseLong(attributes.getValue("id"));
        Way way = new Way();
        way.setId(wayCurrentId);
        foundWays.put(wayCurrentId, way);
    }

    /**
     * Creates new Node object, sets its parameters and puts it into foundNodes HashMap.
     *
     * @param attributes - The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
     */
    private void processNode(Attributes attributes) {
        Node node = new Node();
        node.setId(Long.parseLong(attributes.getValue("id")));
        node.setLat(Double.parseDouble(attributes.getValue("lat")));
        node.setLon(Double.parseDouble(attributes.getValue("lon")));
        foundNodes.put(node.getId(), node);
    }

    /**
     * Adds new segment to currently processed Way object.
     *
     * @param attributes - The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
     */
    private void processWayChildNode(Attributes attributes) {
        long currentWayChildId = Long.parseLong(attributes.getValue("ref"));
        foundWays.get(wayCurrentId).getConnectedNodes().add(currentWayChildId);
    }

    /**
     * Adds street type to currently processed Way object.
     *
     * @param attributes - The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
     */
    private void processWayChildTag(Attributes attributes) {
        if (!Objects.equals(attributes.getValue("k"), "highway"))
            return;

        String currentWayType = attributes.getValue("v");
        foundWays.get(wayCurrentId).setType(currentWayType);
    }

}
