package macbeth;

import java.io.*;
import java.util.TreeMap;

public class Config {

    private static Config config = null;
    private TreeMap<String, String> data;

    private Config() {
        data = new TreeMap<String, String>();

        readFile();
    }

    public static synchronized Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public synchronized String getConfigValue(String key) {
        return data.get(key);
    }

    public synchronized void setConfigValue(String key, String value) {
        data.put(key, value);

        writeFile();
    }

    private void readFile() {
        // Read from the file to populate the configuration
        try {
            BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
                    String[] result = line.split("=");
                    data.put(result[0], result[1]); // TODO: Need to add some more error checking
                }
            }
            while (line != null);
            reader.close();
        }
        catch (IOException ioe) {
        }
    }

    private void writeFile() {

        // Write to the file immediately to update the configuration
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("config.txt"));
            for (String dataKey : data.keySet()) {
                writer.write(dataKey+"="+data.get(dataKey)+"\n");
            }
            writer.close();
        }
        catch (IOException ioe) {
        }
    }
}
