package macbeth;

import java.util.List;

public class MapClient {

    private Path path;

    public void selectPathStrategy(Path path) {
        this.path = path;
    }

    public List<String> getDirections(String start, String end)
    {
        if (path == null) {
            selectPathStrategy(new FastestPath());
        }
        List<String> results = path.getDirections(start, end);
        return results;
    }

    public static void main(String []args) {
        MapClient client = new MapClient();
        List<String>result = client.getDirections("Rexburg","Kalamazoo");
        client.selectPathStrategy(new ScenicPath());
        result = client.getDirections("Rexburg","Kalamazoo");
        client.selectPathStrategy(new ShortestPath());
        result = client.getDirections("Rexburg","Kalamazoo");
    }
}
