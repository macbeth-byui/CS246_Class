package macbeth;

import java.util.List;

public class ScenicPath implements Path {

    @Override
    public List<String> getDirections(String start, String end) {
        System.out.println("Calculating scenic path between "+start+" and "+end);
        return null;
    }
}
