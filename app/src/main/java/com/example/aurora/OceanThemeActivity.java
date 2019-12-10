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



public class OceanThemeActivity extends BluetoothConnection {
    private ImageView play_pause;
    private AnimatedVectorDrawable pause;
    private AnimatedVectorDrawable play;
    private boolean tick = true;
    private static MediaPlayer oceanMediaPlayer;
    private static final String TAG = "OCEAN THEME";
    private SeekBar soundSeekBar;
    private AudioManager audioManager;
    int volume;
    private SeekBar brightnessSeekBar;

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

                    (new Thread(new workerThread("1"))).start(); //THIS WILL START THE OCEAN LIGHTS
                    Log.d(TAG, "MESSAGE START FROM OCEAN THEME");

                } else {
                    oceanMediaPlayer.pause();
                    animate(null);
                    (new Thread(new workerThread("2"))).start(); //THIS WILL STOP THE OCEAN LIGHTS
                    Log.d(TAG, "MESSAGE STOP FROM OCEAN THEME");
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

    void initVolControl() {

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        soundSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        soundSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

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
                //code to come
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //come to come
            }
        });

    }

    /**
     * Set up fragment managers
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
