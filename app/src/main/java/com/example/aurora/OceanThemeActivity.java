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

/**
 * Licence for including the color picker library:
 *
 * Copyright 2012 Lars Werkman
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */



public class OceanThemeActivity extends BluetoothConnection {
    private ImageView play_pause;
    private AnimatedVectorDrawable pause;
    private AnimatedVectorDrawable play;
    private boolean tick = true;
    private static MediaPlayer oceanMediaPlayer;
    private static final String TAG = "OCEAN THEME";
    private SeekBar soundSeekBar;
    private AudioManager audioManager;
    private SeekBar brightnessSeekBar;
    private static final String STOP_PLAYING_OCEAN_THEME = "2";
    private static final String START_PLAYING_OCEAN_THEME = "1";
    private static final int PROGRESS_INT_VALUE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocean_theme);
        setUpPlayButton();
        setMediaPlayer();
        setFM();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setSoundSeekBar();
        trackSoundBarProgress();
        initVolControl();
        setBrightnessBar();
        trackBrightnessBarProgress();

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!oceanMediaPlayer.isPlaying()) {
                    oceanMediaPlayer.start();
                    animate(null);
                    (new Thread(new workerThread(START_PLAYING_OCEAN_THEME))).start(); //THIS WILL START THE OCEAN LIGHTS
                    Log.d(TAG, "MESSAGE START FROM OCEAN THEME");
                }
                else {
                    (new Thread(new workerThread(STOP_PLAYING_OCEAN_THEME))).start(); //THIS WILL STOP THE OCEAN LIGHTS
                    Log.d(TAG, "MESSAGE STOP FROM OCEAN THEME");
                    oceanMediaPlayer.pause();
                    animate(null);
                }
            }
        });
    }

    /**
     * Sets up the media player for the ocean theme and play or pauses music
     */
    void setMediaPlayer(){
        Log.d(TAG,"MEDIA PLAYER STARTED");
        oceanMediaPlayer = MediaPlayer.create(this, R.raw.ocean_sounds);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (oceanMediaPlayer != null) {
            oceanMediaPlayer.pause();
            if (isFinishing()) {
                oceanMediaPlayer.stop();
                oceanMediaPlayer.release();
            }
        }
        (new Thread(new workerThread(STOP_PLAYING_OCEAN_THEME))).start(); //THIS WILL STOP THE OCEAN LIGHTS
        Log.d(TAG, "MESSAGE STOP FROM OCEAN THEME");
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
     * Sets up the control of the media player
     */

    void initVolControl() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        soundSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        soundSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    /**
     * helper method to wire up UI widgets
     */
    void setSoundSeekBar() {
        soundSeekBar = findViewById(R.id.volumeSeekBar);
    }

    void setBrightnessBar() {
        brightnessSeekBar = findViewById(R.id.brightnessSeekBar);
    }

    /**
     * method to track progress on the brightness bar which allows the user to increase or decrease the lamps brightness
     */
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
     * Code for the brightness bar which allows the user to increase or decrease the lamps brightness
     */
    void trackBrightnessBarProgress(){
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progress = progress + PROGRESS_INT_VALUE;
                StringBuilder sb = new StringBuilder();
                sb.append(" " + progress );
                String s = sb.toString();

                (new Thread(new workerThread( s ))).start();
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
     * Set up fragment manager
     */
    void setFM(){
        Log.d(TAG,"FRAGMENTS SET UP");
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentBlob = fm.findFragmentById(R.id.fragment_blob);
        if (fragmentBlob == null) {
            fragmentBlob = new BlobFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_blob, fragmentBlob)
                    .commit();
        }
    }
}
