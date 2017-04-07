/*
Created by Jacklyn Brown
Application title: Bluetooth-Scanner
Purpose: This application is a bluetooth scanner that picks up signal strength and displays the scan results.
The app displays a title, a rounded corner button when click changes the background color
and text color.


Create a single activity app with a fragment for the UI
Show a single button at the top right, background color #00abfb, rounded corners, that initially
says “Start Scan” in white letters. Tapping the button will do the following:
Change button background to #c5d772, text to “Scanning”, text color #ececed, and make it not tappable
Start a BLE scan (please implement BLE scanner in a service that returns results via local broadcast)
Results should be shown in a ListView in the fragment showing: Device’s bluetooth name
RSSI signal strength Hex representation of the scan record
After 15 seconds, the scan will stop and the button will be changed back to “Start Scan” and enabled
Tapping again will clear the ListView results and start over.
 */


/*
*Package name
 */
package sampleapp.prempoint.bluetoothscanner.activity;


/*
*Import classes
 */
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import sampleapp.prempoint.bluetoothscanner.R;
import sampleapp.prempoint.bluetoothscanner.fragment.BLEReceiverFragment;

/*
*Class name MainActivity.java
 */
public class MainActivity extends AppCompatActivity {

    /*
*Constructor objects(variables)
 */
    Context context;
    private BluetoothAdapter bluetoothAdapter;
    public BluetoothManager bluetoothManager;

    @Override
    /*
    *Method that creates
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        *setContentView command displays the activity_main layout file that is located in resource layout folder
         */
        setContentView(R.layout.activity_main);

        /*
        * sharing memory for a new object, creates and commits a fragment transaction
         */
        BLEReceiverFragment blescanFragment = BLEReceiverFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, blescanFragment).commit();
    }
}

