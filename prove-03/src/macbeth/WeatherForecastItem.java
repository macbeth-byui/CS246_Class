package macbeth;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherForecastItem {
    @SerializedName("dt_txt")
    private String time;
    @SerializedName("main")
    private Map<String,Float> measurements;
    @SerializedName("weather")
    private List<WeatherDescription> weatherList;
    private WeatherWind wind;

    public String getTime() { return time; }
    public Map<String, Float> getMeasurements() { return measurements; }
    public List<WeatherDescription> getWeather() { return weatherList; }
    public WeatherWind getWind() { return wind; }

    public String toString() {
        return "Time: " + time +
                " Temp: " + measurements.get("temp_max") + " / " + measurements.get("temp_min") +
                " Weather: " + weatherList.get(0).getDescription() +
                " Wind: " + wind.getSpeed();
    }

}
