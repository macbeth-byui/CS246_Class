package macbeth;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;

public class EarthquakeAnalyzer {

    /**
     * Read all data from a URL (GET request) and return the content as a string
     *
     * @param url - The website with all options included
     * @return - All content as a string
     */

    private String loadWebData(String url) {
        StringBuilder data = new StringBuilder();
        try {
            URL urlObj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Read all data from the website into a single string
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
                    data.append(line);
                }
            }
            while (line != null);

        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }

        return data.toString();
    }

    public void displayEarthquakeData() {
        String data = loadWebData("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson");

    }

    public static void main(String[] args) {
        EarthquakeAnalyzer ea = new EarthquakeAnalyzer();
        ea.displayEarthquakeData();
    }
}
