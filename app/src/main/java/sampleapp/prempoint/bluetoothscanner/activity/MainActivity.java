package sampleapp.prempoint.bluetoothscanner.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import sampleapp.prempoint.bluetoothscanner.R;
import sampleapp.prempoint.bluetoothscanner.bluetooth.BLEService;

public class MainActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.fragment);

        Button clickButton = (Button) findViewById(R.id.btnStartScan);

        context = this.getApplicationContext();

        clickButton.setOnClickListener( new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Log.v("RUN", "--");

                //Intent i = new Intent(getApplicationContext(), ScannerService.class);
                Intent i = new Intent(getApplicationContext(), BLEService.class);
                i.putExtra("scanBle", true);

                // i.putExtra("scanWifi", false);
                startService(i);
            }

        });
    }
}

