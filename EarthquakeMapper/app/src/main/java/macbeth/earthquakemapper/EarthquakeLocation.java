package macbeth.earthquakemapper;

import java.util.List;

/**
 * Contains the coordinate information from the USGS Earthquake JSON
 *
 * https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
 */
public class EarthquakeLocation {
    private List<Float> coordinates;

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
