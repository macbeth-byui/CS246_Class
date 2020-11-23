package macbeth;

public class Main {

    public static void main(String[] args) {
	    //Config config = new Config();
        Config config = Config.getInstance();

	    System.out.println("Contents of the file:");
	    System.out.println(config.getConfigValue("IP"));
		System.out.println(config.getConfigValue("Port"));
		System.out.println(config.getConfigValue("Timeout"));
		System.out.println(config.getConfigValue("LastLogin"));

		// Make some changes and add some new items
		config.setConfigValue("Timeout","1000");
		config.setConfigValue("LastLogin","Right Now");

		System.out.println("\nContents of the file after changes were made:");
		System.out.println(config.getConfigValue("IP"));
		System.out.println(config.getConfigValue("Port"));
		System.out.println(config.getConfigValue("Timeout"));
		System.out.println(config.getConfigValue("LastLogin"));

    }
}
