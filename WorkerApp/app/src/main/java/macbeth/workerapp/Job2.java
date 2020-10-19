package macbeth.workerapp;

import java.util.Random;

public class Job2 extends Thread {

    private int jobSize;
    private MainActivity activity;

    public Job2(int jobSize, MainActivity activity) {
        this.jobSize = jobSize;
        this.activity = activity;
    }

    public void doJob() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.displayOutput("Starting job with "+jobSize+" steps.");
            }
        });


        Random random = new Random();
        for (int i = 1; i <= jobSize; i++) {
            final String step = String.valueOf(i);
            try {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.displayOutput("Running step "+step+" of "+jobSize+".");
                    }
                });
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.displayOutput("Finished all "+jobSize+" steps in the job.");
            }
        });

      }

    @Override
    public void run() {
        doJob();
    }
}
