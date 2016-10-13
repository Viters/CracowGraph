import java.util.ArrayList;

/**
 * Created by sir.viters on 13.10.2016.
 */
public class Way {
    private long id;
    private ArrayList<Long> connectedNodes;
    private double distance;

    Way() {
        connectedNodes = new ArrayList<>();
        distance = 0;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public double getDistance() {
        return distance;
    }

    public void calculateDistance(double lon1, double lat1, double lon2, double lat2) {
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

    public ArrayList<Long> getConnectedNodes() {
        return connectedNodes;
    }
}
