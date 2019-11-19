package com.example.aurora;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.appcompat.app.AppCompatActivity;


public class Bluetooth extends AppCompatActivity {
    BluetoothA2dp bluetoothA2dp;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setProfileListener();
        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

    }

    void setProfileListener() {
            BluetoothProfile.ServiceListener profileListener = new BluetoothProfile.ServiceListener() {
            public void onServiceConnected(int profile, BluetoothProfile proxy) {
                if (profile == BluetoothProfile.A2DP) {
                    bluetoothA2dp = (BluetoothA2dp) proxy;
                }
            }

            public void onServiceDisconnected(int profile) {
                if (profile == BluetoothProfile.A2DP) {
                    bluetoothA2dp = null;
                }
            }
        };

        bluetoothAdapter.getProfileProxy(getApplicationContext(), profileListener, BluetoothProfile.A2DP);

    }

    /**
     * Device discovery is a scanning procedure that searches the local area
     * for Bluetooth-enabled devices and requests some information about each one.
     * However, a nearby Bluetooth device responds to a discovery request only if
     * it is currently accepting information requests by being discoverable.
     * If a device is discoverable, it responds to the discovery request by sharing some information,
     * such as the device's name, its class, and its unique MAC address.
     * Using this information, the device that is performing the discovery process can then choose
     * to initiate a connection to the discovered device.
     */

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }

}
