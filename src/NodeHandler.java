import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

/**
 * Created by Viters on 2016-10-13.
 */
public class NodeHandler extends DefaultHandler {
    private boolean fnode;
    private ArrayList<Node> foundNodes;

    public NodeHandler() {
        foundNodes = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        fnode = qName.equalsIgnoreCase("node");
        if (fnode) {
            Node node = new Node();
            node.setId(Long.parseLong(attributes.getValue("id")));
            node.setLat(Double.parseDouble(attributes.getValue("lat")));
            node.setLon(Double.parseDouble(attributes.getValue("lon")));
            foundNodes.add(node);
        }
    }

    public ArrayList<Node> getFoundNodes() {
        return foundNodes;
    }
}
