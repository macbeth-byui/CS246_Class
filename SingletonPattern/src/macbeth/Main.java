package macbeth;

public class Main {

    public static void main(String[] args) {
	    Config config = Config.getInstance();

	    // This will create the file initially ... you can comment this out once the file
        // has been created.
        config.setConfigValue("IP","39.238.11.23");
	    config.setConfigValue("Port","3233");
	    config.setConfigValue("Timeout","300");

	    // Test that we can read from the config
	    System.out.println(config.getConfigValue("IP"));
		System.out.println(config.getConfigValue("Port"));
		System.out.println(config.getConfigValue("Timeout"));
		System.out.println(config.getConfigValue("LastLogin"));

		// Make some changes and add some new items
		config.setConfigValue("Timeout","1000");
		config.setConfigValue("LastLogin","Right Now");

    }
}
