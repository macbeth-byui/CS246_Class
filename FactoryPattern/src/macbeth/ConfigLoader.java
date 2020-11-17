package macbeth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {

    public static void main(String[] args) {
        List<AvionicPort> ports = new ArrayList<AvionicPort>();

        try {
            String line = null;
            BufferedReader reader = new BufferedReader(new FileReader("Port_Config.txt"));
            do {
                line = reader.readLine();
                if (line != null) {
                    AvionicPort port = PortFactory.createPort(line);
                    if (port != null) {
                        ports.add(port);
                    }
                }
            }
            while (line != null);
            reader.close();
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }

        for (AvionicPort port : ports) {
            System.out.println(port);
        }
    }
}
