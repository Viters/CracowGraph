import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by sir.viters on 13.10.2016.
 */
public class Map {
    private HashMap<Long, Way> waysArray;
    private HashMap<Long, Node> nodesArray;

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

    public void filter() {
        waysArray.forEach((k, v) -> {
            int nodesNum = v.getConnectedNodes().size();
            if (!v.getConnectedNodes().get(0).equals(v.getConnectedNodes().get(nodesNum - 1)))
                v.getConnectedNodes().subList(1, nodesNum - 1).clear();
        });

        waysArray.forEach((k, v) -> v.getConnectedNodes().forEach(i -> nodesArray.get(i).confirmNode()));

        Iterator<HashMap.Entry<Long, Node>> iter = nodesArray.entrySet().iterator();
        while(iter.hasNext()) {
            HashMap.Entry<Long, Node> entry = iter.next();
            if (!entry.getValue().isConfirmed()) {
                iter.remove();
            }
        }
    }
}
