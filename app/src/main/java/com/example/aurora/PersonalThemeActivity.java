package com.example.aurora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PersonalThemeActivity extends AppCompatActivity {

    private ImageView spotifyIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_theme);
        initalizeWidgets();
        setFM();
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

    //To be implemented
    void startSpotify(){
        spotifyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent spotifyIntent = new Intent(PersonalThemeActivity.this, ForestThemeActivity.class);
                // startActivity(spotifyIntent);
            }
        });
    }

}
