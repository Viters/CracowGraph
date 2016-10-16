import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) {
        Map mapData = Map.parseMapData("map.xml");

        mapData.filter();

        mapData.calculateDistances();

        try {
            mapData.export("output.json");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
