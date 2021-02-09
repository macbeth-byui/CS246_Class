package macbeth.earthquakemapper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

/**
 * This will display a Map of Earthquakes.  Reference the following ArcGIS tutorials:
 *
 * https://developers.arcgis.com/labs/android/create-a-starter-app/
 * https://developers.arcgis.com/labs/android/display-point-line-and-polygon-graphics/
 *
 */
public class MainActivity extends AppCompatActivity {

    private Button bRefresh;
    private MapView mMapView;
    private GraphicsOverlay mGraphicsOverlay;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create objects for layout
        mMapView = findViewById(R.id.mapView);
        bRefresh = findViewById(R.id.b_refresh);
        pb = findViewById(R.id.progressBar);

        // Setup the map
        setupMap();
        createGraphicsOverlay();

        // Connect button with the refreshMap function
        bRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshMap();
            }
        });

        disablePB();

        // Get the earthquake data when you start the app
        refreshMap();

    }

    @Override
    protected void onPause() {
        if (mMapView != null) {
            mMapView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMapView != null) {
            mMapView.dispose();
        }
        super.onDestroy();
    }

    /**
     * The map is drawn initially zoomed all the way out
     */
    private void setupMap() {
        if (mMapView != null) {
            Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR;
            double latitude = 0;
            double longitude = 0;
            int levelOfDetail = 1;
            ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            mMapView.setMap(map);
        }
    }

    /**
     * Create a Graphics Overlay for the Map.  This will be used to put dots on the map
     * where earthquakes have occurred.
     */
    private void createGraphicsOverlay() {
        mGraphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(mGraphicsOverlay);
    }

    /**
     * Clear all the dots on the map.
     */
    private void clearGraphicsOverlay() {
        mGraphicsOverlay.getGraphics().clear();
    }

    /**
     * Add a new dot on the map.  The magnitude is used to determine the color of the dot.
     * @param latitude
     * @param longitude
     * @param mag
     */
    public void createPointOnMap(float latitude, float longitude, float mag) {
        Point point = new Point(longitude, latitude, SpatialReferences.getWgs84());
        int color = 0;
        if (mag < 3.0) {
            color = Color.BLUE;
        }
        else if (mag < 5.0) {
            color = Color.YELLOW;
        }
        else {
            color = Color.RED;
        }

        SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, color, 10.0f);
        Graphic pointGraphic = new Graphic(point, pointSymbol);
        mGraphicsOverlay.getGraphics().add(pointGraphic);
    }

    /**
     * Read JSON data from the USGS website to get all the earthquakes from today.
     * Create a dot for each earthquake.
     */
    private void refreshMap() {
        clearGraphicsOverlay();

        new RefreshMapTask(this).start();
    }

    public void disablePB() {
        pb.setVisibility(View.INVISIBLE);
    }

    public void enablePB() {
        pb.setVisibility(View.VISIBLE);
    }

}
