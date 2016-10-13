import java.util.HashMap;

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
        waysArray.values().removeIf(v -> v.getConnectedNodes().get(0).equals(v.getConnectedNodes().get(v.getConnectedNodes().size() -  1)));
        waysArray.values().forEach(v -> v.getConnectedNodes().subList(1, v.getConnectedNodes().size() - 1).clear());
        waysArray.values().forEach(v -> v.getConnectedNodes().forEach(i -> nodesArray.get(i).confirmNode()));
        nodesArray.values().removeIf(v -> !v.isConfirmed());
    }

    public void calculateDistances() {
        waysArray.values().forEach(v -> {
            Node start = nodesArray.get(v.getConnectedNodes().get(0));
            Node end = nodesArray.get(v.getConnectedNodes().get(1));

            v.calculateDistance(start.getLon(), start.getLat(), end.getLon(), end.getLat());
        });
    }
}
