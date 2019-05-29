package macbeth;

/* STRETCH REQUIREMENTS */

public class WeatherForecastSummary {
    private String city;
    private float maxTemp;
    private float maxWind;

    public WeatherForecastSummary(String city, WeatherForecast forecast) {
        this.city = city;
        maxTemp = Float.MIN_VALUE;
        maxWind = Float.MIN_VALUE;
        for (WeatherForecastItem item : forecast.getForecastItems()) {
            if (item.getMeasurements().get("temp_max") > maxTemp) {
                maxTemp = item.getMeasurements().get("temp_max");
            }
            if (item.getWind().getSpeed() > maxWind) {
                maxWind = item.getWind().getSpeed();
            }
        }
    }

    public String getId() { return city; }
    public float getMaxTemp() { return maxTemp; }
    public float getMaxWind() { return maxWind; }

    public String toString() {
        return "City: " + city +
                " Max Temp: " + maxTemp +
                " Max Wind: " + maxWind;
    }

}
