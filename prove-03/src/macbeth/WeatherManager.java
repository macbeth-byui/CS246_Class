package macbeth;

import com.google.gson.Gson;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class WeatherManager {
    private final String key = "3cdb5f940155747817fa162765ae2a6b";
    private final String baseUrl = "https://api.openweathermap.org/data/2.5/";
    private Gson gson;
    private List<WeatherForecastSummary> summaries;

    public WeatherManager() {
        gson = new Gson();
        summaries = new ArrayList<WeatherForecastSummary>();
    }

    /* STRETCH REQUIREMENTS */

    public void loadMultipleForecasts(List<String> cityList) {
        for (String city : cityList) {
            WeatherForecast forecast = loadWeatherForecast(city);
            WeatherForecastSummary summary = new WeatherForecastSummary(city, forecast);
            summaries.add(summary);
        }
    }

    public void sortMaxWind() {
        summaries.sort(new Comparator<WeatherForecastSummary>() {
            @Override
            public int compare(WeatherForecastSummary o1, WeatherForecastSummary o2) {
                return (int)(o2.getMaxWind() - o1.getMaxWind());
            }
        });
    }

    public void sortMaxTemp() {
        summaries.sort((WeatherForecastSummary o1, WeatherForecastSummary o2)->(int)(o2.getMaxTemp()-o1.getMaxTemp()));
    }

    public List<WeatherForecastSummary> getSummaries() { return summaries; }

    /* CORE REQUIREMENTS */

    public WeatherConditions loadCurrentWeather(String city) {
        // Request for weather data
        String url = baseUrl + "weather" +
                "?q=" + city +
                "&units=imperial" +
                "&apiKey=" + key;
        String data = loadJsonData(url);

        // Convert JSON data to WeatherConditions object
        return gson.fromJson(data, WeatherConditions.class);
    }

    public WeatherForecast loadWeatherForecast(String city) {
        // Requset for weather forecast
        String url = baseUrl + "forecast" +
                "?q=" + city +
                "&units=imperial" +
                "&apiKey=" + key;
        String data = loadJsonData(url);

        // Convert JSON data to WeatherForecast object
        return gson.fromJson(data, WeatherForecast.class);
    }

    private String loadJsonData(String url) {
        StringBuilder data = new StringBuilder();
        try {
            URL urlObj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Read all data from the website into a single string
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
                    data.append(line);
                }
            }
            while (line != null);

        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }

        return data.toString();
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        WeatherManager mgr = new WeatherManager();

        /* CORE REQUIREMENTS */

        /*
        System.out.println("Enter city name: ");
        String city = scanner.nextLine();
        WeatherConditions conditions = mgr.loadCurrentWeather(city);
        System.out.println(conditions);
        WeatherForecast forecast = mgr.loadWeatherForecast(city);
        System.out.println(forecast);
        */

        /* STRETCH REQUIREMENTS */

        ArrayList<String> cities = new ArrayList<String>();
        cities.add("Rexburg");
        cities.add("Kalamazoo");
        cities.add("Harlingen");
        cities.add("Rockford");
        cities.add("San Francisco");
        System.out.println("Unsorted Summaries:");
        mgr.loadMultipleForecasts(cities);
        for (WeatherForecastSummary summary : mgr.getSummaries()) {
            System.out.println(summary);
        }

        System.out.println("Sorted by Max Temp:");
        mgr.sortMaxTemp();
        for (WeatherForecastSummary summary : mgr.getSummaries()) {
            System.out.println(summary);
        }

        System.out.println("Sorted by Max Wind:");
        mgr.sortMaxWind();
        for (WeatherForecastSummary summary : mgr.getSummaries()) {
            System.out.println(summary);
        }

    }
}

