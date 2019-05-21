package macbeth.helloworldapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sayHello(View view) {
        TextView tv = findViewById(R.id.message);
        tv.setText("Hello");
        Intent intent = new Intent(this, MessageReceiver.class);
        intent.putExtra("message","Hello World!");
        startActivity(intent);

    }
}
