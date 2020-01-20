package macbeth;

import com.google.gson.Gson;

import java.util.*;

public class Main {

    public static void main(String [] args) {
        //Collection<String> directory = new ArrayList<>();
        //Collection<String> directory = new LinkedList<>();
        //Collection<String> directory = new HashSet<>();
        Collection<String> directory = new TreeSet<>();

        directory.add("Bob");
        directory.add("Sue");
        directory.add("Tim");
        directory.add("Sue");

        for (String name : directory) {
            System.out.println(name);
        }

        //Map<String, User> directory2 = new HashMap<>();
        Map<String, User> directory2 = new TreeMap<>();
        directory2.put("Bob",new User("Bob", 32, "555-1234"));
        directory2.put("Sue",new User("Sue", 19, "555-2345"));

        for (String key : directory2.keySet()) {
            System.out.println(key);
        }
        for (User user : directory2.values()) {
            System.out.println(user);
        }

        User user = new User("Tim", 50, "555-4534");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        System.out.println(json);
        User newUser = gson.fromJson(json, User.class);
        System.out.println(newUser);

    }
}
