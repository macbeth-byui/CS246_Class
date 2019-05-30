package macbeth.sharedprefexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        int value = sp.getInt("random", -1);
        Log.d("sptest","Read Random: "+value);

    }

    public void save(View view) {
        Random random = new Random();
        int value = random.nextInt(100);

        SharedPreferences sp = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("random", value);
        editor.commit();
        Log.d("sptest","Wrote Random: "+value);

    }
}
