package macbeth.processrequestsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button bProcess;
    private ListView lvRequests;
    private TextView tvRequestCount;
    private TextView tvProcessingRequest;

    private RequestServer server;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load components from the layout
        bProcess = findViewById(R.id.b_process);
        lvRequests = findViewById(R.id.list_requests);
        tvRequestCount = findViewById(R.id.tv_request_count);
        tvProcessingRequest = findViewById(R.id.tv_processing_request);

        // Create the Request Server first so we can create an adapter that will connect
        // the list in the Request Server to the ListView in the display

        server = new RequestServer(this);

        // An ArrayAdapter is a very simple adapter that maps a list of Strings to a
        // ListView display
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                server.getAllRequests());
        lvRequests.setAdapter(adapter);

        // Connect the button to the processNextRequest function
        bProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processNextRequest();
            }
        });

        // Initialize the displays
        tvRequestCount.setText("0");
        tvProcessingRequest.setText("None");

        // Start the Request Serve server.start();r Thread in the background
        server.start();
    }

    /**
     * Get the next request from the server and show it on the display
     */
    public void processNextRequest() {
        String request = server.getNextRequest();
        tvProcessingRequest.setText(request);
    }

    /**
     * Update the display to match the state of the server
     */
    public void updateDisplay() {
        adapter.notifyDataSetChanged(); // This will update the ListView adapter
        tvRequestCount.setText(String.valueOf(server.getNumRequests()));
    }
}
