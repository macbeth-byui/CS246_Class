package macbeth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RequestServer {
    private List<String> requests;

    public RequestServer() {
        requests = new ArrayList<String>();
    }

    public void exit() {

    }

    public void run() {
        Random random = new Random();
        try {
            while (true) {
                Thread.sleep(random.nextInt(3000));
                requests.add(new String("REQ-"+random.nextInt(10000)));
            }
        }
        catch (InterruptedException ie) {
        }
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
