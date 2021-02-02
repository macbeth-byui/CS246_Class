package macbeth.sharedprefexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        load(); // Just starting so we need to load

        Log.d("sptest","onCreate()");
    }

    // I could have done a load in onRestart but this is a simple
    // app and there are not other activities that may have changed
    // my shared preferences.

    @Override
    protected void onStop() {
        super.onStop();
        save(); // Save because I may be leaving the app forever (maybe not, but saving won't hurt)
                // To trigger onStop, just leave the app and do something else.
                // To prove it works, exit the app fully and then start it back up again and the name will be restored

        Log.d("sptest","onStop()");
    }

    /**
     * Save to data to shared preferences.
     */
    private void save() {
        String nameValue = etName.getText().toString();
        SharedPreferences sp = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Name", nameValue);
        editor.putInt("Age", 44);
        editor.putString("Title", "Sr Eng");
        editor.commit();  // .commit() is immediate; .apply() is in the background
        Log.d("sptest","Saved Name: "+nameValue);
    }

    /**
     * Load data from shared preferences
     */
    private void load() {
        String nameValue = "";
        SharedPreferences sp = getSharedPreferences("Data", Context.MODE_PRIVATE);
        nameValue = sp.getString("Name", "");
        sp.getInt("Age", 0);
        sp.getString("Title","CEO");
        etName.setText(nameValue);
        Log.d("sptest","Loaded Name: "+nameValue);
    }










}
