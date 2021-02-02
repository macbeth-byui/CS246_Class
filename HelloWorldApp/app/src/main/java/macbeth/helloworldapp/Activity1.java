package macbeth.helloworldapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
    }

    public void helloButtonPressed(View view) {
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("Message", "Hello, World");
        startActivity(intent);
    }

}
