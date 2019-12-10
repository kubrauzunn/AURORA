package com.example.aurora;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ForestThemeActivity extends BluetoothConnection {
    private AnimatedVectorDrawable pause;
    private AnimatedVectorDrawable play;
    private ImageView pp_button;
    private boolean tick = true;
    private static MediaPlayer forestMediaPlayer;
    private static final String TAG = "FOREST THEME";
    private SeekBar soundSeekBar;
    AudioManager audioManager;
    int volume;
    private SeekBar brightnessSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forest_theme);
        setUpPlayButton();
        setMediaPlayer();
        setFM();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setSoundSeekBar();
        trackSoundBarProgress();
        initVolControl();
        setBrightnessBar();
        trackBrightnessBarProgress();

        pp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!forestMediaPlayer.isPlaying()) {
                    forestMediaPlayer.start();
                    animate(null);
                    (new Thread(new workerThread("3"))).start(); //THIS WILL START THE FOREST LIGHTS
                    Log.d(TAG, "MESSAGE START FROM FOREST THEME");

                } else {
                    forestMediaPlayer.pause();
                    animate(null);
                    (new Thread(new workerThread("4"))).start(); //THIS WILL STOP THE FOREST LIGHTS
                    Log.d(TAG, "MESSAGE STOP FROM FOREST THEME");
                }
            }
        });

    }


    /**
     *
     * Methods related to the playing of music and play buttons
     *
     **/

    void setUpPlayButton(){
        pp_button = findViewById(R.id.pause_play_b_forest);
        pp_button.bringToFront();
        pause = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_pause_play_button, null);
        play = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_play_pause_button, null);
    }


    /**
     * Sets up the media player for the ocean theme and play or pauses music
     */
    void setMediaPlayer(){
        Log.d(TAG,"MEDIA PLAYER STARTED");
        forestMediaPlayer = MediaPlayer.create(this, R.raw.forest_sounds);
    }

    public void animate(View view) {
        AnimatedVectorDrawable drawable = tick ? play : pause ;
        pp_button.setImageDrawable(drawable);
        drawable.start();
        tick = !tick;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (forestMediaPlayer != null) {
            forestMediaPlayer.pause();
            if (isFinishing()) {
                forestMediaPlayer.stop();
                forestMediaPlayer.release();
            }
        }
    }

    void initVolControl() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        soundSeekBar.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        soundSeekBar.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    void setSoundSeekBar() {
        soundSeekBar = findViewById(R.id.volumeSeekBar);
    }
    void setBrightnessBar() {
        brightnessSeekBar = findViewById(R.id.brightnessSeekBar);
    }

    void trackSoundBarProgress() {
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                volume = progress;
                //send volume
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //code to come
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Volume: " + Integer.toString(volume), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Code for the brightness bar which allows the user to increase or decrease the lamps brightness
     */

    void trackBrightnessBarProgress(){
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                StringBuilder sb = new StringBuilder();
                sb.append(" " + progress );
                String s = sb.toString();
                System.out.println("b" + s);

                (new Thread(new workerThread("brightness send" + s ))).start(); //THIS WILL START THE OCEAN LIGHTS
                Log.d(TAG, "MESSAGE START FROM OCEAN THEME");

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
     * Methods related to the management of fragments
     **/

    void setFM(){
        FragmentManager fm = getSupportFragmentManager();
       /* Fragment fragmentTheme = fm.findFragmentById(R.id.fragment_theme);
        if (fragmentTheme == null) {
            fragmentTheme = new ThemeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_theme, fragmentTheme)
                    .commit();
        }*/

        Fragment fragmentBlob = fm.findFragmentById(R.id.blob_fragment);
        if (fragmentBlob == null) {
            fragmentBlob = new BlobFragment();
            fm.beginTransaction()
                    .add(R.id.blob_fragment, fragmentBlob)
                    .commit();
        }
    }

}
