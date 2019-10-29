**Android and Java**

The Android framework provides classes and tools to allow you to write Java code for an Android app.  When you write code to display to or respond from the user interface, this will be in the Activity classes.  However, just like other software you have written, you will need code that performs tasks, makes decisions, and read/write data.  This will be done using regular Java classes.  You Activity object will likely make instances of these Java classes that you write.  All of the data structures, GSON/JSON, and threading that you have done before will still work the same in Android.

The following links are good for understanding Android:

- Android Developer Guide:  https://developer.android.com/guide 
- Head First Android: https://learning.oreilly.com/library/view/head-first-android/9781491974049/
- When you need to google something, I recommend searching: "android java ???" replacing ??? with the topic you are trying to understand.  Anything starting with " https://developer.android.com/reference" will take you to the related JavaDoc

**Activities and Intents**

An Intent object is created whenever you want to start (transition) to another Activity.  The first Activity in your app is started by an Intent as well.  You can see this in your manfiest.xml.  Notice that <intent-filter> which was created for your starting activity.  The <intent-filter> identifies your activity as being the one that is launched first when the app is started.

The Intent serves two purposes: identify which activity to start and provide inputs to the activity.  Only one intent is used when starting another activity.  Here is the basic code (this assumes that you put this code into your Activity class otherwise "this" would need to be different)

```java
Intent intent = new Intent(this, MyNextActivity.class);
intent.putExtra("book","Alma");
intent.putExtra("chapter",5);
intent.putExtra("verse",42);
startActivity(intent);
```

In this example, the next activity is MyNextActivity and the inputs sent are 1 string and 2 integers.  There are several overloaded putExtra functions that allow you to put numbers, strings, and lists into the Intent.  Since the intent is created only when the activity needs to transition, it is usually not necessary to declare the intent as member data in the class.  You can write the code above a little quicker using chaining.  Since the put functions return in the updated intent, you could write:

```java
Intent intent = new Intent(this, MyNextActivity.class);
intent.putExtra("book","Alma")
      .putExtra("chapter",5);
      .putExtra("verse",42);
startActivity(intent);
```

In the receiving Activity (in this case MyNextActivity), you will need to read the intent:

```java
Intent intent = getIntent();
if (intent != null) { // If nothing was sent, make sure we don't try to read it
    book = intent.getStringExtra("book");     // If param not there, then it will be null
    chapter = intent.getIntExtra("chapter",0);  // If param not there, then set it to 0
    verse = intent.getIntExtra("verse",0);
}
```

If you wanted to put an object in, you have 2 options.  The first is to convert the object into a JSON string:

```java
Scripture s = new Scripture("Alma",5,42);
Gson gson = new Gson();
String s_json = gson.toJson(s);
intent.putExtra("scripture",s_json);
```

When you read the intent, you would use:

```java
String s_json = intent.getStringExtra("scripture");
Gson gson = new Gson();
Scripture s = gson.fromJson(s, Scripture.class);
```

The other option is to "implements" the Serializable interface in your data class (in this example called Scripture).  This interface has not functions to implement but it identifies the class as being serializable.  A serializable class has only member data is either native typed (e.g. int, float, boolean) or objects that implemented Serializable already (e.g. String or ArrayList ... look in the JavaDoc to see if the class implements Serializable).  If you do this, you will use the putExtra and the getSerializableExtra functions.

Besides putting individual items into the Intent or putting a JSON representation in the intent, you can also send a Bundle (putExtra(String name, Bundle value)).  A Bundle looks just like a Map.  It contains key/value pairs where each value is something that implements the Parceable interface.  Many classes implement the Parceable interface which provides an ability to serialize the data in the class.

You can also use an intent to send someone to another app on the phone.  To learn more about this, look at this website:  https://developer.android.com/training/basics/intents/sending

**Buttons and Callback Functions**

When you create a button in a layout, you can set the onClick property (in the layout designer) to the name of the function that you want to run when the button is clicked.  This is called a callback function.  Nowhere in your java code are you calling that function.  It is only called when the button clicked (event driven).

When you write the function, you need to ensure that it is public and has a parameter of type View.  This will allow the function to show up in the layout designer.

```java
public void sendMessage(View view) {
    // Do something
}
```

There is another way to do this.  Your Activity class can implement the OnClickListener.  This requires that you do 2 things:
1) Implement the onClick function in your class
2) Tell the button that you want your class to respond to the button click

The following code provides an example of implementing the OnClickListener for option 2 above:

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

@Override
protected void onCreate(Bundle savedInstanceState) {
    // ...
    Button b = findViewById(R.id.b_send_msg);
    b.setOnClickListener(this);
}

