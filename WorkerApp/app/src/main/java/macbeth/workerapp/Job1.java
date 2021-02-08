package macbeth.workerapp;

import java.util.Random;

public class Job1 extends Thread {

    private int jobSize;
    private MainActivity activity;

    public Job1(int jobSize, MainActivity activity) {
        this.jobSize = jobSize;
        this.activity = activity;
    }

    public void doJob() {
        activity.displayOutput("Starting job with "+jobSize+" steps.");

        Random random = new Random();
        for (int i = 1; i <= jobSize; i++) {
            try {
                activity.displayOutput("Running step "+i+" of "+jobSize+".");
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        activity.displayOutput("Finished all "+jobSize+" steps in the job.");
      }

    @Override
    public void run() {
        doJob();
    }
}
