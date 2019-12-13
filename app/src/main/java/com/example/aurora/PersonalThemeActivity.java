package com.example.aurora;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.larswerkman.holocolorpicker.ColorPicker;


public class PersonalThemeActivity extends BluetoothConnection implements ColorPicker.OnColorChangedListener {

    private ImageView spotifyIcon;
    private SeekBar soundSeekBar;
    private AudioManager audioManager;
    private SeekBar brightnessSeekBar;
    private static final String STOP_PLAYING_PERSONAL_THEME = "2";
    private static final int PROGRESS_INT_VALUE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_theme);
        initWidgets();
        startSpotify();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setSoundSeekBar();
        trackSoundBarProgress();
        initVolControl();
        getColorPicker();
        setBrightnessBar();
        trackBrightnessBarProgress();

    }

    @Override
    protected void onPause() {
        super.onPause();
        (new Thread(new workerThread(STOP_PLAYING_PERSONAL_THEME))).start(); //THIS WILL STOP THE OCEAN LIGHTS
        System.out.println("MESSAGE STOP FROM OCEAN THEME");
    }

    /**
     * Sets up color picker and related methods to allow users to choose a color
     */
    public void getColorPicker() {
        final ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
        findViewById(R.id.picker);
        picker.getColor();
        //To set the old selected color u can do it like this
        picker.setOldCenterColor(picker.getColor());
        //adds listener to the colorpicker which is implemented in the activity
        picker.setOnColorChangedListener(this);

        //to turn of showing the old color
        picker.setShowOldCenterColor(false);

        //to generate RGB values of the chosen color and send it off to the raspberry pi of the aurora lamp
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                int green = Color.green(color);
                int red = Color.red(color);
                int blue = Color.blue(color);
                //sending rgb value as a string in a new thread
                StringBuilder sb = new StringBuilder();
                sb.append( red + "," + green + "," + blue );
                String s = sb.toString();
                (new Thread (new workerThread(s))).start();
            }
        });
    }

    @Override
    public void onColorChanged(int color) {
     //needed to be overwritten
    }

    /**
     * helper methods for setting up the layout widgets
     */
    void initWidgets(){
        spotifyIcon = findViewById(R.id.spotify_icon);
    }

    void setBrightnessBar() {
        brightnessSeekBar = findViewById(R.id.brightnessSeekBar);
    }

    /**
     * Code for the brightness bar which allows the user to increase or decrease the lamps brightness
     */
    void trackBrightnessBarProgress() {
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progress = progress + PROGRESS_INT_VALUE; //in order to avoid confusion with theme numbers which are sent to the raspberry pi
                StringBuilder sb = new StringBuilder();
                sb.append(" " + progress );
                String s = sb.toString();
                (new Thread(new workerThread(s ))).start();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    /**
     * Code for the volume bar which allows the user to increase or decrease the volume of the sound
     */

    void initVolControl() {

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        soundSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        soundSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

    }

    void setSoundSeekBar() {
        soundSeekBar = findViewById(R.id.volumeSeekBar);
    }

    void trackSoundBarProgress() {
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    /**
     * The following section contains code which will be implemented later on in relation to
     * the integration of Spotify
     */

    void startSpotify() {
        spotifyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent spotify_intent = new Intent(Intent.ACTION_MAIN);
                spotify_intent.setComponent(new ComponentName("com.spotify.music", "com.spotify.music.MainActivity"));
                if (spotify_intent != null) {
                    startActivity(spotify_intent);
                } else {
                    Toast.makeText(PersonalThemeActivity.this, "You need to have Spotify installed on the phone", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
