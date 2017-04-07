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
package sampleapp.prempoint.bluetoothscanner.fragment;


/*
*Import classes
 */
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import sampleapp.prempoint.bluetoothscanner.R;
import sampleapp.prempoint.bluetoothscanner.bluetooth.BLEService;
import sampleapp.prempoint.bluetoothscanner.device.BLEDeviceContainer;
import sampleapp.prempoint.bluetoothscanner.device.BLEDeviceSummary;


/*
*Class name BLEReceiverFragment.java
 */
@SuppressWarnings("ClassWithoutLogger")
public class BLEReceiverFragment extends Fragment implements View.OnClickListener{

    /*
        * Constructor objects(variables)
        */
    private View view;
    private ListView list_Results;
    private Button scanButton;

    private BluetoothAdapter bluetoothAdapter;
    private BLEDeviceContainer bleDeviceAdapter;
    private final int deviceScanTime= 15000;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            BLEDeviceSummary bleDeviceSummary = (BLEDeviceSummary) bundle.get("device");
            bleDeviceAdapter.addDevice(bleDeviceSummary);
            bleDeviceAdapter.notifyDataSetChanged();
            Log.e("broadcast", "received: " + bundle);
        }
    };


    public BLEReceiverFragment() {


    }

    public static BLEReceiverFragment newInstance() {
        return new BLEReceiverFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /*
    *The onResume( ) method is called when the user clicks the Start Scan button to start the activity.
     */
    @Override
    public void onResume() {
        super.onResume();

        bleDeviceAdapter = new BLEDeviceContainer((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        list_Results.setAdapter( bleDeviceAdapter);

        getActivity().registerReceiver(receiver, new IntentFilter(BLEService.NOTIFICATION));
    }


    /*
    *The  onPause() is called when the user clicks the Start Scan button to resume the activity.
     */
    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    /**
     * The Fragment's User Interface
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment, container, false);
        list_Results = (ListView) view.findViewById(R.id.list_scanning_results);
        scanButton = (Button) view.findViewById(R.id.btn_start_scan);
        scanButton.setOnClickListener(this);

        final BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter= bluetoothManager.getAdapter();

        return view;
    }

    @Override
    /*
    *The  onClick(View v)callback to invoked when a view is clicked by the Start Scan button.
     */
    public void onClick(View v) {

       /*
        *Determine if bluetooth is enable or not
         */
        determineBLeEnablement();

        bleDeviceAdapter.initiateList();
        bleDeviceAdapter.notifyDataSetChanged();


        final Intent bluetoothIntent = new Intent(getActivity(), BLEService.class);
        getActivity().startService(bluetoothIntent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().stopService(bluetoothIntent);
                scanButton.setBackgroundResource(R.drawable.btn_start_scan_attributes);
                scanButton.setText(R.string.btn_start_scan);
                scanButton.setTextColor(ContextCompat.getColor(getContext(), R.color.button_start_scan_text_color));
                scanButton.setClickable(true);
            }
        },deviceScanTime);

/*
*The setBackgroundResource from the drawable file will change the button scanning button is clicked
 */
        scanButton.setBackgroundResource(R.drawable.btn_scanning_attributes);
        scanButton.setText(R.string.btn_scanning);
        scanButton.setTextColor(ContextCompat.getColor(getContext(), R.color.button_scanning_color));
        scanButton.setClickable(false);
    }

      /*
        *This method enables to turn on the bluetooth when the Start Scan button is clicked
        * isEnable method returns true if adapter is enabled
        *
         */
      private void determineBLeEnablement() {
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(enableBtIntent);
        }
    }



}
