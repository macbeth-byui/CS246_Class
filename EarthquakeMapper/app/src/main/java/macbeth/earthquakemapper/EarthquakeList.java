package macbeth.earthquakemapper;

import java.util.List;

/**
 * Contains the list of earthquake events from the USGS Earthquake JSON
 *
 * https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
 */
public class EarthquakeList {
    private List<EarthquakeEvent> features;

    public List<EarthquakeEvent> getFeatures() {
        return features;
    }
}
