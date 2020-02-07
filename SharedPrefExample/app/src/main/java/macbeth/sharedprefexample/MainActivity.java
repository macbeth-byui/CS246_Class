package macbeth.sharedprefexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        load();

        Log.d("sptest","onCreate()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        save();

        Log.d("sptest","onStop()");
    }

    private void save() {
        String nameValue = etName.getText().toString();
        SharedPreferences sp = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Name", nameValue);
        editor.commit();
        Log.d("sptest","Saved Name: "+nameValue);
    }

    private void load() {
        String nameValue = "";
        SharedPreferences sp = getSharedPreferences("Data", Context.MODE_PRIVATE);
        nameValue = sp.getString("Name", "?");
        etName.setText(nameValue);
        Log.d("sptest","Loaded Name: "+nameValue);
    }










}
