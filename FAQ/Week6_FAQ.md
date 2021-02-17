## CS 246 Week 6 Support Material

**Examples and Videos**

The following examples and videos support the material in Week 6.  Subsequent sections below provide additional information and examples for common questions that students encounter during this week.

* Video: https://video.byui.edu/media/CS246+Week+6+Multithreading/0_uzsf9e8s
* Work Example (Java + Threading): https://github.com/macbeth-byui/CS246_Class/tree/master/worker/src/macbeth
* WorkerApp Example (Android + Threading): https://github.com/macbeth-byui/CS246_Class/tree/master/WorkerApp
* EarthquakeMapper (Android + ArcGIS + Threading): https://github.com/macbeth-byui/CS246_Class/tree/master/EarthquakeMapper
* ProcessRequestsApp (Android + Threading): https://github.com/macbeth-byui/CS246_Class/tree/master/ProcessRequestsApp

**Threading**

Threading is needed whenever we want our software to do more than one thing at a time.  Possible examples include:

- Complete more than one task at the same time
- One thread is receiving requests and one thread is processing them
- Every request that comes into a server is processed by a separate thread (so the server can keep going)
- Completing long tasks that would prevent the software from proceeding

When we decide what needs to go into a Thread, multiple possibilities exist:

* If we have different tasks that do different things and these tasks will happen at different times, then it makes sense to create separate Thread (or runnable) classes.
* If we have different tasks that do the same things, then we will want to write a single Thread class and reuse as needed
* If we have different tasks that need to be done at the same time in "parallel", we should start the multiple threads one right after the other.  Note that in the discussion about AsyncTasks below, the AsyncTask will run multiple AsyncTasks in serial.
* If we have different tasks that the need to be done in order, then a single Thread class should be created.  Even though we have 1 Thread class, we can still create multiple classes for the different tasks and then call them in order in the 1 Thread class we created.

When a user interface is involved, we usually don't want the user to have to wait for things to finish before allowing them to use the user interface again.  This is important with a phone because we want the user to continue to use the app (no frozen phone) while we read things from the internet, files, or databases.

When we use threads, the operating system will manage which thread gets to run.  If we have multiple cores in our processor, the operating system will be able to do more things at the same time.  Threads can be given priorities so that important tasks do not get interrupted.

When we work with threads, there are some important things we need to think about:

1. Are there objects/variables which more than one thread need to use?  If the threads will read the data, this is usually okay.  However, if more than one thread need to write to the same data at the same time, this can be dangerous.  We usually need to protection around that data so only one thread can access it at a time (usually a queue).  This is often called the Reader-Writer problem.  We can use the "synchronized" keyword in Java to ensure that a function is only called by one thread at a time.  
2. You need to consider if the order of the threads matters.  When you start more than one thread, there is no guarantee that one will finish before another.  There are ways that we can wait for a thread to finish (we call that joining a thread).  If two threads are waiting on each other, then this is a situation called deadlock.  
3. How long will the thread run?  Is it intended to run forever (in Android we call that a service thread) or is a short task that needs to be done in the background that will finish.

If you want to pass data to a Thread, you need to pass it in the constructor before you start the thread.  

There are limits to the number of Threads you can run based on your operating system.  

The official Java documentation for threads (or concurrency) can be found here: https://docs.oracle.com/javase/tutorial/essential/concurrency/index.html

**Threads in Java**

To create a Thread in Java there are 2 choices:

* Create a class that extends the Thread class - We do this if our class does not already have a base class
* Create a class that implements the Runnable interface - We do this if our class already has a base class. 

Notwithstanding these rules, you can choose to always implements Runnable as it doesn't make any difference to performance and capability.

The examples below show how to define a Thread using the 2 methods:

```java
public class MyTask1 extends Thread {

	public MyTask1() {
		// Initialize any variables you want to remember
	}
	
	@override
	public void run() {
		// The code you put here will run in the Thread
	}
}

public class MyTask2 implements Runnable {

	public MyTask2() {
		// Initialize any variables you want to remember
	}
	
	@override
	public void run() {
		// The code you put here will run in the Thread
	}
}

```

To start these two threads, you would do the following in another function:

```java
MyTask1 task1 = new MyTask1();
task1.start();  // This will start the Thread

MyTask task2 = new MyTask2();
Thread thread = new Thread(task2); // Create Thread with the runnable
thread.start(); // This will start the Thread
```

