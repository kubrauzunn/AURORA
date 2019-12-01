package com.example.aurora;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;



public class OceanThemeActivity extends AppCompatActivity {
    //Music player variables
    private ImageView play_pause;
    private AnimatedVectorDrawable pause;
    private AnimatedVectorDrawable play;
    private boolean tick = true;
    private static MediaPlayer oceanMediaPlayer;
    private static final String TAG = "OCEAN THEME";
    public BluetoothSocket socket;
    public BluetoothDevice connect_device = null;
    private byte[] start = new byte[1];
    private byte[] stop = new byte[2];
    OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocean_theme);
        setUpPlayButton();
        setMediaPlayer();
        setFM();


        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        final class workerThread implements Runnable {
            private String btMsg;
            public workerThread(String msg) {
                btMsg = msg;
            }

            public void run(){
                send(btMsg);
            }
        }

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!oceanMediaPlayer.isPlaying()){
                    oceanMediaPlayer.start();
                    animate(null);
                    (new Thread(new workerThread("1"))).start();
                    Log.d(TAG,"MESSAGE START FROM OCEAN THEME");

                } else {
                    oceanMediaPlayer.pause();
                    animate(null);
                    (new Thread(new workerThread("0"))).start();
                    Log.d(TAG,"MESSAGE STOP FROM OCEAN THEME");
                }
            }
        });


        Log.d(TAG,"BLUETOOTH ADAPTER SETUP STARTED");
        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals("raspberrypi"))
                {
                    Log.d("AURORA",device.getName());
                    Log.d(TAG,"DEVICE HAS BEEN RETRIEVED");
                    connect_device = device;
                    break;
                }
            }
        }
    }


    public void send(String msg) {

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        //UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee"); //Standard SerialPortService ID
        try {

            socket = connect_device.createRfcommSocketToServiceRecord(uuid);
            if (!socket.isConnected()) {
                socket.connect();
            }

            outputStream = socket.getOutputStream();
            outputStream.write(msg.getBytes());

        } catch (IOException e) {
            Log.e(TAG, "Error creating socket");
            e.printStackTrace();
        }

        try {
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
        }
    }


    /**
     * Sets up the media player for the ocean theme and play or pauses music
     */
    void setMediaPlayer(){
        Log.d(TAG,"MEDIA PLAYER STARTED");
        oceanMediaPlayer = MediaPlayer.create(this, R.raw.ocean_sounds);
    }

    /**
     * Animate the pause and play vectors inside the play/pause button
     */

    public void animate(View view) {
        Log.d(TAG,"ANIMATE STARTED");
        AnimatedVectorDrawable drawable = tick ? play : pause;
        play_pause.setImageDrawable(drawable);
        drawable.start();
        tick = !tick;
    }


    /**
     * Sets up the buttons for the media player
     */

    void setUpPlayButton(){
        Log.d(TAG,"PLAY BUTTONS SET UP");
        play_pause = findViewById(R.id.pause_play_b_ocean);
        play_pause.bringToFront();
        pause = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_pause_play_button, null);
        play = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_play_pause_button, null);
    }


    /**
     * Set up fragment managers
     */
    void setFM(){
        Log.d(TAG,"FRAGMENTS SET UP");
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_theme);
        if (fragment == null) {
            fragment = new ThemeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_theme, fragment)
                    .commit();
        }
        Fragment fragmentBlob = fm.findFragmentById(R.id.fragment_blob);
        if (fragmentBlob == null) {
            fragmentBlob = new BlobFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_blob, fragmentBlob)
                    .commit();
        }
    }


}
