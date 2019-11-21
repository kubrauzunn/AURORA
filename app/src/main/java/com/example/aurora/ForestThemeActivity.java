package com.example.aurora;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

public class ForestThemeActivity extends AppCompatActivity {

    /*******Variable will be used later on**********/
    private AnimatedVectorDrawableCompat pause;
    private AnimatedVectorDrawableCompat play;
    /*********************************************/

    private ImageView pp_button;
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
                    pp_button.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
                    //animate(null);
                } else {
                    forestMediaPlayer.pause();
                    pp_button.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                    //animate(null);
                }
            }
        });
    }



    void setUpPlayButton(){
        pp_button = findViewById(R.id.play_pause_btt);
        pp_button.bringToFront();
        //pause = ResourcesCompat.getDrawable(getResources(), R.drawable.avd_pause_play_button, null);
        //play =  ResourcesCompat.getDrawable(getResources(), R.drawable.avd_play_pause_button, null);
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
    //fragment managing
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


    /**
     * The following section contains code which is not usable at the moment due to
     * backwards compatibility issues. We are working on this.
     **/

    /*
    public void animate(View view) {
        AnimatedVectorDrawableCompat drawable = tick ? pause : play;
        pp_button.setImageDrawable(drawable);
        drawable.start();
        tick = !tick;
    }
    */


}