Notice that the run function does not receive any parameters (the async task described below can take parameters).  If you need inputs to be sent in, they should be provided in the constructor so they are available when the run function is executed.

When you create Threads in Java, there is a function setPriority which can be used to set the priority of your threads.  Threads with higher priority will get to execute first.

When you start a Thread, its parent Thread is based on the function that started the Thread (the use of a structure chart or calling tree).

Java has some deprecated (not supported or advised but still available) functions to allow you to destroy or stop a thread.  However, its better to put logic in any loops you have (or have timeouts set properly in your network stream) to ensure that the thread will stop on its own in a failure condition.

**Threads and Android**

In Android there are very specific reasons that we need to use a Thread.  Without following this guidance, either the software will not run or it will not run well.  The general rule is that long processing should not take place in the UI Thread (which is where the Activity classes execute).

* You cannot read from a network connection in the UI Thread.  If you try then you will get an exception causing your app to exit.
* You can read and write files but if the files are very big then you will want to put them in a thread to avoid delays in the user interface.
* If you have long executing algorithms (multiple loops), you may want to put these in a Thread to avoid delays in the user interface.

There are two common ways of doing Threads in Android.  Using the Thread (or Runnable with Thread) class or using the AsyncTask.  The AsyncTask has been commonly used in alot of Android software but has recently become deprecated (not recommended to be used anymore).  Since Android is an actively supported tool, it is recommended that you refer to the Android Development website for the latest guidance.

**Updating Screen (UI) From Thread**

You cannot update the screen (user interface - UI) from a background thread directly.  UI updates must be done in the UI thread and long duration tasks must be done in background threads.  This is the rule in Android development.  The most common solution is to use `runOnUiThread` (described below).  In the past, `AsyncTask` was a common solution (described further below) but is no longer recommended by Android.  There is a third option called the `Handler`.   The `Handler` is used to communicate messages between different threads.  `runOnUiThread` is implemented using a `Handler`.  For the projects in this course, `runOnUiThread` is recommended.

In Android, every Activity class has the runOnUiThread function available.  The purpose of this function is to provide a way for a background thread to run code on the parent UI thread.  This function is unusual in that it takes a Runnable object.  When you can the runOnUiThread function, the code will execute the run function you provide in the UI thread.  Your app has only 1 UI Thread which all Activities in the App share.

If your thread calls runOnUiThread multiple times, there is no guarentee that these calls will finish at the same time.  Notice that they are not "blocking" functions.  When you call runOnUiThread, it goes directly to the next line of code in your thread (the stuff in the runOnUiThread is running in a different thread).

If you want to use the runOnUiThread function from a class that is not an Activity, then the constructor to your class must receive your Activity object.  For example:

```java
class MyThread extends Thread {

    private MyActivity activity;

    public MyThread(MyActivity activity) {
        this.activity = activity;
    }
 
    public void run() {
        // Do a bunch of stuff
        activity.runOnUiThread(new Runnable() {
            public void run() {
                activity.updateActivityDisplay(...);
                // The updateActivityDisplay function 
                // in the MyActivity class would do things
                // like making a Toast or updating the 
                // list view.
            }
        });
     }
 }
```

In this example, the activity was used both to call runOnUiThread and to call a function that would update the display.

If the thread is still running but the Activity is destroyed (someone closes the app or navigates back to a previous activity screen), then the Activity object will not get deleted (garbage collection) because the Thread still has a reference to the Activity.  We can prevent this by using a WeakReference which will allow the Activity to be deleted even if the thread is not done yet.  If we allow it to be deleted, then we need to check to make sure the activity still exists:

```java
class MyThread extends Thread {

    private final WeakReference<MyActivity> activity;

    public MyThread(MyActivity activity) {
        this.activity = new WeakReference<MyActivity>(activity);
    }
 
    public void run() {
        // Do a bunch of stuff
        if (activity.get() != null) {
            activity.get().runOnUiThread(new Runnable() {
                public void run() {
                    activity.get().updateActivityDisplay(...);
                }
            });
         }
     }
 }
```

**AsyncTask**

The AsyncTask was developed just for Android.  It has been the standard for Android for many releases but has recently been deprecated (not recommended to use anymore).  The AsyncTask still works but has been discouraged due to misuse by developers (see discussion about WeakReference below).  Android currently recommends to use the Thread class with runOnUiThread.

