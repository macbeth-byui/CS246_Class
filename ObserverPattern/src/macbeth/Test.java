package macbeth;

public class Test {

    public static void main(String []args) {
        // Start the DataReader thread
        DataReader reader = new DataReader();
        reader.start();

        // Create multiple clients that will receive data from the
        // DataReader.  Note that the messages sent by the DataReader
        // come from a different Thread so these are asynchronous.  This
        // is what makes the Observer pattern so useful.
        Client client1 = new Client("Hydraulics", reader);
        Client client2 = new Client("FlightControls", reader);
        Client client3 = new Client("Electronics", reader);

    }
}
