package macbeth;

    import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecast {
    @SerializedName("list")
    private List<WeatherForecastItem> forecastItems;

    public List<WeatherForecastItem> getForecastItems() { return forecastItems; }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (WeatherForecastItem item : forecastItems) {
            result.append(item.toString() + "\n");
        }
        return result.toString();
    }
}
