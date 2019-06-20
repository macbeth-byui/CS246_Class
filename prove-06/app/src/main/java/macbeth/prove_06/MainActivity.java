package macbeth.prove_06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_city;
    private ListView lv_forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_city = findViewById(R.id.et_city);
        lv_forecast = findViewById(R.id.lv_forecast);
    }



    public void getTemp(View view) {
        TempTask task = new TempTask(et_city.getText().toString());
        task.start();
    }

    public void getForecast(View view) {
        ForecastTask task = new ForecastTask(et_city.getText().toString());
        task.start();
    }

    public void displayTemp(float temp) {
        Toast.makeText(this, "Temp is "+temp, Toast.LENGTH_SHORT).show();
    }

    public void displayForecast(WeatherForecast forecast) {
        ArrayAdapter adapter = new ArrayAdapter<WeatherForecastItem>(this,
                android.R.layout.simple_list_item_1, forecast.getForecastItems());
        ((ArrayAdapter) lv_forecast.getAdapter()).notifyDataSetChanged();

    }

    private class TempTask extends Thread {

        private String city;

        public TempTask(String city) {
            this.city = city;
        }

        @Override
        public void run() {
            WeatherManager mgr = new WeatherManager();
            final WeatherConditions conditions = mgr.loadCurrentWeather(city);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displayTemp(conditions.getMeasurements().get("temp"));
                }
            });
        }
    }

    private class ForecastTask extends Thread {

        private String city;

        public ForecastTask(String city) {
            this.city = city;
        }

        @Override
        public void run() {
            WeatherManager mgr = new WeatherManager();
            final WeatherForecast forecast = mgr.loadWeatherForecast(city);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displayForecast(forecast);
                }
            });
        }
    }

}
