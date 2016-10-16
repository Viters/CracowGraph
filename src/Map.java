import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by sir.viters on 13.10.2016.
 */
class Map {
    private HashMap<Long, Way> waysArray;
    private HashMap<Long, Node> nodesArray;

    Map(HashMap<Long, Way> waysArray, HashMap<Long, Node> nodesArray) {
        this.waysArray = waysArray;
        this.nodesArray = nodesArray;
    }

    HashMap<Long, Way> getWaysArray() {
        return waysArray;
    }

    HashMap<Long, Node> getNodesArray() {
        return nodesArray;
    }

    void filter() {
        waysArray.values().removeIf(v -> !Way.getAllowedTypes().contains(v.getType()));
        waysArray.values().removeIf(v -> v.getConnectedNodes().get(0).equals(v.getConnectedNodes().get(v.getConnectedNodes().size() -  1)));
        waysArray.values().forEach(v -> v.getConnectedNodes().subList(1, v.getConnectedNodes().size() - 1).clear());
        waysArray.values().forEach(v -> v.getConnectedNodes().forEach(i -> nodesArray.get(i).confirmNode()));
        nodesArray.values().removeIf(v -> !v.isConfirmed());
    }

    void calculateDistances() {
        waysArray.values().forEach(v -> {
            Node start = nodesArray.get(v.getConnectedNodes().get(0));
            Node end = nodesArray.get(v.getConnectedNodes().get(1));
            v.calculateDistance(start, end);
        });
    }

    void export() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("output.json", "UTF-8");
        writer.println("[");

        int i = 1;
        for (Way v : waysArray.values()) {
            JSONWay data = new JSONWay(v, this);
            writer.println("\t{");

            writer.print("\t\t\"startLat\": ");
            writer.print("\"" + data.getStartLat() + "\",\n");

            writer.print("\t\t\"startLon\": ");
            writer.print("\"" + data.getStartLon() + "\",\n");

            writer.print("\t\t\"endLat\": ");
            writer.print("\"" + data.getEndLat() + "\",\n");

            writer.print("\t\t\"endLon\": ");
            writer.print("\"" + data.getEndLon() + "\",\n");

            writer.print("\t\t\"distance\": ");
            writer.print("\"" + data.getDistance() + "\",\n");

            writer.print("\t\t\"type\": ");
            writer.print("\"" + data.getType() + "\"\n");

            if (i++ != waysArray.values().size())
                writer.println("\t},");
            else
                writer.println("\t}");
        }

        writer.println("]");

        writer.close();
    }
}
