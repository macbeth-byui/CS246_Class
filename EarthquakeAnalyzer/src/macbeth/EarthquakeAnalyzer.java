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
     */

    public void displayEarthquakeData() {
        HTTPHelper httpHeler = new HTTPHelper();
        String data = httpHeler.readHTTP("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson");
        Gson gson = new Gson();
        EarthquakeList list = gson.fromJson(data, EarthquakeList.class);
        Collections.sort(list.getEarthquakes(), new Comparator<EarthquakeEvent>() {
            @Override
            public int compare(EarthquakeEvent o1, EarthquakeEvent o2) {
                return Float.compare(o1.getDetail().getMag(), o2.getDetail().getMag());
            }
        });
        for (EarthquakeEvent e : list.getEarthquakes()) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        EarthquakeAnalyzer ea = new EarthquakeAnalyzer();
        ea.displayEarthquakeData();
    }
}
