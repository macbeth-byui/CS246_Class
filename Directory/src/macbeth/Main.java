package macbeth;

import com.google.gson.Gson;

import java.util.*;

public class Main {

    public static void main(String []args) {

        //Collection<String> directory = new ArrayList<String>();
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

        directory2.put("Bob",new User("Bob",20, "555-1234"));
        directory2.put("Sue",new User("Sue", 25, "555-7890"));

        System.out.println(directory2.get("Sue"));

        for (String key : directory2.keySet()) {
            System.out.println(key);
        }

        for (User value : directory2.values()) {
            System.out.println(value);
        }

        User u = new User("Tim", 50, "123-4567");
        Gson gson = new Gson();
        String jsonString = gson.toJson(u);
        System.out.println(jsonString);

        User u2 = gson.fromJson(jsonString, User.class);
        System.out.println(u2);
    }
}