@Override
public void onClick(View view) {
    // Do Something
}
```

The 2nd option above an also be done with an anonymous class:

    public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        Button b = findViewById(R.id.b_send_msg);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do Something
            }
        });
    }


This pattern of implementing a listener that defines what you will do if something happens and then telling someone to use that listener (in this case, telling the button to use my implementation of onClick in my listener) is very common in Java and in other Object Oriented languages.  We will explore this more when we look at the Observer Design Pattern.

When you press the back button in Android, it will destroy the current activity and bring the previous activity back to life.  When you transition to another Activity via an Intent (or if you leave the app), the current Activity goes from the onResume state to the onPause and then the onStop state.  When you come back, it goes from onStop, to onRestart, to onStart, and finally onResume.  This means (unless you write code to change it), when you back to a previous activity, the contents of the screen are unchanged.

**Debugging on Android (including Logcat)**

There are two common ways to debug with Android.  Android Studio contains a complete debugger (click on the picture of the bug next to the play button) that will allow you to set breakpoints or view variables while its running on your phone or emulated phone.  

The use of Logcat is a great debug tool as well.  When you write your code, use Log.d (d stands for debug ... there is also Log.w for warnings, Log.e for errors, and Log.v for verbose):

```java
Log.d("weather","temp read: "+temp);
```

When you do this, it will create a log entry in the debug category with the "weather" tag.  You should use the tags to categorize your debug statements in your code.  To view the log, open the "Logcat" tab (look at the bottom of Android Studio or goto menu option View and select Tool Windows and then Logcat).  You will see a drop down box for the device (select your phone or emulated phone), the process (select your app), and then select "Debug".  You can type the tag you are looking for in the search window.  All the debug messages will be displayed.

You can also look in the logcat for exceptions.  If the app crashes, look in the logcat and see what line of code caused the problem.

You can still use System.out.println (maybe Log.* functions are better) to display information.  You can see this information in the Logcat as well.

**Databases**

There are ways that you can store information for your app: JSON data in a File, SQL Database, or Cloud Storage.

If you convert the information in JSON, then you can write the data into a file.  Its then easy to read from the file and convert the text back to your objects.  There are several classes in Java that deal with files.  This is sample code I have written for files in Android:

```java
public void saveFile(View view) {
    try {
        Data data = new Data(param1.getText().toString(),
                param2.getText().toString(),
                param3.getText().toString());

        Gson gson = new Gson();
        String json = gson.toJson(data);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("ParamFile.txt", Context.MODE_PRIVATE)));
        writer.write(json);
        writer.close();
        fileContents.setText("Wrote data to ParamFile.txt");
    }
    catch (IOException ioe) {
        fileContents.setText("Unable to save to ParamFile.txt");
        Log.d("files",ioe.toString());
    }
}

public void loadFile(View view) {
    try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("ParamFile.txt")));
        String json = reader.readLine();
        reader.close();

        Gson gson = new Gson();
        Data data = gson.fromJson(json, Data.class);
        fileContents.setText(data.toString());
    }
    catch (IOException ioe) {
        fileContents.setText("Unable to load from ParamFile.txt");
        Log.d("files",ioe.toString());

    }
}
```

In the code above, the Context.MODE_PRIVATE means that the file created is only available to the app.  Other apps (including the user of the phone) will not have access to the file you created.

Here is a link for additional information about files in Android:  https://developer.android.com/training/data-storage/files

You can use SQL database directly on the phone.  Here is a resource to learn how to do that:  https://developer.android.com/training/data-storage/sqlite

A common cloud storage service that students use for CS246 is Firebase by Google.  You will need to create an account, add libraries to the gradle build files, and use the tutorials/sample code available on their website.  You can learn more here: https://firebase.google.com/

Note that you should not use shared preferences (See below) for large amounts of data.

**Layout**

The layout xml's (res/layouts) are used to place layouts, containers, and objects (buttons, textboxes, etc) onto the screen.  You can create the xml manually or you can design it using the layout designer.  Additionally, you can create new components and add them from your Java code (useful if you display will be dynamic).  Sometimes it can be easier to write or modify the XML manually when you are working with embedded layouts.  For example, when you create fragments or recycler views, you will create a separate layout file for each individual fragment or for the format of each row in the recycler view.

The ID for each component in the layout is your link to the java code.  Make sure you name the ID's in a way that you will be able to recognize them in you code.  We use findViewById to get a component (or View) out of the layout.  You can only get a view after the setContentView function is called in your Activity.  When you use these functions, you have to specify the ID by using R.  R is a class that contains all the ID's from the layout.  R is auto-generated based on the layout XML's.

If you want to remove the title bar at the top, you have to change the theme in the manifest file:   https://developer.android.com/training/system-ui/status 

**Shared Preferences**

There are 2 kinds of preferences in Android:
* Preferences - Using the getPreferences() function, you can get the default preference "file" for your app.  There is only one default for each app.
* SharedPreferences - Using the getSharedPreferences() function, you can get a custom named preference "file".  You can have several of these.  When you use this, you will specify the name of the file and whether you want the information to be private to your app.

You should use this to store small amounts of data.  For example, in assignment 5, the favorite scripture is easy to store in preferences.

You can see some example code here:  https://github.com/macbeth-byui/CS246_Class/tree/master/SharedPrefExample/app/src/main/java/macbeth/sharedprefexample

**The @override Tag**

When you do a CTRL-O in IntelliJ or Android Studio to implement functions from a base class or interface, it will add @override.  This is called a decorator (the @).  Decorators are used to modify code or enforce code rules.  In this case, when you add @override, you are stating that you intend the function to override (or implement) a function from a base class or interface.  If you incorrectly provide the function interface (wrong data types, wrong function names, wrong return type) then the @override tag will provide a compiler error.

**Code Organization**

When you are organizing your code, two suggestions:

1) Create packages to collect related code
2) Split your code up into classes for user interface (the Activities and Fragments), classes for data (e.g. JSON and ability to read from external sources), and classes for logic.  We call the Model-View-Controller (MVC) design pattern.  We will be discussing this in the near future as part of review of design patterns.

**Gradle**

To use GSON (or any other library that you may have found on the Maven Repository) in Android Studio, it is recommended to use the gradle configuration files.  There are 2 gradle.build files that need to be used in various situations.  Frequently when you are following a tutorial for a library, it will give you instruction to modify these files.  In the module gradle file, if you wanted to use GSON, you would add:

```xml
	implementation 'com.google.code.gson:gson:2.8.5'
```

You also you this file to set the compileSdkVersion (the version you will use the compile) and minSdkVersion (the minimum version that the phone must have installed to run your app).