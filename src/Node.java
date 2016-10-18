/**
 * This class is responsible for holding data about street connections.
 *
 * @author Łukasz Szcześniak
 * @version 20161016
 */
class Node {
    final private double lon;
    final private double lat;
    /**
     * By default Node object is not confirmed and will be deleted
     * from Map. Nodes that connect different streets should
     * be confirmed before Map filtering.
     */
    private int occurrences;
    private boolean isEdge;

    Node(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
        occurrences = 0;
        isEdge = false;
    }

    double getLon() {
        return lon;
    }

    double getLat() {
        return lat;
    }

    void addOccurence() {
        ++occurrences;
    }

    void confirmEdge() {
        isEdge = true;
    }

    final boolean isConfirmed() {
        return occurrences > 1 || isEdge;
    }
}
