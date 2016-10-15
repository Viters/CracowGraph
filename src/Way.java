import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sir.viters on 13.10.2016.
 */
class Way {
    private long id;
    private ArrayList<Long> connectedNodes;
    private double distance;
    private String type;
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

    void calculateDistance(double lon1, double lat1, double lon2, double lat2) {
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

    static List<String> getAllowedTypes() {
        return allowedTypes;
    }
}