It uses the Java Thread class.  Instead of creating a Thread and using runOnUiThread for all of the updates to the user interface, the AsyncTask provides a framework of functions which run in either the UI thread or the background thread:

* onPreExecute - runs in the UI thread before the background thread runs
* doInBackground - runs in the background thread
* onProgressUpdate - runs in the UI thread whenever doInBackground calls the publishProgress function
* onPostExecute - runs in the UI thread after the background thread runs

To make the AsyncTask generic, many of these functions allow for data to passed between them.  Therefore, AsyncTask was made into a template of three variable data types AsyncTask<Type1, Type2, Type3>.  The types are used in the following way:

* Type1 - Type of the parameters sent to doInBackground
* Type2 - Type of the parameters sent to onProgressUpdate via the publishProgress function
* Type3 - Type of the parameter returned from doInBackground and sent to onPostExecute

The big drawback of the AsyncTask is its complexity.  However, the AsyncTask does allow you to organize all the phases of the task and it also ensures that actions occur at the right times.  If you have more than one AsyncTask, the default behavior of Android is for the AsyncTasks to run in serial (one right after the other).  This is a benefit over using just plain Threads for Android.

If I wanted to implement the same MyThread function show in the example above using an AsyncTask, it would look like this:

```java
class MyThread extends AsyncTask<Void, Void, Void> {

    private final WeakReference<MyActivity> activity;

    public MyThread(MyActivity activity) {
        this.activity = new WeakReference<MyActivity>(activity);
    }
 
    public Void doInBackground(Void... voids) {
        // Do a bunch of stuff
        return null;
    }
    
    public Void onPostExecute(Void void) {}
        if (activity.get() != null) {
            activity.get().updateActivityDisplay(...);
        }
    }

 }
```

Some additional considerations:

* AsyncTask is intended to be used for short duration activities (e.g. reading data from a server, completing an algorithm)
* Long duration tasks (maybe running forever) should use Thread
* If you create multiple AsyncTasks and run them, then they will run in serial instead of simultaneously.  Using ThreadPools you can tell Android to run them in parallel, but this is not their default behavior.  

**ListView**

A ListView is a container in Android that can display a list to the screen (see the discussion of RecyclerView below which is a newer version for ListView ... ListView is not deprecated).  When you see a scrollable list on a phone, you should think of a scrollable ArrayList object.  The ListView is connected to an ArrayList using an ArrayAdapter.  An adapter is something that is used to convert from one thing to another.  In this case, we want to convert between an ArrayList<String> to a ListView.  

```java
ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArray);
myListView.setAdapter(adapter);
```

In the Weather Forecast example, the ArrayAdapter would have a template type of <WeatherForecast>.  To allow the forecast to be displayed in the list, you should implement a toString function in the WeatherForecast class.

In this code, the adapter has a "simple list" layout (this is a layout provided by android) and will be mapped to myArray (which contains String values).  If the myArray ever changed, we would need to notify the ListView that something changed:

```java
adapter.notifyDataSetChanged(); // Assume the adapter is private member data in the class
```

If I receive JSON data and I want to put it into a ListView, then I probably have an ArrayList containing objects that match the JSON structure.  For example, if I have ArrayList<MyStructure>, then my ArrayAdapter would be ArrayAdapter<MyStructure> as well.  You will need to provide a toString function in your MyStructure class for the ListView to work properly.

**RecyclerView**

The RecyclerView was recently put into Android to provide better performance and allow you to have more interesting content in your list.  When you create a RecyclerView, you need to create a RecyclerView.Adapter to explain how we will go from our data to the list in the UI.  The RecyclerView.Adapter is a template class where the type provided is another class you have to write that inherits from the RecyclerView.ViewHolder.  A ViewHolder will remeber the content for each item in the list.  The Adapter will create objects of the ViewHolder for each item in the list.  The Adapter will also connect (or bind) the data to each element in the ViewHolder.  Here is a simple example of an Adapter for a RecyclerView:

