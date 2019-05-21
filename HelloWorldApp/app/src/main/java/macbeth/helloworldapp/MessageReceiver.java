package macbeth.helloworldapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MessageReceiver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_receiver);
        Intent intent = getIntent();
        TextView tv = findViewById(R.id.rcvd_message);
        tv.setText(intent.getStringExtra("message"));
    }
}
