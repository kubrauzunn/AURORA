package com.example.aurora;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class OceanThemeActivity extends AppCompatActivity {
    private ImageView play_pause;
    private AnimatedVectorDrawable pause;
    private AnimatedVectorDrawable play;
    private boolean tick = true;
    private static MediaPlayer oceanMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocean_theme);
        setUpPlayButton();
        setMediaPlayer();
        setFM();
    }

    /**
     * Sets up the media player for the ocean theme and play or pauses music
     */
    void setMediaPlayer(){
        oceanMediaPlayer = MediaPlayer.create(this, R.raw.ocean_sounds);
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!oceanMediaPlayer.isPlaying()){
                    oceanMediaPlayer.start();
                    animate(null);
               } else {
                    oceanMediaPlayer.pause();
                    animate(null);
                }
            }
        });

    }



    public void animate(View view) {
        AnimatedVectorDrawable drawable = tick ? play : pause;
        play_pause.setImageDrawable(drawable);
        drawable.start();
        tick = !tick;
    }


    /**
     * Sets up the buttons for the media player
     */

    void setUpPlayButton(){
        play_pause = findViewById(R.id.pause_play_b_ocean);
        play_pause.bringToFront();
        pause = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_pause_play_button, null);
        play = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_play_pause_button, null);
    }


    /**
     * When the user leaves the activity the music stops automatically.
     * This keeps the user focused on the task at hand when relaxing.
     */
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
     * Set up fragment managers
     */
    void setFM(){
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
