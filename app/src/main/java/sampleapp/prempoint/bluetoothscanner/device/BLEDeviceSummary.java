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
 * Import classes
 */
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

/*
 *  BLEDeviceSummary.java class
 */
public class BLEDeviceSummary implements Parcelable{

    private BluetoothDevice name;
    private String rssi;
    private String record;

    private BLEDeviceSummary(Parcel in) {

        name = in.readParcelable(BluetoothDevice.class.getClassLoader());
        rssi = in.readString();
        record = in.readString();
    }

    public BLEDeviceSummary() {
    }

    public static final Creator<BLEDeviceSummary> CREATOR = new Creator<BLEDeviceSummary>() {
        @Override
        public BLEDeviceSummary createFromParcel(Parcel in) {
            return new BLEDeviceSummary(in);
        }

        @Override
        public BLEDeviceSummary[] newArray(int size) {
            return new BLEDeviceSummary[size];
        }
    };


/*
 *The getters and setters
 */

    public BluetoothDevice getName() {
        return name;
    }

    public void setName(BluetoothDevice name) {
        this.name = name;
        //this.name = name.getName();
    }

    String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
    *The writeToParcel object return value from BLEDeviceSummary
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeParcelable(name, flags);
        //dest.writeString(name);
        dest.writeString(rssi);
        dest.writeString(record);
    }



}
