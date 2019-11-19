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

public class ForestThemeActivity extends AppCompatActivity {
    private ImageView pp_button;
    private AnimatedVectorDrawable pause;
    private AnimatedVectorDrawable play;
    private boolean tick = true;
    private static MediaPlayer forestMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forest_theme);
        setFM();
        setUpPlayButton();
        setMediaPlayer();
    }


    void setMediaPlayer(){
        forestMediaPlayer = MediaPlayer.create(this, R.raw.forest_sounds);
        pp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!forestMediaPlayer.isPlaying()){
                    forestMediaPlayer.start();
                    animate(null);
                } else {
                    forestMediaPlayer.pause();
                    animate(null);
                }
            }
        });
    }

    public void animate(View view) {
        AnimatedVectorDrawable drawable = tick ? pause : play;
        pp_button.setImageDrawable(drawable);
        drawable.start();
        tick = !tick;
    }

    void setUpPlayButton(){
        pp_button = findViewById(R.id.pause_play_b_forest);
        pp_button.bringToFront();
        pause =  (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_pause_play_button, null);
        play = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_play_pause_button, null);
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

    void setFM(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentTheme = fm.findFragmentById(R.id.fragment_theme);
        if (fragmentTheme == null) {
            fragmentTheme = new ThemeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_theme, fragmentTheme)
                    .commit();
        }

        Fragment fragmentBlob = fm.findFragmentById(R.id.blob_fragment);
        if (fragmentBlob == null) {
            fragmentBlob = new BlobFragment();
            fm.beginTransaction()
                    .add(R.id.blob_fragment, fragmentBlob)
                    .commit();
        }
    }

}
