package macbeth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RequestServer extends Thread {
    private List<String> requests;
    private boolean running;

    public RequestServer() {
        requests = Collections.synchronizedList(new ArrayList<String>());
        running = false;
    }

    public void exit() {
        running = false;
    }

    public void run() {
        running = true;
        Random random = new Random();
        try {
            while (running) {
                Thread.sleep(random.nextInt(10));
                requests.add(new String("REQ-"+random.nextInt(10000)));
            }
        }
        catch (InterruptedException ie) {
        }
        System.out.println("Exiting Server");
    }

    public String getNextRequest() {
        if (requests.isEmpty()) {
            return "NOTHING";
        }
        return requests.remove(0);
    }

    public void displayRequests() {
        System.out.println("Requests:");
        int i=1;
        // When using Collections.synchronizedList, any iteration
        // through that list must be in a synchronized block:
        synchronized (requests) {
            for (String request : requests) {
                System.out.println(i + ": " + request);
                i++;
            }
        }
        System.out.println("--END--");
    }
}
