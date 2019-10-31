package com.example.aurora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //GUI variables on start interface

    private ImageView personalPeacePlayBtn;
    private ImageView forestThemePlayBtn;
    private ImageView oceanThemePlayBtn;

    //theme titles
    private TextView theme1;
    private TextView theme2;
    private TextView theme3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //play buttons
        initializePlaybuttons();

        //initialize theme titles
        initializeThemeTitles();

        //starting themes
        // starting PERSONAL theme by clicking on button or title
        personalPeacePlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personalThemeIntent = new Intent(MainActivity.this, PersonalThemeActivity.class );
                startActivity(personalThemeIntent);
            }

        });
        theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theme1Intent = new Intent(MainActivity.this, PersonalThemeActivity.class);
                startActivity(theme1Intent);
            }

        });

        //starting FOREST theme by clicking on button or title
        forestThemePlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forestThemeIntent = new Intent(MainActivity.this, ForestThemeActivity.class );
                startActivity(forestThemeIntent);
            }

        });
        theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forestThemeIntent = new Intent(MainActivity.this, ForestThemeActivity.class);
                startActivity(forestThemeIntent);
            }
        });

        //starting OCEAN theme by clicking on button or title
        oceanThemePlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oceanThemeIntent = new Intent(MainActivity.this, OceanThemeActivity.class );
                startActivity(oceanThemeIntent);
            }

        });
        theme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oceanThemeIntent = new Intent(MainActivity.this, OceanThemeActivity.class);
                startActivity(oceanThemeIntent);
            }
        });
    }

    // helper methods to wire up GUI widgets
    // wire up playbuttons
    void initializePlaybuttons() {
        personalPeacePlayBtn = findViewById(R.id.play_personal_theme_icon);
        forestThemePlayBtn = findViewById(R.id.play_forest_theme_icon2);
        oceanThemePlayBtn = findViewById(R.id.play_ocean_them_icon3);
    }
    //wire up themes titles
    void initializeThemeTitles(){
        theme1 = findViewById(R.id.theme1_title);
        theme2 = findViewById(R.id.theme2_title);
        theme3 = findViewById(R.id.theme3_title);
    }

    //SPOTIFY connecting code here, needs to be in the MainActivity
    @Override
    protected void onStart() {
        super.onStart();
        // We will start writing our code here.
    }

    private void connected() {
        // Then we will write some more code here.
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Aaand we will finish off here.
    }
}
