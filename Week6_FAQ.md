**Threading**

Threading is needed whenever we want our software to do more than one thing at a time.  When a user interface is involved, we usually don't want the user to have to wait for things to finish before allowing them to use the user interface again.  This is important with a phone because we want the user to continue to use the app (no frozen phone) while we read things from the internet, files, or databases.

When we use threads, the operating system will manage which thread gets to run.  If we have multiple cores in our processor, the operating system will be able to do more things at the same time.  Threads can be given priorities so that important tasks do not get interrupted.

When we work with threads, there are some important things we need to think about:
1) Are there objects/variables which more than one thread need to use?  If the threads will read the data, this is usually okay.  However, if more than one thread need to write to the same data at the same time, this can be dangerous.  We usually need to protection around that data so only one thread can access it at a time (usually a queue).  This is often called the Reader-Writer problem.  We can use the "syncrhonized" keyword in Java to ensure that a function is only called by one thread at a time.  
2) You need to consider if the order of the threads matters.  When you start more than one thread, there is no guarantee that one will finish before another.  There are ways that we can wait for a thread to finish (we call that joining a thread).  If two threads are waiting on each other, then this is a situation called deadlock.  
3) How long will the thread run?  Is it intended to run forever (in Android we call that a service thread) or is a short task that needs to be done in the background that will finish.

If you want to pass data to a Thread, you need to pass it in the constructor before you start the thread.  

The official Java documentation for threads (or concurrency) can be found here: https://docs.oracle.com/javase/tutorial/essential/concurrency/index.html

**The runOnUiThread Function**

In Android, every Activity class has the runOnUiThread function available.  The purpose of this function is to provide a way for a background thread to run code on the parent UI thread.  This function is unusual in that it takes a Runnable object.  When you can the runOnUiThread function, the code will execute the run function you provide in the UI thread.

If your thread calls runOnUiThread multiple times, there is no guarentee that these calls will finish at the same time.  Notice that they are not "blocking" functions.  When you call runOnUiThread, it goes directly to the next line of code in your thread (the stuff in the runOnUiThread is running in a different thread).

If you want to use the runOnUiThread function from a class that is not an Activity, then the constructor to your class must receive your Activity object.  For example:

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
                }
            });
         }
     }

In this example, the activity was used both to call runOnUiThread and to call a function that would update the display.

If the thread is still running but the Activity is destroyed (someone closes the app or navigates back to a previous activity screen), then the Activity object will not get deleted (garbage collection) because the Thread still has a reference to the Activity.  We can prevent this by using a WeakReference which will allow the Activity to be deleted even if the thread is not done yet.  If we allow it to be deleted, then we need to check to make sure the activity still exists:

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

**AsyncTask**

The AsyncTask was developed just for Android.  It uses the Java Thread class.  Instead of creating a Thread and using runOnUiThread for all of the updates to the user interface, the AsyncTask provides a framework of functions which run in either the UI thread or the background thread:

* onPreExecute - runs in the UI thread before the background thread runs
* doInBackground - runs in the background thread
* onProgressUpdate - runs in the UI thread whenever doInBackground calls the publishProgress function
* onPostExecute - runs in the UI thread after the background thread runs

To make the AsyncTask generic, many of these functions allow for data to passed between them.  Therefore, AsyncTask was made into a template of three variable data types AsyncTask<Type1, Type2, Type3>.  The types are used in the following way:

* Type1 - Type of the parameters sent to doInBackground
* Type2 - Type of the parameters sent to onProgressUpdate via the publishProgress function
* Type3 - Type of the parameter returned from doInBackground and sent to onPostExecute

The big drawback of the AsyncTask is its complexity.  However, the AsyncTask does allow you to organize all the phases of the task and it also ensures that actions occur at the right times.  If you have more than one AsyncTask, the default behavior of Android is for the AsyncTasks to run in serial (one right after the other).  This is a benefit over using just plain Threads for Android.

**ListView**

