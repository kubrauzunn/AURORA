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

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentTheme = fm.findFragmentById(R.id.fragment_theme);
        if (fragmentTheme == null) {
            fragmentTheme = new ThemeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_theme, fragmentTheme)
                    .commit();
        }

        spotifyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent spotifyIntent = new Intent(PersonalThemeActivity.this, ForestThemeActivity.class);
               // startActivity(spotifyIntent);
            }
        });
    }

    void initalizeWidgets(){
        spotifyIcon = findViewById(R.id.spotify_icon);
    }


}
