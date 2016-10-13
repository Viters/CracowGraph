import java.util.ArrayList;

/**
 * Created by sir.viters on 13.10.2016.
 */
public class Way {
    private long id;
    private ArrayList<Long> connectedNodes;

    Way() {
        connectedNodes = new ArrayList<>();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {

        return id;
    }

    public ArrayList<Long> getConnectedNodes() {
        return connectedNodes;
    }
}
