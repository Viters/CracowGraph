import java.util.ArrayList;

/**
 * Object of this class is ready to be saved as JSON object.
 * It creates a structure of Node and Way objects.
 *
 * @author Łukasz Szcześniak
 * @version 20161025
 */
class JSONData {
    private ArrayList<JSONWay> ways;
    private ArrayList<JSONNode> nodes;

    /**
     * Create JSONData object based on provided Map.
     *
     * @param map - object that will be parsed
     */
    JSONData(Map map) {
        ways = new ArrayList<>();
        nodes = new ArrayList<>();
        map.getWaysArray().values().forEach(v -> ways.add(new JSONWay(v)));
        map.getNodesArray().values().forEach(v -> nodes.add(new JSONNode(v)));
    }
}
