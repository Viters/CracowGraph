/**
 * Created by sir.viters on 19.10.2016.
 */
class JSONNode {
    final long id;
    final double lat;
    final double lon;

    JSONNode(Node node) {
        this.id = node.getId();
        this.lat = node.getLat();
        this.lon = node.getLon();
    }
}
