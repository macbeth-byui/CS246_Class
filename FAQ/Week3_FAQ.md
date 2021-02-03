## CS 246 Week 3 Support Material

**Examples and Videos**

The following examples and videos support the material in Week 3.  Subsequent sections below provide additional information and examples for common questions that students encounter during this week.

* Video:  https://video.byui.edu/media/CS246+Week+3+Collections/0_7fv9gzj5
* Java Collection Framework UML: https://macbeth-byui.github.io/CS246/diagrams/Java_Collection_Framework.html
* Data Structure Example Code: https://github.com/macbeth-byui/CS246_Class/tree/master/Directory/src/macbeth
* Another Example of HTTP Get Requests: https://github.com/macbeth-byui/CS246_Class/tree/master/HTTPHelper/src/macbeth
* Weather Conditions UML: https://macbeth-byui.github.io/CS246/diagrams/WeatherConditions.html
* Weather Forecast UML: https://macbeth-byui.github.io/CS246/diagrams/WeatherForecast.html
* Another Full API Example (Earthquakes): https://github.com/macbeth-byui/CS246_Class/tree/master/EarthquakeAnalyzer/src/macbeth
* Another Full API Example (Earthquakes UML): https://macbeth-byui.github.io/CS246/diagrams/Earthquake.html

**API's**

API stands for Application Programming Interface.  An API can take many forms (you can use them from others or you can make your own):

* Create a Class that has public functions that you can give to someone else to use.  The public functions are the API.  You can create a collection of classes and put the code into a JAR file.  You can give that JAR file to someone else.  You can also put the JAR file into a public repository like Maven (https://mvnrepository.com/).  API documents usually provide details about each function.  In Java, this is usually formatted by the JavaDoc tool.
* Provide a web server that receives requests and provides responses.  The requests can be sent via HTTP (either GET or POST methods).  In the request, the user provides information stating what data they are looking for.  The format of the request is strict and described in API documents.  The response is also well formatted.  The most common formats to receive back are JSON or XML.  The user then writes code to parse these responses for further use.

The API in this case is creating the correct URL to request information.  The response is JSON data that you convert to an object using GSON.  You convert it so you can do something with it (like display a temperature).

Working with API's can be difficult since a small mistake in your URL or conversion process can cause big errors.  Simple debug techniques such as using System.out.println to display API results and resulting objects is helpful.  You may also find it easier to reading only one thing at a time from the JSON result.  Can you just read the temperature and nothing else?  Once you get that one to work, then proceed to adding more.

There are many (many, many) API's available online for you to use (just google things like "free json data" or "earthquake json" and you will probably find it).  Many social media companies have public API's.  Here is a website to browse:  https://www.programmableweb.com/apis/directory.  Using API's on the web can be a source of ideas for writing software for personal learning and enjoyment.  Find a source of data and then write code to read it and process it.

When you use data provided online, you should be aware of the cost and source of the information.  Not all data is free and not all data is accurate.  You may also find that some data could have errors in it.

Many of the Android App's that you will develop in your team will require some sort of external data like the one we used during this assignment.

When you access an API via a web server, the data provider will frequently require you to have an API key.  The key is required for 2 reasons:

* Since data can be used frequently to make money, the provider of the data will want to charge you to get the data.  The provider will track how much data is requested by looking at the key.  If you write software and share it with others, then the software they are using will all be utilizing your key.  You will then be charged for the amount of data that is requested by your users.  Some data (even from sites that charge) is provided for free (usually only small amounts per month)
* A danger that a data provider must guard against is someone requesting large amounts of data at very high frequencies.  This deliberate attempt to slow down the server is called a Denial of Service Attack.  To prevent this, the data provider will require that all attempts to obtain data be made with a valid key.  The key is associated with a user account that is established with the data provider.

The key needs to be kept secret.  One approach to put the key in a separate code file by itself and ensure that this code file is not put into GitHub for public viewing.  This means if someone uses your code, they will need to write some code to provide this key. To avoid putting a file into GitHub, you will need to list that file in the `.gitignore` file before committing that file to the git repository. 

**Making HTTP Requests**

To get data from a server, you will need to create an HTTP request.  There are multiple ways of doing this.  My personal favorite solution is as follows:

    private String readHTTP(String url) {
        try {
            URL urlObj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder data = new StringBuilder();
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
                    data.append(line);
                }
            }
            while (line != null);
            return data.toString();
        }
        catch (IOException ioe) {
            System.out.println("Error Reading HTTP Data: " + ioe);
            return null;
        }
    }

