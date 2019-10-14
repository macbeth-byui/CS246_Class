package macbeth.helloworldapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sayHello(View view) {
        EditText et = findViewById(R.id.et_name);
        Intent intent = new Intent(this, HelloActivity.class);
        intent.putExtra("NAME", et.getText().toString());
        startActivity(intent);
    }

}
