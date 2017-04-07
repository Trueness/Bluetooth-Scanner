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

package sampleapp.prempoint.bluetoothscanner.bluetooth;



/*
*Import classes
 */
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import sampleapp.prempoint.bluetoothscanner.device.BLEDeviceSummary;


/*
*Class name BLEService.java
 */
public class BLEService extends Service {

    private BluetoothAdapter mBluetoothAdapter;
    public static final String NOTIFICATION = "sampleapp.prempoint.bluetoothscanner.bluetooth";
    List<BluetoothDevice> mList;    //ArrayList holds unique devices

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mList = new ArrayList<>();

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        /*
        *This method enables to turn on the bluetooth when the Start Scan button is clicked
        * isEnable method returns true if adapter is enabled
        *
         */
       /*
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(enableBtIntent);
        }
        */


        /*
        * The Runnable is an interface that provides the Start Scan button to run.
         */
        new Thread(new Runnable() {

        /*
        *Run() moves the current Thread into the background
         */
            @Override
            public void run() {
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            }
        }).start();
    }


    /*
    * The broadcastUpdate provides the RSSI singal strength and the HEX scan result from the BLEDeviceSummary
     */
    private void broadcastUpdate(BLEDeviceSummary device) {
        final Intent intent = new Intent(NOTIFICATION);
        intent.putExtra("device", device);
        sendBroadcast(intent);
    }
    /*
    * If a new device is detected this method calls broadcastUpdate.
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            if(!mList.contains(device)) {
                mList.add(device);

                BLEDeviceSummary bleDevice = new BLEDeviceSummary();
                bleDevice.setName(device);
                bleDevice.setRssi(String.valueOf(rssi));
                bleDevice.setRecord(bytesToHexString(scanRecord));

                broadcastUpdate(bleDevice);
            }
        }
    };

    /*
    *The scan results is converted into hexadecimals.
     */

    //from: http://javarevisited.blogspot.com/2013/03/convert-and-print-byte-array-to-hex-string-java-example-tutorial.html
    public static String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
            sb.append(String.format("%02x", b&0xff));
        }
        return sb.toString();
    }

    /*
    *This method ends the activity.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }
}
