package com.example.aurora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Set;

public class PersonalThemeActivity extends AppCompatActivity {

    private ImageView spotifyIcon;
    private static MediaPlayer oceanMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_theme);
        initalizeWidgets();
        setFM();
        setMediaPlayer();
    }

    //helper methods
    void setFM() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentTheme = fm.findFragmentById(R.id.fragment_theme);
        if (fragmentTheme == null) {
            fragmentTheme = new ThemeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_theme, fragmentTheme)
                    .commit();
        }
        Fragment fragmentBlob = fm.findFragmentById(R.id.fragment_color_picker);
        if (fragmentBlob == null) {
            fragmentBlob = new ColorPickerFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_color_picker, fragmentBlob)
                    .commit();
        }
    }

    void initalizeWidgets(){
        spotifyIcon = findViewById(R.id.spotify_icon);
    }

    //for testing bluetooth connection purposes
    void setMediaPlayer() {
        oceanMediaPlayer = MediaPlayer.create(this, R.raw.forest_sounds);
        spotifyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!oceanMediaPlayer.isPlaying()) {
                    oceanMediaPlayer.start();

                } else {
                    oceanMediaPlayer.pause();

                }
            }
        });

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


}
