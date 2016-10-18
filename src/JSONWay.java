/**
 * Object of this class is ready to be saved as JSON object.
 * It maps Way and Map objects to a single object with requested values.
 *
 * @author Łukasz Szcześniak
 * @version 20161016
 */
class JSONWay {
    final private double startLon;
    final private double startLat;
    final private double endLon;
    final private double endLat;
    final private double distance;
    final private String type;
    final private String name;
    final private boolean isRoundabout;

    /**
     * Create JSONWay object and set requested values.
     *
     * @param way - object that will be mapped
     * @param map - object that provides additional information needed for mapping process
     */
    JSONWay(Way way) {
        startLon = way.getFirstNode().getLon();
        startLat = way.getLastNode().getLat();
        endLon = way.getLastNode().getLon();
        endLat = way.getLastNode().getLat();
        distance = way.getDistance();
        type = way.getType();
        name = way.getName();
        isRoundabout = way.isRoundabout();
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

    String getName() {
        return name;
    }

    boolean isRoundabout() {
        return isRoundabout;
    }
}
