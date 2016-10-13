/**
 * Created by Viters on 2016-10-13.
 */
public class Node {
    private long id;
    private double lon;
    private double lat;
    private boolean isNode;

    Node() {
        isNode = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void confirmNode() {
        isNode = true;
    }

    public boolean isConfirmed() {
        return isNode;
    }
}
