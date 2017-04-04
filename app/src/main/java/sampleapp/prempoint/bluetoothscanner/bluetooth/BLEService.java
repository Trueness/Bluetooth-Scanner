package sampleapp.prempoint.bluetoothscanner.bluetooth;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Description:
 *
 * @author
 */

//TODO: 1. Use a broadcast receiver to returm the results (Ref PP 237 APC)
//TODO:    1.a Also Ref: https://developer.android.com/guide/topics/connectivity/bluetooth-le.html
//TODO:      https://android.googlesource.com/platform/development/+/512ea9b6f8cc75ec74a7ab8d1c38dec201667f1e/samples/BluetoothHDP/src/com/example/bluetooth/health?autodive=0/
//TODO: 2. Expose the Following Parameters (Maybe as a Model Class):
//TODO     a. Device’s bluetooth name
//TODO     b. RSSI signal strength
//TODO     c. Hex representation of the scan record

public class BLEService extends Service {

    private Context mCtx;
    private BluetoothAdapter bluetoothAdapter;
    public BluetoothManager bluetoothManager;
    private final static int REQUEST_ENABLE_BT = 1;

    Activity mActivity;

    /**
     * Constructor
     */
    /*
    public BLEService (Context context,Activity activity){
        activity = mActivity;
        mCtx =context;
        initializeBLeAdapter();
        determineBLeEnablement();

    }
    */
    @Override
    public void onCreate() {

        Log.v("BLE Service Starting", "--");
        super.onCreate();

        //activity = mActivity;
        // mCtx =context;

        mCtx = this.getApplicationContext();

        initializeBLeAdapter();
        determineBLeEnablement();
        StartBLEDiscovery();

    }

    /*
     * This method initializes the BLE Adapter
     */
    public void initializeBLeAdapter() {
        // Initializes Bluetooth adapter.
        bluetoothManager = (BluetoothManager) mCtx.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    public void determineBLeEnablement() {
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //  mActivity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), "Please Enable Bluetooth", Toast.LENGTH_LONG).show();
        }
    }
 public void StartBLEDiscovery() {
     final BroadcastReceiver mReceiver = new BroadcastReceiver() {
         @Override
         public void onReceive(Context context, Intent intent) {
             String action = intent.getAction();

             // When discovery finds a device
             if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                 // Get the BluetoothDevice object from the intent
                // BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // if(intent.hasExtra("scanBle")) {
                     BluetoothDevice device = intent.getParcelableExtra("scanBle");
               //  }
                 Log.v("BlueTooth Testing", device.getName() + "\n"
                         + device.getAddress());
             }
         }
     };
     IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
     getApplication().registerReceiver(mReceiver, filter);
 }



   // bluetoothAdapter.startDiscovery();


        //Intent receivers below
    protected void onHandleIntent(Intent intent) {

        boolean scanWifi = intent.getBooleanExtra("scanBle", true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        boolean scanWifi = intent.getBooleanExtra("scanBle", true);
        return null;
    }
}