In this code, we first create an URL object which contains the website (along with any parameters) that we want to connect to.  When openConnection is called on the URL, the request will be sent and we received an HttpsURLConnection.   The type cast is done because in reality, openConnection returns a URLConnection which is abstract.  However, since we are connecting to an HTTPS site, HttpsURLConnection is the appropriate derived class.  The HttpsURLConnection contains the getInputStream function.

The HttpsURLConnection provides the stream (via the getInputStream function) to read the result from the website.  I like to wrap this input stream around an InputStreamReader and then a BufferedReader.  (Here is a link summarizing some of the different reader and writers in Java:  https://hajsoftutorial.com/java-reader-writer-classes/) This will provide me with a readLine function that will read a full line into a string from the web server.  The do while loop will read one line at a time from the web server until it receives a null (nothing more to read).  The code will combine all the strings read into one big stream.  The StringBuilder is an alternative way to add those strings together.  It is more efficient at adding strings together.  Since we are reading from a stream, we also have to watch out for an IOException.

The above code is used for doing GET requests.  Usually a GET request has the parameters as part of the URL (using the ? and & delimiters).  If you need to do a POST request, the parameters are sent in a message to the web server.  The following needs to be done after creating the HttpsURLConnection:

    connection.setRequestMethod("POST");
    connection.setDoOutput(true);

Just like we used the BufferedReader to read data, we can create a DataOutputStream to write data:

    DataOutputStream outputStream = new DataOutputStream (connection.getOutputStream ());
    outputStream .writeBytes(data);
    outputStream.flush();
    outputStream.close();

Sometimes we have to set headers in our GET or POST request (API's will describe if you need to do this ... sometimes the key is put in the header).  If you need to a header, then do the following after creating the HttpsUrlConnection:

    connection.setRequestProperty("Content-Type", "application/json");

The first parameter is the header key and the second parameter is the header value.

When you create the URL or when you send data in a POST request, you will notice alot of examples that talk about encoding with UTF-8.  The UTF-8 encoding is used to represent characters outside of the standard ASCII table.  To ensure that the web server understands what we are trying to send them, it may be necessary to encode our parameters in UTF-8:

    connection.setRequestProperty("Accept-Charset", "UTF-8");
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

To encode a parameter, we can use the URLEncoder:

    URLEncoder.encode(parameter, "UTF-8");

**JSON**

JSON (Javascript Object Notation) is a common format of data that an API provides.  JSON text is a textual representation of an object in your code.  The process of going from object to String is called serialization.  Unfortunately, Java does not have a convenient JSON library provided with the JDK.  Libraries like GSON help to fill this gap.

To better understand how JSON works, create a class with lots of different type of data within it (i.e. int, float, `String`, `List<>`, `Map<>`, and other objects of classes you create).  Then (using the GSON library) call the `toJson` function and look a the string content.  The native data types (including Strings) are stored as key/value pairs in what looks like a Map (python programmers ... think dictionary) separated by <u>curly braces</u>.  If you include a list in your class, the list will show up in the JSON text with <u>square brackets</u>.  If you include a map or another class, you will get another embedded Map (curly braces).  Doing an example like this on your own will help you more than anything else to understand how you can convert JSON text back to an object.

If you have a map in a JSON string, you have two options:
* Store as a Map in your class.  In this case, all data in the map from the JSON string will be included.  You need to make sure that all of the values in this map have the same type.
* Store as an object of another class.  In this case, the class that you create only needs to include the parameters that you want.  You can always just default to this instead of using a map.

Sometimes it can be frustrating to have to make classes when you just need one or two pieces of data.  However, if the JSON structure is complicated with many levels of embedded information, there is no other way for tools like GSON to find what you are looking for.  If the data is at the same level (wind speed, wind direction), GSON is okay mapping what you want.  However, you can't put wind speed and temperature in the same class because they are in different sets of curly braces in the JSON structure.

When you look at the JSON format found on an API website, you need to first identify which parameters you want to save in your object.  Once you select the desired parameters, then you need to create a data model using classes and HAS-A relationships that represents the JSON structure.  You only need to include the parameters that you will use.  <u>Creating a UML is the most important thing you can do to use JSON properly</u>.  When you write your UML (and then your eventually the actual classes), the member data name should match the names in the JSON format.  If you want to change the name, you can use the @SerializedName:

    @SerializedName("id")
    private String name;

In this case, the name in the JSON data is "id" but the name in the class will be "name" (which perhaps is more meaningful.  Its possible to have multiple JSON names that can map to the same member data in your class:

    @SerializedName(value="id", alternate={"title"})
    private String name;

In the assignment, the weather forecast contained many examples of complex embedded JSON structure.  When you have a mix of lists and classes, a UML diagram is good to draw based on what you see in that JSON structure.  For example, one possible UML for the WeatherForecast would be:  https://macbeth-byui.github.io/CS246/diagrams/WeatherForecast.html

In general, you will create a full set of classes that represent the JSON model to get at the data you really want.  There are ways to parse through the JSON string to get to the data that you really want before converting it to an object.  If you want to learn more about this, consider this site:  http://tutorials.jenkov.com/java-json/gson-jsonreader.html

To help debug, you should print out the JSON data you read to ensure it was received properly.  Additionally, you should add toString functions to all your data classes so that you can print out the object that was created when you run `fromJson`.

There are other options besides JSON.  The most common one is XML.  Both of these can be used to communicate data.  JSON has become a widely accepted format for data exchange.  XML is more flexible because you can define your own tags to separate data.  XML can be used to show connections and relationships as opposed to just data content.  However, conversion into objects for XML is more complicated.

There are also other options besides GSON to process JSON.  Here is a site that summarizes several:

https://javarevisited.blogspot.com/2014/12/how-to-read-write-json-string-to-file.html

The other options all do the same thing.  However, the basic to and from features from GSON to go from string to object are pretty good so long as you understand the need to write UML and classes for your data properly first.

**ArrayLists and LinkedLists**

Recall that an ArrayList is just like a vector in C++ or a list in Python.  The basic operations of any List are:

- add(e) - Add "e" to the end of the list
- add(index, e) - Insert "e" at the index location (everything else moves over)
- addAll(c) - Add "c" (which is another list) to the end of the list
- get(index) - Obtain an item from the list
- remove(index) - Remove an item from the list

The LinkedList has the behavior like an ArrayList but it is not organized like an array so indexing is not efficient.  However, the LinkedList is really good if you want to implement a doubled ended queue.  The LinkedList will give you O(1) performance on either end of the list because it maintains a reference to the front and back at all times.

There are more to explore here: https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/List.html

**Maps**

A Map is like a List in that they are both containers that hold data.  To get something out of a List, we use indexing (use square brackets).  To get something out of a Map, we use a key to lookup the value.  This is why Map's are usually described as key/value pairs.  

Here is a summary of the different type of Maps in Java:

- HashMap - Contains key/value pairs.  The keys are not sorted.  Uses hashing to provide O(1) lookup time
- TreeMap - Contains key/value pairs.  The keys are sorted.  Uses a binary search tree to provide O(log n) lookup time
- LinkedHashMap - Contains key/value pairs put into a HashMap to provide O(1) lookup.  Also creates a linked list to connect the nodes so that the key's can remained ordered.
- ConcurrentHashMap - Same as a HashMap but is safe when there are concurrent processes trying to modify the HashMap at the same time.

When using Maps with JSON data, frequently you will have the following:

```java
public class Data {
	private Map<String, Float> stats; // Expect this will contain mean, median, mode
}

public class Test {
	public static void main(String []args) {
		String jsonData = getJsonData(); // Assume this got data from somewhere
		Gson gson = new Gson();
		Data d = gson.fromJson(jsonData, Data.class);
		float value = d.get("mean");
	}
}
```

Notice in the example code above that the Map contained key/value pairs for float data.  To get the data out, you would use the "get" function on the map after you convert it using GSON.

If we want to loop through a Map, we need to use the the `keySet` function to get a set of keys that we can loop through and get values:

```java
Map<String,Integer> tempByCity;

for (String city : tempByCity.keySet()) {
    int temp = tempByity.get(city);
    System.out.println("City: "+city+" Temp:"+temp);
}
```

**Data Structures and Interfaces**

In Java, all the Data Structure classes inherit from some interface.  For example, the ArrayList and LinkedList inherit from the List interface.  The HashSet and TreeSet inherit from the Set interface.  Both the List and Set interfaces inherit from the Collection interface.  The HashMap and TreeMap inherit from the Map interface.

The interfaces provide the minimum set of functionality that must be implemented in all the derived class.  If you were to invent a new class called the CircularArrayList, then you would be well served to implement interface.  If you do this, then programmers can easily switch between different data structures by saving their variables using the interface data type.  In the code example below, note that we can set the myList variable to objects of any class that implements the List interface (including the one we invented):

```java
List<Integer> myList;

myList = new ArrayList<Integer>();
myList = new LinkedList<Integer>();
myList = new CircularArrayList<Integer>();
```



**JavaDoc**

If you write comments using the JavaDoc format, you can generate JavaDoc html files from it.  In IntelliJ, goto menu option Tools and select Generate JavaDoc. 

When you write JavaDoc, there are alot of decorator symbols you can use to provide additional tagged information like @link (provide a hyperlink in the javadoc html report), @param (identifies a function parameter and will hyperlink it), @return (identifies a return type), @see (identifies a reference to other functions).  Here is a link to read more about these:  https://www.oracle.com/technetwork/java/javase/documentation/index-137868.html

**Reusable Code**

Java can be rather bulky (even more than C++ sometimes) and sometimes we think that we are not being very efficient with our code.  Two things to consider:
* Start with a design.  Plan your classes and code to that plan.  This will reduce the likelihood of unintended or extra code.  This will also increase reuse in your code.
* The Java Library is an API that contains many reusable components.  Use your JavaDoc frequently to find the functions that will give you the data you need.

**Design Patterns**

In the first several weeks of this course we learn about both the Java language and other libraries such as JSON and Android.  Your code during these weeks may be a little messy as you get used to these new topics.  As you begin work on your Android app, we will begin to introduce different design patterns that are found in object oriented languages such as Java.  Using our new background in Java, we will look at the following design patterns: Strategy, MVC, Observer, Factory, Singleton, and Adapter.

You may have noticed that there are some basic architectures that are used in previous courses and in these early assignments.  Good reasons to make a separate classes:

1. You need something to be in charge (maybe this has the main function in it).  Sometimes we call this a controller, manager, or presenter.
2. You have some code which does a unique task that needs to be used in multiple places in your code or in other projects.  Classes to handle HTTP communications, files, encryption, etc.
3. You have some functions which are getting long and complicated.

**Sorting & Comparators**

Java already has built-in capability to sort numbers and strings.  However, if you have a list of objects (say WeatherForecastItem objects), Java doesn't know my default how to do this.  To learn how, let's start with basic sorting.

You can sort 2 ways:

```java
List<String> names;

// Create a new list that is sorted
List<String> sortedNames = Collections.sort(names);

// Sort the list "in-place"
names.sort()
```

If Java doesn't know how to sort our list, we need to provide a Comparator object.  A Comparator is an interface that has a function called "compare" that takes two objects.  The function should return an integer.  If the first object (the left side) is less than the second, then a negative is returned.  If the first object is greater than the second, then a positive is returned.  If they are equal, 0 should be returned.  Note that we have to define the compare function when we do "new Comparator" because is just an interface.

```java
List<WeatherForecastItem> forecasts;

// Sort the forecast summaries based on the forecasted max temperature
// The compare that I write for the Compartor is re-using the Float.compare function
// with the max_temp value from each WeatherForecastItem
Collections.sort(forecasts, new Comparator<WeatherForecastItem>() {
    @Override
    public int compare(WeatherForecastItem o1, WeatherForecastItem o2) {
        return Float.compare(o1.getMeasurements().get("max_temp"), 
                             o2.getMeasurements().get("max_temp"));
    }
});
```

**Lambdas**

The lambda notation allows us to define a function with no name, with parameters (that can have optional types), and code that uses those parameters to provide a value to return.  For example, the following lambda function would add two numbers together:

```java
(x, y) -> x+y
```

The lambda is used to provide a quick implementation for a needed interface.  Looking at sorting, if you needed to create an object of the Compartor interface, then you would need to provide an implementation for a compare function that took 2 parameters.   We could replace the definition of the compare function with a lambda function that did the same thing.  Below you will see the 2 parameters in parentheses followed by an ->.  After the arrow, the code to return is provided. 

```java
List<WeatherForecastItem> forecasts;

Collections.sort((WeatherForecastItem o1, WeatherForecast Item o2)->Float.compare(
    o1.getMeasurements().get("max_temp"),o2.getMeasurements().get("max_temp")));
```

A lambda should be considered when implementing an interface for a specific need (e.g. provide the compartor for the sort function we want to run).  The lambda will allow you access to member data in the class that the lambda is defined within.

**Exception Handling**

When we look at exception handling in Java, you might have noticed that the Java libraries make use of exception much more than in C++.  Python is actually similar to Java in this respect in that exception handling is the primary means of communicating failure.

When you work with exceptions sent by other people, consider this generic structure:

```java
try {
    // Setup Code
    
    // The Code that might cause the exception
    
    // Process the result (assuming it worked)
}
catch (Exception e) {
    // Cleanup the problem
}
```

When you look at the structure above, notice that there is alot of code in the try block.  This is okay.  This represents the success case for the code.  If anything fails, I don't want to finish the code in the try block.

**Files**

To read and write to files, there are multiple approaches you can take in Java.  This has been my favorite because it provides the capability to read full strings from the file:

```java
BufferedReader reader = new BufferedReader(new FileReader("MyFile.txt"));
String result = reader.readLine();  // This will be null if there is nothing to read
reader.close(); // Important step

BufferedWriter writer = new BufferedWriter(new FileWriter("MyFile.txt"));
writer.write("Hello");
writer.close();
```

The BufferedReader and BufferedWriter will read and write multiple characters for us so that we don't have to read and write one at a time.  You can read more in the JavaDoc:  https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/io/BufferedReader.html

**Java and Time**

If you read in `dt` instead of `dt_txt` from the API, you needed someway to convert the time to a more human readable format.  The value that you read from the server is the number of seconds since "epoch" (which was Jan 1, 1970).  The Date class in Java can take the number of milliseconds since Epoch.  If you display a Date object in Java, it is displayed properly (including current timezone).  So, we need to convert seconds to milliseconds:

```java
Date date = new Date(dt * 1000);
System.out.println(date);
```



**StackOverflow (and other sites)**

Having Google at our fingertips to search for information is often one of our first approaches to solving a problem.  However, it is very important to realize that these sites are answering a problem from someone else.  It may be similar to your problem, but more than likely the answer will need to be modified.  Try an approach like this:
1. When you find some code that may be want you are looking for, try running it in a sample program.
2. Add comments to identify what the code does (line by line).
3. Identify what parts of the code may be useful to you (it will usually always need to be modified).
4. Summarize your findings in a notebook or other document on your computer.  Treat this like a science experiment.  By writing it down, you will likely learn more about the technique then if you just copied in the code and hoped for the best.