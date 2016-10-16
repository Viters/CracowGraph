import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class for operations on Open Street Map street data.
 * It does not hold actual Node data, only referenced to it via ID.
 *
 * @author Łukasz Szcześniak
 * @version 20161016
 */
class Way {
    private long id;
    private ArrayList<Long> connectedNodes;
    private double distance;
    private String type;
    /**
     * Street types that will NOT be deleted while Map filtering.
     *
     * @see <a href="https://wiki.openstreetmap.org/wiki/Pl:Map_Features">Open Street Map documentation</a>
     */
    private static List<String> allowedTypes = Arrays.asList(
            "motorway", "trunk", "primary", "secondary",
            "tertiary", "unclassified", "residential", "motorway_link",
            "trunk_link", "primary_link", "secondary_link",
            "tertiary_link", "living_street", "pedestrian"
            );

    Way() {
        connectedNodes = new ArrayList<>();
        distance = 0;
    }

    void setId(long id) {
        this.id = id;
    }

    long getId() {
        return id;
    }

    double getDistance() {
        return distance;
    }

    /**
     * Calculates distance between two nodes using haversine formula.
     *
     * @param start - first segment of street
     * @param end - last segment of street
     *
     * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html">Haversine formula</a>
     */
    void setDistance(Node start, Node end) {
        double lon1 = start.getLon();
        double lat1 = start.getLat();
        double lon2 = end.getLon();
        double lat2 = end.getLat();

        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;
        double latDistance = Math.toRadians(latDiff);
        double lngDistance = Math.toRadians(lonDiff);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(lat1))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        this.distance = 6371 * c * 1000;
    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    ArrayList<Long> getConnectedNodes() {
        return connectedNodes;
    }

    Long getFirstNodeId() {
        return connectedNodes.get(0);
    }

    Long getLastNodeId() {
        return connectedNodes.get(connectedNodes.size() - 1);
    }

    static List<String> getAllowedTypes() {
        return allowedTypes;
    }
}
