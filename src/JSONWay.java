/**
 * Object of this class is ready to be saved as JSON object.
 * It maps Way and Map objects to a single object with requested values.
 *
 * @author Łukasz Szcześniak
 * @version 20161016
 */
class JSONWay {
    private double startLon;
    private double startLat;
    private double endLon;
    private double endLat;
    private double distance;
    private String type;

    /**
     * Create JSONWay object and set requested values.
     *
     * @param way - object that will be mapped
     * @param map - object that provides additional information needed for mapping process
     */
    JSONWay(Way way, Map map) {
        startLon = map.getNodesArray().get(way.getFirstNodeId()).getLon();
        startLat = map.getNodesArray().get(way.getFirstNodeId()).getLat();
        endLon = map.getNodesArray().get(way.getLastNodeId()).getLon();
        endLat = map.getNodesArray().get(way.getLastNodeId()).getLat();
        distance = way.getDistance();
        type = way.getType();
    }

    double getStartLon() {
        return startLon;
    }

    double getStartLat() {
        return startLat;
    }

    double getEndLon() {
        return endLon;
    }

    double getEndLat() {
        return endLat;
    }

    double getDistance() {
        return distance;
    }

    String getType() {
        return type;
    }
}
