import java.util.HashMap;

/**
 * Created by sir.viters on 13.10.2016.
 */
public class Map {
    private final HashMap<Long, Way> waysArray;
    private final HashMap<Long, Node> nodesArray;

    Map(HashMap<Long, Way> waysArray, HashMap<Long, Node> nodesArray) {
        this.waysArray = waysArray;
        this.nodesArray = nodesArray;
    }

    public HashMap<Long, Way> getWaysArray() {
        return waysArray;
    }

    public HashMap<Long, Node> getNodesArray() {
        return nodesArray;
    }
}
