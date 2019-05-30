package macbeth;

public class Trip {
    private ModeOfTravel selectedMode;

    public void travel(String start, String finish) {
        selectedMode.getRoute(start, finish);
    }

    public void setMode(ModeOfTravel mode) {
        selectedMode = mode;
    }
}
