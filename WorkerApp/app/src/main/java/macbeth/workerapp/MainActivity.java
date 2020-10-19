package macbeth.workerapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Job1 will result in an exception
        /*Job1 job1 = new Job1(20, this);
        Job1 job2 = new Job1(30, this);
        Job1 job3 = new Job1(40, this);
        Job1 job4 = new Job1(30, this);
        job1.start();
        job2.start();
        job3.start();
        job4.start();*/

        // Proper way to use Thread with Android
        /*
        Job2 job1 = new Job2(20, this);
        Job2 job2 = new Job2(30, this);
        Job2 job3 = new Job2(40, this);
        Job2 job4 = new Job2(30, this);
        job1.start();
        job2.start();
        job3.start();
        job4.start();*/

        // Another approach using AsyncTask
        Job3 job1 = new Job3(20, this);
        Job3 job2 = new Job3(30, this);
        Job3 job3 = new Job3(40, this);
        Job3 job4 = new Job3(30, this);
        job1.execute();  // AsyncTask uses execute instead of start
        job2.execute();
        job3.execute();
        job4.execute();


    }

    public void displayOutput(String text) {
        TextView tvOutput = findViewById(R.id.output);
        tvOutput.append(text+"\n");
    }
}



