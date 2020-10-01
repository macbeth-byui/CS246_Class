package macbeth;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Contains the all earthquake event information from the USGS Earthquake JSON
 *
 * https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
 */
public class EarthquakeEvent {
    @SerializedName("properties")
    private EarthquakeDetail detail;
    @SerializedName("geometry")
    private EarthquakeLocation location;

    /**
     * Detail of the earthquake
     *
     * @return EarthquakeDetail
     */
    public EarthquakeDetail getDetail() {
        return detail;
    }

    /**
     * Location of the earthquake
     *
     * @return EarthquakeLocation
     */
    public EarthquakeLocation getPoint() {
        return location;
    }

    /**
     * Create a string that contains a summary detail of the earthquake
     *
     * @return String
     */
    public String toString() {
        String output;
        try {
            output = String.format("%s\t%f\t%f\t%f\t%s",
                    new Date(detail.getTime()),
                    detail.getMag(),
                    location.getLatitude(),
                    location.getLongitude(),
                    detail.getPlace());
        } catch (NullPointerException e) {
            output = "No Data";
        }

        return output;
    }

}
