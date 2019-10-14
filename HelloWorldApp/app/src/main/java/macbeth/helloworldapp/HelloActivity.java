package macbeth.helloworldapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        Intent intent = getIntent();
        TextView tv = findViewById(R.id.tv_name);
        tv.setText("Hello, "+intent.getStringExtra("NAME"));
    }
}
