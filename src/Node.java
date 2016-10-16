/**
 * This class is responsible for holding data about street connections.
 *
 * @author Łukasz Szcześniak
 * @version 20161016
 */
class Node {
    private long id;
    private double lon;
    private double lat;
    /**
     * By default Node object is not confirmed and will be deleted
     * from Map. Nodes that connect different streets should
     * be confirmed before Map filtering.
     */
    private int occurrences;
    private boolean isEdge;

    Node() {
        occurrences = 0;
        isEdge = false;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    double getLon() {
        return lon;
    }

    void setLon(double lon) {
        this.lon = lon;
    }

    double getLat() {
        return lat;
    }

    void setLat(double lat) {
        this.lat = lat;
    }

    void addOccurence() {
        ++occurrences;
    }

    void confirmEdge() {
        isEdge = true;
    }

    boolean isConfirmed() {
        return occurrences > 1 || isEdge;
    }
}
