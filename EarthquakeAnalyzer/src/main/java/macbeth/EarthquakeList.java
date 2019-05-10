package macbeth;

import java.util.ArrayList;

/**
 * Contains the list of earthquake events from the USGS Earthquake JSON
 *
 * https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
 */
public class EarthquakeList {
    private ArrayList<EarthquakeEvent> features;

    /**
     * Get the full earthquake list
     *
     * @return List
     */
    public ArrayList<EarthquakeEvent> getEarthquakes() {
        return features;
    }
}
