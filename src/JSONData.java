import java.util.ArrayList;

/**
 * Created by sir.viters on 19.10.2016.
 */
class JSONData {
    private ArrayList<JSONWay> ways;
    private ArrayList<JSONNode> nodes;

    JSONData(Map map) {
        ways = new ArrayList<>();
        nodes = new ArrayList<>();
        map.getWaysArray().values().forEach(v -> ways.add(new JSONWay(v)));
        map.getNodesArray().values().forEach(v -> nodes.add(new JSONNode(v)));
    }
}
