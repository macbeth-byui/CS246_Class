package macbeth;

import java.util.ArrayList;


/**
 * Contains the coordinate information from the USGS Earthquake JSON
 *
 * https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
 */
public class EarthquakeLocation {
    private ArrayList<Float> coordinates;

    /**
     * Longitude of the earthquake.
     *
     * @return longitude
     */
    public float getLongitude() {
        return coordinates.get(0);
    }

    /**
     * Latitude of the earthquake
     *
     * @return latitude
     */
    public float getLatitude() {
        return coordinates.get(1);
    }

}
