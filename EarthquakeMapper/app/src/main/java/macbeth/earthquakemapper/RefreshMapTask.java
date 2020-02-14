package macbeth.earthquakemapper;

import android.os.AsyncTask;

import com.google.gson.Gson;

public class RefreshMapTask extends AsyncTask<String, Void, Void> {

    private MainActivity activity;

    public RefreshMapTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.enablePB();
    }

    @Override
    protected Void doInBackground(String... urls) {
        HTTPHelper http = new HTTPHelper();
        Gson gson = new Gson();
        String jsonData = http.readHTTP(urls[0]);
        EarthquakeList earthquakeList = gson.fromJson(jsonData, EarthquakeList.class);
        for (EarthquakeEvent e : earthquakeList.getFeatures()) {
            activity.createPointOnMap(e.getGeometry().getLatitude(),
                    e.getGeometry().getLongitude(),
                    e.getProperties().getMag());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        activity.disablePB();
    }
}

/*
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
 */


