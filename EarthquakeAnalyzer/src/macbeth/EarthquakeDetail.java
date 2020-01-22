package macbeth;

/**
 * Contains the detailed earthquake information from the USGS Earthquake JSON
 *
 * https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
 */
public class EarthquakeDetail {

    private String place;
    private float mag;
    private long time;

    public EarthquakeDetail() {
    }

    /**
     * Place or location of the earthquake
     *
     * @return place
     */
    public String getPlace() {
        return place;
    }

    /**
     * Magnitude of the earthquake
     *
     * @return magniuted
     */
    public float getMag() {
        return mag;
    }

    /**
     * Time of the earthquake (ms since epoch)
     *
     * @return time
     */
    public long getTime() {
        return time;
    }

}
