package macbeth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RequestServer {
    private List<String> requests;
    boolean running;

    public RequestServer() {
        requests = Collections.synchronizedList(new ArrayList<String>());
        running = false;
    }

    public void run() {
        System.out.println("Requests Server Running");
        Random random = new Random();
        running = true;
        try {
            while (running) {
                Thread.sleep(random.nextInt(3000));
                requests.add(new String("REQ-"+random.nextInt(10000)));
            }
        }
        catch (InterruptedException ie) {
        }
    }

    public void exit() {
        running = false;
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
        for (String request : requests ) {
            System.out.println(i+": "+request);
            i++;
        }
        System.out.println("--END--");
    }
}
