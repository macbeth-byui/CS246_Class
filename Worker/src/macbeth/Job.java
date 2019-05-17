package macbeth;

import java.util.Random;

public class Job extends Thread{

    private int jobSize;

    public Job(int jobSize) {
        this.jobSize = jobSize;
    }

    public void doJob() {
        Random random = new Random();
        for (int i=0; i<jobSize; i++) {
            System.out.println("Performing step " + i + " of " + jobSize);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        doJob();
    }

    public static void main(String[] args) {
        Job job1 = new Job(20);
        Job job2 = new Job(30);
        Job job3 = new Job(40);
        job1.start();
        job2.start();
        job3.start();
    }
}
