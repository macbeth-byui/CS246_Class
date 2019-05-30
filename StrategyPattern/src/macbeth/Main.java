package macbeth;

public class Main {

    public static void main(String[] args) {
	    Trip trip = new Trip();
	    trip.setMode(new SwimMode());
	    trip.travel("ludington, mi", "milwaukee, wi");
	    trip.setMode(new AirMode());
	    trip.travel("idaho falls, id", "bangalore, india");
    }
}
