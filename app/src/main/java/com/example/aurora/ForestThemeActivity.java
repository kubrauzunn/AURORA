package com.example.aurora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ForestThemeActivity extends AppCompatActivity {

    private AnimatedVectorDrawable blob;
    private AnimatedVectorDrawable pause;
    private AnimatedVectorDrawable play;
    private ImageView blobView;
    private ImageView pp_button;
    private boolean tick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forest_theme);


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