A ListView is a container in Android that can display a list to the screen (see the discussion of RecyclerView below which is a replacement for ListView).  When you see a scrollable list on a phone, you should think of a scrollable ArrayList object.  The ListView is connected to an ArrayList using an ArrayAdapter.  An adapter is something that is used to convert from one thing to another.  In this case, we want to convert between an ArrayList<String> to a ListView.  

    ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArray);
    myListView.setAdapter(adapter);

In this code, the adapter has a "simple list" layout (this is a layout provided by android) and will be mapped to myArray (which contains String values).  If the myArray ever changed, we would need to notify the ListView that something changed:

    (ArrayAdapter)(myListView.getAdapter()).notifyDataSetChanged();

If I receive JSON data and I want to put it into a ListView, then I probably have an ArrayList containing objects that match the JSON structure.  For example, if I have ArrayList<MyStructure>, then my ArrayAdapter would be ArrayAdapter<MyStructure> as well.  You will need to provide a toString function in your MyStructure class for the ListView to work properly.

**RecyclerView**

The RecyclerView was recently put into Android to provide better performance and allow you to have more interesting content in your list.  When you create a RecyclerView, you need to create a RecyclerView.Adapter to explain how we will go from our data to the list in the UI.  The RecyclerView.Adapter is a template class where the type provided is another class you have to write that inherits from the RecyclerView.ViewHolder.  A ViewHolder will remeber the content for each item in the list.  The Adapter will create objects of the ViewHolder for each item in the list.  The Adapter will also connect (or bind) the data to each element in the ViewHolder.  Here is a simple example of an Adapter for a RecyclerView:

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
            viewHolder.getField().setText("Info: " + data.get(itemNum).getInfo());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        // This class will be used to create an object for each row in the list
        // The contents of each TextView will be remembered.
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView field1;
            private TextView field2;
            private TextView field3;
 
            public ViewHolder(View itemView) {
                super(itemView);
                field1 = itemView.findViewById(R.id.field1);  // These 3 fields are in the
                field2 = itemView.findViewById(R.id.field2);  // my_item_layout (single row
                field3 = itemView.findViewById(R.id.field3);  // of the list)
            }

            public TextView getField1() {
                return field1;
            }

            public TextView getField1() {
                return field1;
            }

            public TextView getField2() {
                return field2;
            }


         }

The adapter is applied to the ReyclerView using the setAdapter function just like we did for the ListView.  If something in the data changes, we also need to call the notifyDataSetChanged function as well.

**Handlers**

In the instructor solution for Assignment 6, a "handler" concept was used.  Most solutions for the assignment would have a class (call it GetTemp) which implemented a Thread.  The GetWeather function might have directly communicated with the OpenWeatherAPI server.  However, since you probably also had another class called GetForecast which also implemented a Thread, perhaps you created a separate class to communicate with OpenWeatherAPI (call it WeatherDataLoader).  In this setup, the WeatherDataLoaderobject would send information back to either GetTemp or GetForecast.  The WeatherDataLoader could have just returned a String, but in the solution, it called a function that was provided by GetTemp and GetForecast.  To provide a callback function, we create an interface that has a callback function inside of it.  Then we create an object of that interface providing an implementation of the callback.  When WeatherDataLoader is done, it will call that callback function and the correct UI updates will be made.

This is an example of the Observer design pattern that we will discuss more in the future.  However, in this case, there was not much benefit of using it in this assignment since the WeatherDataLoader did not run in a separate thread.

**Databases**

In the previous FAQ, there was a discussion about how to store data in either files or into databases.  There are two types of databases that we can use:

* SQL Databases - These databases use tables to store the information.  The SQL language is used to query these tables for information.  You will have to learn how to write SQL statements to get information out of this database.  The benefit is you will receive exactly the data that you need.  SQL-Lite is available for Android.
* Non SQL Databases - These databases store data in JSON format.  When you want information, you will request the data from a table and it will send it all to you in JSON format.  You can think of it as a list of JSON objects.  If something is added or modified, you can receive notifications.  Firebase is a common non-sql database that CS246 students use.
Databases in Java - SQL or Firebase (Non-SQL - JSON)

**Logging**

Refer to the previous FAQ for questions about using logcat in Android Studio.  Here is a good reference as well: https://developer.android.com/studio/debug/am-logcat 
