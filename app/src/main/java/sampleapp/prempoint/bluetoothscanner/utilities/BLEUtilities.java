/*
Created by Jacklyn Brown
Application title: Bluetooth-Scanner
Purpose: The BLEUtility to provide BLE tested utilities.
 */


/*
*Package name
 */
package sampleapp.prempoint.bluetoothscanner.utilities;


/*
*Class name BLEUtilities.java
 */
@SuppressWarnings("ClassWithoutLogger")
public class BLEUtilities {

      /*
       * The scan results is converted into hexadecimals.
      */

    public static String convertBytesToHexString(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder(64);
        for(byte b : bytes){
            stringBuilder.append(String.format("%02x", b&0xff));
        }
        return stringBuilder.toString();
    }
}
