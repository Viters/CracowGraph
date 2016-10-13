import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Viters on 2016-10-13.
 */
public class OSMHandler extends DefaultHandler {
    private ArrayList<Node> foundNodes;
    private ArrayList<Node> foundWays;

    public OSMHandler() {
        foundNodes = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        boolean fnode = qName.equalsIgnoreCase("node");
        boolean fway = qName.equalsIgnoreCase("way");
        if (fnode) {
            processNode(attributes);
        } else if (fway) {
            processWay(attributes);
        }
    }

    public ArrayList<Node> getFoundNodes() {
        return foundNodes;
    }

    public ArrayList<Node> getFoundWays() {
        return foundWays;
    }

    private void processNode(Attributes attributes) {
        Node node = new Node();
        node.setId(Long.parseLong(attributes.getValue("id")));
        node.setLat(Double.parseDouble(attributes.getValue("lat")));
        node.setLon(Double.parseDouble(attributes.getValue("lon")));
        foundNodes.add(node);
    }

    private void processWay(Attributes attributes) {

    }
}
