package macbeth;

import com.google.gson.Gson;

import java.util.*;

public class Directory {

    public static void main(String[] args) {
        // Compare different types of collections
        Collection<String> directory = new ArrayList<String>();  // Dupliates okay, same order
        directory = new LinkedList<String>();  // Duplicate okay, same order
        directory = new HashSet<String>();  // No duplicates, different order
        directory = new TreeSet<String>();  // No duplicates, sorted order

        directory.add("Bob");
        directory.add("Jacob");
        directory.add("Susan");
        directory.add("Henry");
        directory.add("Susan");

        for (String name : directory) {
            System.out.println(name);
        }

        // Repeat but using User objects this time
        Collection<User> directory2 = new ArrayList<User>();
        directory2 = new LinkedList<User>();
        directory2 = new HashSet<User>();
        //directory2 = new TreeSet<User>(); // Doesn't work because there is no way to sort a "User" object (yet)
        directory2.add(new User("Bob", "bob@gmail.com", "123-4567", 20));
        directory2.add(new User("Jacob", "bob@gmail.com", "123-4567", 30));
        directory2.add(new User("Susan", "bob@gmail.com", "123-4567", 40));
        directory2.add(new User("Henry", "bob@gmail.com", "123-4567", 50));
        directory2.add(new User("Susan", "bob@gmail.com", "123-4567", 40));

        for (User user : directory2) {
            System.out.println(user);
        }

        // Try out the maps using the name as the key
        Map<String, User> directory3 = new HashMap<String,User>();
        directory3 = new TreeMap<String,User>();  // This works because its only sorting the key which is a String
        directory3.put("Bob",new User("Bob", "bob@gmail.com", "123-4567", 20));
        directory3.put("Jacob", new User("Jacob", "bob@gmail.com", "123-4567", 30));
        directory3.put("Susan", new User("Susan", "bob@gmail.com", "123-4567", 40));
        directory3.put("Henry", new User("Henry", "bob@gmail.com", "123-4567", 50));
        directory3.put("Susan", new User("Susan", "susan@gmail.com", "123-4567", 40));

        System.out.println(directory3.get("Bob"));

        for (String key : directory3.keySet()) {
            System.out.println(key);
        }

        for (User user : directory3.values()) {
            System.out.println(user);
        }

        // Convert a single User object to JSON using GSON
        User test = new User("Emma", "emma@gmail.com", "123-4567", 25);
        Gson gson = new Gson();
        String text = gson.toJson(test);
        System.out.println(text);

        // Convert the JSON string back to a User object
        User received = gson.fromJson(text, User.class);
        System.out.println(received);

        // Convert a full collection object to JSON
        text = gson.toJson(directory2);
        System.out.println(text);

        // Convert it back again.  Will create an array.
        User[] users = gson.fromJson(text, User[].class);
        for (User user : users) {
            System.out.println(user);
        }

        // Convert the array into an ArrayList
        List<User> userList = new ArrayList<User>(Arrays.asList(users));
        for (User user : userList) {
            System.out.println(user);
        }

    }
}