```java
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<MyStructure> data;

    public MyAdapter(List<MyStructure> data) {
        // When we create the adapter, we provide the data
        this.data = data;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemNum) {
        // Create a ViewHolder for each row in the list.  Each row in the list
        // is represented by the my_item_layout that was designed for a single
        // row of the list.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.my_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int itemNum) {
        // Connect the data (the text in our data list) to the TextView's in the
        // the ViewHolder.  Remember that the ViewHolder contains the data for a 
        // single row in the list.
        viewHolder.getInfoField().setText("Info: " + data.get(itemNum).getInfo());
        viewHolder.getStatusField().setText("Status: " + data.get(itemNum).getStatus());
        viewHolder.getErrorField().setText("Error: " + data.get(itemNum).getError());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // This class will be used to create an object for each row in the list
    // The contents of each TextView will be remembered.
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView infoField;
        private TextView statusField;
        private TextView errorField;
 
        public ViewHolder(View itemView) {
            super(itemView);
            // These 3 fields are in the my_item_layout (single row of a the list)
            infoField = itemView.findViewById(R.id.infoField);      
            statusField = itemView.findViewById(R.id.statusField);  
            errorField = itemView.findViewById(R.id.errorField);    
        }

        public TextView getInfoField() {
            return infoField;
        }

        public TextView getStatusField() {
            return statusField;
        }

        public TextView getErrorField() {
            return errorField;
        }
    }
```

In the `onCreateViewHolder` function, it specifies the layout XML used for each row of the list.  An example XML layout for the code above would be:

```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">


    <TextView
        android:id="@+id/info_field"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:textSize="30sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:text="0.0" />

    <TextView
        android:id="@+id/status_field"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/info_field"
        app:layout_constraintTop_toTopOf="parent"
        tools:text="Location" />

    <TextView
        android:id="@+id/error_field"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="8dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/info_field"
        app:layout_constraintTop_toBottomOf="@+id/status_field"
        tools:text="DateTime" />
</androidx.constraintlayout.widget.ConstraintLayout>
```



The adapter is applied to the ReyclerView using the setAdapter function just like we did for the ListView.  If something in the data changes, we also need to call the notifyDataSetChanged function as well.

**Databases**

In the previous FAQ, there was a discussion about how to store data in either files or into databases.  There are two types of databases that we can use:

* SQL Databases - These databases use tables to store the information.  The SQL language is used to query these tables for information.  You will have to learn how to write SQL statements to get information out of this database.  The benefit is you will receive exactly the data that you need.  SQL-Lite is available for Android.
* Non SQL Databases - These databases store data in JSON format.  When you want information, you will request the data from a table and it will send it all to you in JSON format.  You can think of it as a list of JSON objects.  If something is added or modified, you can receive notifications.  Firebase is a common non-sql database that CS246 students use.
Databases in Java - SQL or Firebase (Non-SQL - JSON)

**Logging**

Refer to the previous FAQ for questions about using logcat in Android Studio.  Here is a good reference as well: https://developer.android.com/studio/debug/am-logcat 

**Listeners**

Traditionally, when you want to define what to do when someone pushes a button (or want to define what to do when the user does anything with the layout in your activity). you create a Listener object.  The standard way of doing this (using a button as an example) is:

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

**Anonymous Classes**

In the example of the OnClickListener and the runOnUiThread from above discussions, notice that we created a new object and then defined the functions in those objects.  The reason we did this was because when we said `new`, we were creating an object of an interface.  However, since the interface has no implementations, we have to provide them.  This is called an anonymous class (or nameless class).

Some interesting rules when implementing the functions in an anonymous class must be observed:

* The keyword `this` will not refer to the parent class.  While inside the code of an anonymous class, you will need to specify the parent class name if you need to.  For example: 

  ```java
  Toast.makeText(MainActivity.this, "Hello!", Toast.LENGTH_LONG).show();
  ```

* You can't access local variables in the parent function unless they are `final` (constant).   However, you can access member data in the parent class.  Remember that the function in the anonymous class may be called at a future time (e.g. when someone actually pushes the button).  Consideration should be given to what might have changed in the variables the function will use by the time the button is actually pressed.

**Android Permissions**

There are 2 kinds of permissions in Android:

* The phone owner has to specifically grant permission to do something (e.g. keep track of your location)
* The phone owner has to accept a permission when they download an app (e.g. accessing the internet)

These permissions need to be listed in the AndroidManifest.xml.  If you saw this error: "java.lang.SecurityException: Permission denied (missing INTERNET permission?)" in your logcat, then this should prompt to look at the android documentation about the INTERNET permission (https://developer.android.com/training/basics/network-ops/connecting).  Here is what you need in your manifest file:

```
<uses-permission android:name="android.permission.INTERNET" />
```

**Toast Delays**

The `Toast` has the option of either being Long or Short:

* LENGTH_LONG - 3 seconds
* LENGTH_SHORT - 2.5 seconds

