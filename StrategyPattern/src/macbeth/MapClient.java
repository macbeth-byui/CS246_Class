package macbeth;

import java.util.List;

public class MapClient {

    private Paths path;

    public void getDirections(String start, String end) {
        if (path == null) {
            path = new ScenicPath();
        }
        List<String> results = path.findRoute(start, end);
    }

    public void selectPathStrategy(Paths pathStrategy) {
        path = pathStrategy;
    }

    public static void main(String []args) {
        MapClient mapClient = new MapClient();
        mapClient.getDirections("Rexburg","Paris");
        //Paths paths = new ScenicPath();
        mapClient.selectPathStrategy(new ShortestPath());
        mapClient.getDirections("Rexburg","Paris");
        mapClient.selectPathStrategy(new FastestPath());
        mapClient.getDirections("Rexburg","Paris");
    }
}
