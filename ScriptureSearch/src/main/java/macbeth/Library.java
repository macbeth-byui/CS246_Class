package macbeth;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Library {
    private List<Volume> volumes;
    private Gson gson;

    public Library() {
        volumes = new ArrayList<Volume>();
        gson = new Gson();
    }

    public void loadVolume(String title, String url) {
        String data = readHTTP(url);
        Volume volume = gson.fromJson(data, Volume.class);
        volumes.add(volume);
        System.out.println("Loaded "+title+".");
    }

    public void loadBook(String volumeTitle, String url) {
        String data = readHTTP(url);
        Book book = gson.fromJson(data, Book.class);

        Volume volume = new Volume();
        volume.setTitle(volumeTitle);
        volume.getBooks().add(book);

        volumes.add(volume);
        System.out.println("Loaded "+volumeTitle+".");
    }

    private String readHTTP(String url) {
        try {
            URL urlObj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder data = new StringBuilder();
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
                    data.append(line);
                }
            }
            while (line != null);
            return data.toString();
        }
        catch (IOException ioe) {
            System.out.println("Error Reading HTTP Data: " + ioe);
            return null;
        }
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

}
