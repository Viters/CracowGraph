import java.util.ArrayList;

/**
 * Object of this class is ready to be saved as JSON object.
 * It maps Way and Map objects to a single object with requested values.
 *
 * @author Łukasz Szcześniak
 * @version 20161016
 */
class JSONWay {
    private final ArrayList<Long> nodesRef;
    private final int distance;
    private final String type;
    private final String name;

    /**
     * Create JSONWay object and set requested values.
     *
     * @param way - object that will be mapped
     */
    JSONWay(Way way) {
        distance = (int) way.getDistance();
        type = way.getType();
        name = way.getName();
        nodesRef = new ArrayList<>();
        way.getConnectedNodes().forEach(v -> nodesRef.add(v.getId()));
    }
}
