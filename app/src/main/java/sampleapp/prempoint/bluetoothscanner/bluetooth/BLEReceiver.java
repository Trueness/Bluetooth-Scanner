package sampleapp.prempoint.bluetoothscanner.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Jacklyn on 4/4/2017.
 */

public class BLEReceiver extends BroadcastReceiver {

   // public void StartBLEDiscovery() {
      //  final BroadcastReceiver mReceiver = new BroadcastReceiver() {
           // @Override
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
        }
       // IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //getApplication().registerReceiver(mReceiver, filter);









