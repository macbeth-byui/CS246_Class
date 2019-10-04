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
        Gson gson = new Gson();
        EarthquakeList eql = gson.fromJson(data, EarthquakeList.class);
        // The Collections.sort function is a very useful tool to sort a list.  If the list was just numbers
        // or Strings, then we could just pass the list in and it would sort.  However, to sort the list in the
        // EarthquakeList class, we will need to tell Java how to compare 2 EarthquakeEvent's. By implementing a
        // Comparator object we can tell it how to compare.  Notice that Comparator is an Interface.  To create an
        // object of an interface, we have to supply a definition for the abstract function within it.  We call
        // this an anonymous class since we have the function implementation without giving a name to the class.

        /*Collections.sort(eql.getEarthquakes(), new Comparator<EarthquakeEvent>() {
            @Override
            public int compare(EarthquakeEvent o1, EarthquakeEvent o2) {
                // We are going to define earthquake comparisons by comparing their magnitudes.
                // Note that this function needs to return an integer that describes the difference between
                // the 2 objects.  We will use the compare function provided in the Float class to compare
                // the 2 magnitudes.
                return Float.compare(o1.getDetail().getMag(), o2.getDetail().getMag());
            }
        });*/

        // There is another way we can do this.  We can create a lambda function (a function that has no name) that
        // will be interpreted as the compare function.  Our function still needs 2 parameters (o1 and o2) just like
        // the compare function did above.  We use the "->" to signal that this is a lambda function.  The value
        // on the right side of the arrow is what will be returned.  Compare this code with the one above.  They
        // do the same thing.

        Collections.sort(eql.getEarthquakes(), (o1,o2)->Float.compare(o1.getDetail().getMag(), o2.getDetail().getMag()));

        for (EarthquakeEvent e : eql.getEarthquakes()) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        EarthquakeAnalyzer ea = new EarthquakeAnalyzer();
        ea.displayEarthquakeData();
    }
}
