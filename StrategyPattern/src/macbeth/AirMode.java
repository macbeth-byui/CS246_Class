package macbeth;

import java.util.List;

public class AirMode implements ModeOfTravel {
    @Override
    public List<String> getRoute(String start, String finish) {
        System.out.println("Getting best Air route.");
        return null;
    }
}
