**Android and Java**

The Android framework provides classes and tools to allow you to write Java code for an Android app.  When you write code to display to or respond from the user interface, this will be in the Activity classes.  However, just like other software you have written, you will need code that performs tasks, makes decisions, and read/write data.  This will be done using regular Java classes.  You Activity object will likely make instances of these Java classes that you write.  All of the data structures, GSON/JSON, and threading that you have done before will still work the same in Android.

Android Studio is used to make Android Apps.  This is different from other software such as Web App's or command line software.  The later two take a little less infrastructure to create.  An App for Android (or iOS) requires compatibility with the operating system platform.  This requires a lot more code and configuration.  However, the investment can pay off because the end result can be a marketable piece of software that can be sold and used by consumers.

The following links are good for understanding Android:

- Android Developer Guide:  https://developer.android.com/guide 
- Head First Android: https://learning.oreilly.com/library/view/head-first-android/9781491974049/
- When you need to google something, I recommend searching: "android java ???" replacing ??? with the topic you are trying to understand.  Anything starting with " https://developer.android.com/reference" will take you to the related JavaDoc

**Activities and Intents**

Before diving into how to use Activities and Intents, realize first that they are just classes and that objects are made out of them.  At a basic level, this is part of large UML diagram that supports both the Android platform and your App functionality.

