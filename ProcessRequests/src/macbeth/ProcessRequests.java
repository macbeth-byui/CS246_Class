package macbeth;

import java.util.Scanner;

public class ProcessRequests {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RequestServer server = new RequestServer();
        Thread t = new Thread(server);
        t.start();
        //server.start();

        String choice;
        do {
            System.out.println();
            System.out.println("Request Menu");
            System.out.println("1) Display All");
            System.out.println("2) Process Next");
            System.out.println("3) Exit");
            System.out.print("> ");
            choice = scanner.nextLine();
            if (choice.equals("1")) {
                server.displayRequests();
            }
            else if (choice.equals("2")) {
                System.out.println("Processing: "+server.getNextRequest());
            }
        }
        while (!choice.equals("3"));
        server.exit();
    }
}
