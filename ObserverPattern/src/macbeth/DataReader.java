package macbeth;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class runs in a thread and receives messages from the user.
 * All messages are sent to all DataListeners that are registered
 * with this class.
 */
public class DataReader extends Thread {

    private List<DataListener> listeners;

    /**
     * Start with an empty list of listeners.
     */
    public DataReader() {
        listeners = new ArrayList<>();
    }

    /**
     * Add a new listener
     * @param listener
     */
    public void addListener(DataListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove an existing listener
     * @param listener
     */
    public void removeListener(DataListener listener) {
        listeners.remove(listener);
    }

    /**
     * Send a message to all the listeners.
     * @param data
     */
    private void notifyListeners(String data) {
        for (DataListener listener : listeners) {
            listener.dataReceived(data);
        }
    }

    @Override
    public void run() {
        System.out.println("DataReader is Ready to Receive Commands from User...");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String data = scanner.nextLine();
            notifyListeners(data); // Forward it to the listeners
        }
    }
}
