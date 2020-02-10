package macbeth.workerapp;

import android.os.AsyncTask;

import java.util.Random;

/*
The AsyncTask class is a template class that has 3 variable types:

1) First Variable Type - The input into doInBackground (note this is variable list which is accessed like an array)
2) Second Variable Type - The input into onProgressUpdate (note this is a variable list which is accessed like an array)
3) Third Variable Type - The output from doInBackground and the input to onPostExecute
 */
public class Job3 extends AsyncTask<Void,Integer,String> {

    private int jobSize;
    private MainActivity activity;

    public Job3(int jobSize, MainActivity activity) {
        this.jobSize = jobSize;
        this.activity = activity;
    }

    /*
    This is run in the UI thread before doInBackground begins.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.displayOutput("Starting job with "+jobSize+" steps.");
    }

    /*
       This is run in the background thread.  No updates to the UI are done in this function.
        */
    @Override
    protected String doInBackground(Void... voids) {
        Random random = new Random();
        for (int i = 1; i <= jobSize; i++) {
            publishProgress(i);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "Completed all "+jobSize+" steps.";
    }

    /*
    This is run on the UI thread whenever publishProgress is called in doInBackground
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        activity.displayOutput("Runing step "+values[0]+" of "+jobSize+",");
    }


    /*
    This is run on the UI thread when doInBackground is done
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        activity.displayOutput(s);
    }

}
