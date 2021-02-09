package macbeth.earthquakemapper;

import com.google.gson.Gson;

public class RefreshMapTask extends Thread {

    private MainActivity activity;

    public RefreshMapTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        super.run();
        HTTPHelper http = new HTTPHelper();
        Gson gson = new Gson();
        String jsonData = http.readHTTP("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson");
        EarthquakeList earthquakeList = gson.fromJson(jsonData, EarthquakeList.class);
        for (EarthquakeEvent e : earthquakeList.getFeatures()) {
            activity.createPointOnMap(e.getGeometry().getLatitude(),
                    e.getGeometry().getLongitude(),
                    e.getProperties().getMag());
        }
    }
}



