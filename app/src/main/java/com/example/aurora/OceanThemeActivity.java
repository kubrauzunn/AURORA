package com.example.aurora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OceanThemeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocean_theme);

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
