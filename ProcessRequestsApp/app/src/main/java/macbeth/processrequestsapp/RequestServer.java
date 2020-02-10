package macbeth.processrequestsapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RequestServer extends Thread {
    private List<String> requests;

    public RequestServer() {
        requests = Collections.synchronizedList(new ArrayList<String>());
    }

    /**
     * Randomly create requests forever.
     */
    public void run() {
        Random random = new Random();
        try {
            while (true) {
                Thread.sleep(random.nextInt(3000));
                requests.add(new String("REQ-"+random.nextInt(10000)));

                // Update the Display to represent the modified request list
            }
        }
        catch (InterruptedException ie) {
        }
    }

    public List<String> getAllRequests() {
        return requests;
    }

    public int getNumRequests() {
        return requests.size();
    }

    /**
     * Remove the next request and return the value.  If its empty, then return "None"
     * @return
     */
    public String getNextRequest() {
        if (requests.isEmpty()) {
            return "None";
        }
        String request = requests.remove(0);

        // Update the Display to represent the modified request list

        return request;
    }
}

