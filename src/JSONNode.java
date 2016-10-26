/**
 * Object of this class is ready to be saved as JSON object.
 * It maps Node object to a parsed object with requested values.
 *
 * @author Łukasz Szcześniak
 * @version 20161016
 */
class JSONNode {
    private final long id;
    private final double lat;
    private final double lon;

    /**
     * Create JSONWay object and set requested values.
     *
     * @param node - object that will be parsed
     */
    JSONNode(Node node) {
        this.id = node.getId();
        this.lat = node.getLat();
        this.lon = node.getLon();
    }
}