An Activity is used to represent a single Android screen.  If you were playing a game, checking the weather, or looking at your bank account in an app, the screen you are looking at is an Activity object.  You can stay on a single activity screen for a long time with various interactions and drawing occurring on the same screen.  There is always a layout xml file associated with each Activity.  Using either the layouts xml or by dynamically creating layout objects in your Java code you can create graphics (https://developer.android.com/guide/topics/graphics).  If you are using an emulator, some of these graphics may not be as good due to a lack of processor and memory resources.  Usually, there is a big improvement we you run your app on a real phone.

The Activity is based on a state machine going from onCreate, onStart, onResume, onPause, onStop, and onDestroy.  These functions are called when something happens to your Activity.  If you want to do something when these transitions are called, then you should override them in your Activity class.  Usually the only one you must have is onCreate.

* The onCreate is like a constructor for you.  You should do all of your initialization items here including registration for events (e.g. listen for button clicks).  
* The onStart is called when the Activity becomes visible to the user.  This can be a useful time to enable anything that you may have disabled when you went into the onStop state.
* The onResume is called with the user can interact with the Activity
* The onPause is called when the user about to leave the Activity for any reason.  This is good time to save any information you don't want to lose if you end up going to onDestroy.  Generally only small amounts of data should be saved since this is a short state.
* The onStop is called when the user no longer can see the Activity but the Activity object is still alive.  This is a good time to save any information you don't want to lose and also a good to time to disable anything that is not needed until the user returns to your activity.
* The onDestroy is called when the Activity object is going to be permanently deleted from memory.  

Generally, code will only run in one of these "on" states or in a function that is called when an event occurs (e.g. button pressed).  If someone is typing in an EditText, you won't usually care about it (unless you have a key listener registered) until someone pushes a button.  

An Intent object is created whenever you want to start (transition) to another Activity.  The first Activity in your app is started by an Intent as well.  You can see this in your manfiest.xml.  Notice that <intent-filter> which was created for your starting activity.  The <intent-filter> identifies your activity as being the one that is launched first when the app is started.

The Intent serves two purposes: identify which activity to start and provide inputs to the activity.  Only one intent is used when starting another activity.  Here is the basic code (this assumes that you put this code into your Activity class otherwise "this" would need to be different).  When you apply the inputs to the next activity (called the Intent), you can have multiple Extra's included.  This is setup like a HashMap with a Key/Value pair for each Extra (i.e. input) put into the Intent.  Notice that the first parameter is the key name which is a String.  I have hard coded it here, but you could have create static constants instead (eg., private final static String INTENT_EXTRA_BOOK_NAME = "book";)

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

When you create a button in a layout, you can set the onClick property (in the layout designer) to the name of the function that you want to run when the button is clicked.  This is called a callback function.  Nowhere in your java code are you calling that function.  It is only called when the button is clicked (event driven).

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

```java
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
```


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

When you create a layout, you can either hard code values into the fields or you can provide references to string values defined in strings.xml.  When you do this, the phrase "@string" is put in front of the name you created.

There are many options to change the look and feel of everything you put in your layout.  There are fonts, size, backgrounds, and colors available to be set in the layout editor.  All of these things can also be set directly in your Java code.  If you want to add a picture to your screen, consider using the ImageView component.

If you want to remove the title bar at the top, you have to change the theme in the manifest file:   https://developer.android.com/training/system-ui/status 

**TextView and EditText**

The TextView and EditText are components in the layout that you can use to build the user interface of your activity.  Both of these are classes that have within them String member data.  The String in the TextView can be changed by calling setText on the the TextView object.  However, the User cannot change the TextView.  The EditText is intended to be modified by the user by typing on the phone keyboard.  To see what the user typed or to change what the user typed, you can call the setText or getText functions.

**Shared Preferences**

Shared Preferences are like file storage in that they are remembered even if the phone is power cycled.  When you store data using Shared Preferences, the data is stored like a HashMap.  Each item you put in Shared Preferences is usually given a String name (or key).  The structure is similar to how Extras are put into an Intent.  There are 2 kinds of preferences in Android:
* Preferences - Using the getPreferences() function, you can get the default preference "file" for your activity.  There is only one default for each activity.  This can not be shared with other activities.
* SharedPreferences - Using the getSharedPreferences() function, you can get a custom named preference "file".  You can have several of these.  When you use this, you will specify the name of the file.  These are shared between all Activities of your App.

You should use this to store small amounts of data.  For example, in assignment 5, the favorite scripture is easy to store in preferences.

When you save data, you should think about how other Activities may need the data.  For example, in assignment 5, the display activity needs to be able to save the scripture so that the prompt activity can load the scripture.  Since the prompt activity needs the scripture split up into 3 parts, it would be wise to send the 3 parts (book ,chapter, verse) separately in the intent so that the display scripture can store the 3 pieces separately in the Shared Preferences.

You can see some example code here:  https://github.com/macbeth-byui/CS246_Class/tree/master/SharedPrefExample/app/src/main/java/macbeth/sharedprefexample

**The @override Tag**

When you do a CTRL-O in IntelliJ or Android Studio to implement functions from a base class or interface, it will add @override.  This is called a decorator (the @).  Decorators are used to modify code or enforce code rules.  In this case, when you add @override, you are stating that you intend the function to override (or implement) a function from a base class or interface.  If you incorrectly provide the function interface (wrong data types, wrong function names, wrong return type) then the @override tag will provide a compiler error.

**IDE's**

Integrated Development Environments (IDE) are tool used by software engineers to develop software.  They were created to combine multiple tools and capabilities into a single platform.  There are many IDE's out there and some companies will let you choose your IDE while others will mandate which one you have to use.  Thankfully, all IDE's have common capabilities albeit different ways of performing those capabilities.  Generally, once you have learned one IDE, it makes it easier to learn a new one as needed.  Some IDE's are platform specific like Android Studio (which allows you to create Android app's in either Java or Kotlin).  Some IDE's work with multiple programming languages but do not include any compilers or libraries (e.g. Visual Studio Code).

Many people find Android Studio to be a very difficult tool to learn.  Part of the reason is the integrate gradle build environment which can be slow (depending on your computer) and the unclear error messages.  However, without this build environment, the manual process of creating complex software like an Android App (and it is complex) would be impractical to do.  The best way to learn the IDE is to use it for an extended period of time.  Even after this course, you should work on some App that you have started that you want to "tinker" with for the next several years.

**Code Organization**

When you are organizing your code, two suggestions:

1) Create packages to collect related code
2) Split your code up into classes for user interface (the Activities and Fragments), classes for data (e.g. JSON and ability to read from external sources), and classes for logic.  We call the Model-View-Controller (MVC) design pattern.  We will be discussing this in the near future as part of review of design patterns.

**Gradle**

To use GSON (or any other library that you may have found on the Maven Repository) in Android Studio, it is recommended to use the gradle configuration files.  There are 2 gradle.build files that need to be used in various situations.  Frequently when you are following a tutorial for a library, it will give you instruction to modify these files.  In the module gradle file, if you wanted to use GSON, you would add:

```xml
	implementation 'com.google.code.gson:gson:2.8.6'
```

You also you this file to set the compileSdkVersion (the version you will use the compile) and minSdkVersion (the minimum version that the phone must have installed to run your app).