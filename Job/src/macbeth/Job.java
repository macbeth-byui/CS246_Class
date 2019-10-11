package macbeth;

import java.util.Random;

public class Job implements Runnable {

    private int jobSize;

    public Job(int jobSize) {
        this.jobSize = jobSize;
    }

    public void run() {
        doWork();
    }

    public void doWork() {
        Random random = new Random();
        for (int i=1; i<=jobSize; i++) {
            System.out.println("Working on step " + i + " of " + jobSize );
            try {
                Thread.sleep(random.nextInt(3000));
            }
            catch (InterruptedException ie) {

            }
        }
    }

    public static void main(String []args) {
        Job job1 = new Job(20);
        Job job2 = new Job(30);
        Thread thread1 = new Thread(job1);
        thread1.start();
        new Thread(job2).start();
        //job2.start();
        System.out.println("I'm Here");
    }
}
