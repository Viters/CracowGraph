/**
 * Created by sir.viters on 16.10.2016.
 */
class JSONWay {
    private double startLon;
    private double startLat;
    private double endLon;
    private double endLat;
    private double distance;
    private String type;

    JSONWay(Way way, Map map) {
        startLon = map.getNodesArray().get(way.getConnectedNodes().get(0)).getLon();
        startLat = map.getNodesArray().get(way.getConnectedNodes().get(0)).getLat();
        endLat = map.getNodesArray().get(way.getConnectedNodes().get(1)).getLon();
        endLon = map.getNodesArray().get(way.getConnectedNodes().get(1)).getLat();
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

    public String getType() {
        return type;
    }
}
