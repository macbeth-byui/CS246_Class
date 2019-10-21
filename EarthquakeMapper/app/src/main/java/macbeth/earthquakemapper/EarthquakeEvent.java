package macbeth.earthquakemapper;

import java.util.Date;

/**
 * Contains the all earthquake event information from the USGS Earthquake JSON
 *
 * https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
 */
public class EarthquakeEvent {
    private EarthquakeDetail properties;
    private EarthquakeLocation geometry;

    public EarthquakeDetail getProperties() {
        return properties;
    }

    public EarthquakeLocation getGeometry() {
        return geometry;
    }
}
