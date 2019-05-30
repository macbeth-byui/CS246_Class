package macbeth;

import java.util.List;

public class SwimMode implements ModeOfTravel {

    @Override
    public List<String> getRoute(String start, String finish) {
        System.out.println("Getting Best Swim Route");
        return null;
    }
}
