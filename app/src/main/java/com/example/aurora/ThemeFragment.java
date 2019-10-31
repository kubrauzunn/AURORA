package com.example.aurora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;

public class ThemeFragment extends Fragment {

    private SeekBar brightnessSeekBar;
    private SeekBar soundSeekBar;
    private ImageView bulbBrightnessIcon;
    private ImageView volumeIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //return all views in UI
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_theme, container, false);

        brightnessSeekBar = v.findViewById(R.id.brightnessSeekBar);
        soundSeekBar = v.findViewById(R.id.volumeSeekBar);
        bulbBrightnessIcon = v.findViewById(R.id.brightness_icon);
        volumeIcon = v.findViewById(R.id.volume_icon);


        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int volumeChangeVal;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // volumeChange = progress;
                // code to come
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //code to come
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //come to come
            }
        });

        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int brightnessChangedVal;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //brightnessChangedVal = progress;
                // code to come
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //code to come
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //come to come
            }
        });
        return v;
    }




}
