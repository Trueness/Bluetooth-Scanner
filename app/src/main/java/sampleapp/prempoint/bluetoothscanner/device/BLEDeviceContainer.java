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
 * Package name
 */
package sampleapp.prempoint.bluetoothscanner.device;

/*
 * import classes
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import sampleapp.prempoint.bluetoothscanner.R;

/*
 * Class name BLEDeviceContainer.java
 */

 public class BLEDeviceContainer extends BaseAdapter{

/*
 *The ArrayList returns the BLEDeviceSummary results.
 */
    private ArrayList<BLEDeviceSummary> listDevices;

    private final LayoutInflater inflater;

 /*
  *TextView content
 */
    private TextView bleDeviceName;
    private TextView rSSILevel;
    private TextView hexValue;

    /*
    *Instantiate the BLEDeviceSummary
     */
  public BLEDeviceContainer(LayoutInflater inflater) {
        this.inflater = inflater;
        initiateList();
    }

     public void addDevice(BLEDeviceSummary bleDevice) {
        if(!listDevices.contains(bleDevice)) {
            listDevices.add(bleDevice);
        }
    }


    /*
    * initiate list from BLEDeviceSummary
     */
   public void initiateList() {
       listDevices = new ArrayList<>();
    }

    /*
    *The getCount returns the number of items in the array list from BLEDeviceSummary
     */
    @Override
    public int getCount() {
        return listDevices.size();
    }

    /*
*  The getItem() returns the list devices data object
 */
    @Override
    public Object getItem(int position) {
        return listDevices.get(position);
    }

    /*
    *Get the row id associated with the specified position in the listDevices
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
    *Get a View that displays the data from list_result layout.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.list_results, parent, false);


        BLEDeviceSummary device = listDevices.get(position);
        /*
    *TextView widgets
     */
        bleDeviceName =  (TextView) view.findViewById(R.id.list_results_ble_name);
        rSSILevel =  (TextView) view.findViewById(R.id.list_results_rssi);
        hexValue =  (TextView) view.findViewById(R.id.list_results_hex);

        /*
        *The SetText( ) accepts a string of data for display from the list_results layout
         */
        if(device.getName() !=null) {
            bleDeviceName.setText("Device Name: " + device.getName());
        }else{
            bleDeviceName.setText("Device name was not discovered");
        }

        rSSILevel.setText("RSSI: " + device.getRssi());
        hexValue.setText("Scan Record: " + device.getRecord());

       /*
        * The scanning button returns the list_results from the device
         */
        return view;
    }


}
