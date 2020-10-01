package macbeth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Helper class to send HTTP requests (the url) and receive responses.  Example use of the class:
 *
 * HTTPHelper http = new HTTPHelper();
 * String result = http.readHTTP("http://api.open-notify.org/iss-now.json");
 *
 * Note that the URL provided can also have parameters including keys.
 */
public class HTTPHelper {

    /**
     * Will send the HTTP GET Request per the URL provided and listen for the the response.
     *
     * @param url - The HTTP Request URL (including any parameters)
     * @return - The HTTP Response string.  Note that this will be null if there was an error
     */
    public String readHTTP(String url) {
        try {
            // Convert the String url to a URL object which will allow a connection to be established
            URL urlObj = new URL(url);

            // Open a Connection to the remote site.  This will send the HTTP Get request
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

            // Open an input stream to read the response.  I prefer the BufferedReader since it provides
            // the readLine function.
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // When concatenating strings read from the remote server, we get some performance improvement by
            // using the StringBuilder class.
            StringBuilder data = new StringBuilder();
            String line;

            // Keep reading from the remote server until we get a null which indicates the connection has
            // been closed.
            do {
                line = reader.readLine();  // Read the next line
                if (line != null) {
                    data.append(line); // Append will concatenate the new string just read into the StringBuilder
                }
            }
            while (line != null);

            // Convert the StringBuilder into a regular String for use by the calling function
            return data.toString();
        }
        catch (IOException ioe) {
            // Print out an error if something went wrong and return a null response string
            System.out.println("Error reading HTTP Response: "+ioe);
            return null;
        }
    }

    /**
     * Test the readHTTP function.
     * @param args
     */
    public static void main(String[] args) {
        HTTPHelper http = new HTTPHelper();
        String result = http.readHTTP("http://api.open-notify.org/iss-now.json");
        System.out.println(result);
        result = http.readHTTP("https://www.byui.edu");
        System.out.println(result);
    }

}
