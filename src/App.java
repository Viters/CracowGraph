import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class App {

    public static void main(String[] args) {
        Map mapData;

        mapData = Map.parseMapData("big.xml");


        mapData.filter();

        mapData.calculateDistances();

        try {
            mapData.export("output.json");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
