**API's**

API stands for Application Programming Interface.  An API can take many forms (you can use them from others or you can make your own):

* Create a Class that has public functions that you can give to someone else to use.  The public functions are the API.  You can create a collection of classes and put the code into a JAR file.  You can give that JAR file to someone else.  You can also put the JAR file into a public repository like Maven (https://mvnrepository.com/).  API documents usually provide details about each function.  In Java, this is usually formatted by the JavaDoc tool.
* Provide a web server that receives requests and provides responses.  The requests can be sent via HTTP (either GET or POST methods).  In the request, the user provides information stating what data they are looking for.  The format of the request is strict and described in API documents.  The response is also well formatted.  The most common formats to receive back are JSON or XML.  The user then writes code to parse these responses for further use.

There are many (many, many) API's available online for you to use (just google for it and you will probably find it).  Many social media companies have public API's.  Here is a website to browse:  https://www.programmableweb.com/apis/directory

When you access an API via a web server, the data provider will frequently require you to have an API key.  The key is required for 2 reasons:

* Since data can be used frequently to make money, the provider of the data will want to charge you to get the data.  The provider will track how much data is requested by looking at the key.  If you write software and share it with others, then the software they are using will all be utilizing your key.  You will then be charged for the amount of data that is requested by your users.  Some data (even from sites that charge) is provided for free (usually only small amounts per month)
* A danger that a data provider must guard against is someone requesting large amounts of data at very high frequencies.  This deliberate attempt to slow down the server is called a Denial of Service Attack.  To prevent this, the data provider will require that all attempts to obtain data be made with a valid key.  The key is associated with a user account that is established with the data provider.

The key needs to be kept secret.  One approach to put the key in a separate code file by itself and ensure that this code file is not put into GitHub for public viewing.  This means if someone uses your code, they will need to write some code to provide this key.  

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

In this code, we first create an URL object which contains the website (along with any parameters) that we want to connect to.  The HttpsURLConnection will create an HTTPS connection and send the request.  To get the response we need to setup an input stream object.  THe HttpsURLConnection provides the stream via the getInputStream function.  I like to wrap this input stream around an InputStreamReader and then a BufferedReader.  This will provide me with a readLine function that will read a full line into a string from the web server.  The do while loop will read one line at a time from the web server until it receives a null (nothing more to read).  The code will combine all the strings read into one big stream.  The StringBuilder is an alternative way to add those strings together.  It is more efficient at adding strings together.  Since we are reading from a stream, we also have to watch out for an IOException.

The above code is used for doing GET requests.  Usually a GET request has the parameters as part of the URL (using the ? and & delimiters).  If you need to do a PUT request, the parameters are sent in a message to the web server.  The following needs to be done after creating the HttpsURLConnection:

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

JSON (Javascript Object Notation) is a common format of data that an API provides.  JSON text is a textual representation of an object in your code.  To better understand how JSON works, create a class with lots of different type of data within it (i.e. int, float, String, List<>, Map<>, and other objects of classes you create).  Then (using the GSON library) call the toJson function and look a the string content.  The native data types (including Strings) are stored as key/value pairs in what looks like a Map (python programmers ... think dictionary) separated by curly braces.  If you include a list in your class, the list will show up in the JSON text with square brackets.  If you include a map or another class, you will get another embedded Map (curly braces).  Doing an example like this on your own will help you more than anything else to understand how you can convert JSON text back to an object.

If you have a map in a JSON string, you have two options:
* Store as a Map in your class.  In this case, all data in the map from the JSON string will be included.  You need to make sure that all of the values in this map have the same type.
* Store as an object of another class.  In this case, the class that you create only needs to include the parameters that you want.  You can always just default to this instead of using a map.

When you look at the JSON format found on an API website, you need to first identify which parameters you want to save in your object.  Once you select the desired parameters, then you need to create a data model using classes and HAS-A relationships that represents the JSON structure.  You only need to include the parameters that you will use.  <u>Creating a UML is the most important thing you can do to use JSON properly</u>.  When you write your UML (and then your eventually the actual classes), the member data name should match the names in the JSON format.  If you want to change the name, you can use the @SerializedName:

    @SerializedName("id")
    private String name;

In this case, the name in the JSON data is "id" but the name in the class will be "name" (which perhaps is more meaningful.  Its possible to have multiple JSON names that can map to the same member data in your class:

    @SerializedName(value="id", alternate={"title"})
    private String name;

In general, you will create a full set of classes that represent the JSON model to get at the data you really want.  There are ways to parse through the JSON string to get to the data that you really want before converting it to an object.  If you want to learn more about this, consider this site:  http://tutorials.jenkov.com/java-json/gson-jsonreader.html

**ArrayLists**

Recall that an ArrayList is just like a vector in C++ or a list in Python.  The basic operations of any List are:

- add(e) - Add "e" to the end of the list
- add(index, e) - Insert "e" at the index location (everything else moves over)
- addAll(c) - Add "c" (which is another list) to the end of the list
- get(index) - Obtain an item from the list
- remove(index) - Remove an item from the list

There are more to explore here: https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/List.html

**Maps**

A Map is like a List in that they are both containers that hold data.  To get something out of a List, we use indexing (use square brackets).  To get something out of a Map, we use a key to lookup the value.  This is why Map's are usually described as key/value pairs.  

Here is a summary of the different type of Maps in Java:

- HashMap - Contains key/value pairs.  The keys are not sorted.  Uses hashing to provide O(1) lookup time
- TreeMap - Contains key/value pairs.  The keys are sorted.  Uses a binary search tree to provide O(log n) lookup time
- LinkedHashMap - Contains key/value pairs put into a HashMap to provide O(1) lookup.  Also creates a linked list to connect the nodes so that the key's can remained ordered.
- ConcurrentHashMap - Same as a HashMap but is safe when there are concurrent processes trying to modify the HashMap at the same time.

**JavaDoc**

If you write comments using the JavaDoc format, you can generate JavaDoc html files from it.  In IntelliJ, goto menu option Tools and select Generate JavaDoc.  We will explore this later in the semester.

**Reusable Code**

Java can be rather bulky (even more than C++ sometimes) and sometimes we think that we are not being very efficient with our code.  Two things to consider:
* Start with a design.  Plan your classes and code to that plan.  This will reduce the likelihood of unintended or extra code.  This will also increase reuse in your code.
* The Java Library is an API that contains many reusable components.  Use your JavaDoc frequently to find the functions that will give you the data you need.

**Design Patterns**

In the first several weeks of this course we learn about both the Java language and other libraries such as JSON and Android.  Your code during these weeks may be a little messy as you get used to these new topics.  As you begin work on your Android app, we will begin to introduce different design patterns that are found in object oriented languages such as Java.  Using our new background in Java, we will look at the following design patterns: Strategy, MVC, Observer, Factory, Singleton, and Adapter.

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
forecasts.sort(new Comparator() {
    @Override
    public int compare(WeatherForecastItem o1, WeatherForecastItem o2) {
        return Float.compare(o1.getMeasurements().get("max_temp"), 
                             o2.getMeasurements().get("max_temp"));
    }
});
```

There is short hand we can use with thing like this.  The lambda notation allows us to define the compare function for the sort to use.  The left hand side of the "->" shows the inputs to the compare function and the right hand side of the "->" shows what is returned.  This has the added benefit of not needing the function name.

```java
List<WeatherForecastItem> forecasts;

forecasts.sort((WeatherForecastItem o1, WeatherForecast Item o2)->Float.compare(
    o1.getMeasurements().get("max_temp"),o2.getMeasurements().get("max_temp")));
```

