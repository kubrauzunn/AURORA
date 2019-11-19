package com.example.aurora;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class OceanThemeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocean_theme);
        setFM();
    }

    //helper methods inflating fragment
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
