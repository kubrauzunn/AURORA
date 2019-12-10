package com.example.aurora;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

public class BluetoothConnection extends AppCompatActivity {
    private BluetoothDevice connect_device;
    private static final String TAG = "OCEAN THEME";
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private String btMsg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBTConnection();
    }

    final class workerThread implements Runnable {

        public workerThread(String msg) {
            btMsg = msg;
        }

        public void run() {
            send(btMsg);
        }
    }

    /**
     * THIS METHOD WILL SET UP THE BLUETOOTH ADAPTER AND GET THE CONNECTED DEVICE
     */

    public void setUpBTConnection() {
        Log.d(TAG, "BLUETOOTH ADAPTER SETUP STARTED");
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
            mBluetoothAdapter.enable();
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("aurora")) {
                    Log.d("AURORA", device.getName());
                    //Log.d("AURORA", device);
                    Log.d(TAG, "DEVICE HAS BEEN RETRIEVED");
                    connect_device = device;
                    break;
                }
            }
        }
    }

    /**
     * This method connects the socket of the client with a device server. Thereafter, it writes a message to device.
     *
     * @param msg
     */

    public void send(String msg) {
        OutputStream outputStream;
        BluetoothSocket socket;
        UUID uuid = UUID.fromString("0000111f-0000-1000-8000-00805f9b34fb");
        try {
            socket = (BluetoothSocket) connect_device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(connect_device, 1);
            Log.e(TAG, "Socket has been set");

            if (!socket.isConnected()) {
                mBluetoothAdapter.cancelDiscovery();
                Log.e(TAG, "Discoverability has been cancelled");
                socket.connect();
                Log.e(TAG, "Socket connected");


            }
            outputStream = socket.getOutputStream();
            outputStream.write(msg.getBytes());
            Log.e(TAG, "Message has been written first time");

        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Log.e(TAG, "Error creating socket");
            e.printStackTrace();
        }

        /* try {
            socket.connect();
            Log.e(TAG, "Connected");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            try {
                Log.e(TAG, "trying fallback...");
                socket = (BluetoothSocket) connect_device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(connect_device, 1);
                socket.connect();
                Log.e(TAG, "Socket connected");
                outputStream = socket.getOutputStream();
                Log.e(TAG, "outputStream set");
                outputStream.write(msg.getBytes());
                Log.e(TAG, "Write has been sent");
            } catch (Exception e2) {
                Log.e(TAG, "Couldn't establish Bluetooth connection!");
            }
        }*/

    }
}