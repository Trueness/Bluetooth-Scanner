package sampleapp.prempoint.bluetoothscanner.bluetooth;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import sampleapp.prempoint.bluetoothscanner.activity.MainActivity;

/**
 * Description:
 *
 * @author
 */

//TODO: 1. Use a broadcast receiver to returm the results (Ref PP 438 APC)
//TODO: 2. Expose the Following Parameters (Maybe as a Model Class):
//TODO     a. Device’s bluetooth name
//TODO     b. RSSI signal strength
//TODO     c. Hex representation of the scan record

public class BLEService extends Service {

    private  Context mCtx;
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

    }



    /*
     * This method initializes the BLE Adapter
     */
    public void initializeBLeAdapter(){
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

