package macbeth.prove_06;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class WeatherConditions {
    private int id;
    private String name;
    @SerializedName("main")
    private Map<String, Float> measurements;

    public int getId() { return id; }
    public String getName() { return name; }
    public Map<String, Float> getMeasurements() { return measurements; }
    public String toString() {
        return "id: " + id + " name=" + name + " measurements: " + measurements;
    }
}
