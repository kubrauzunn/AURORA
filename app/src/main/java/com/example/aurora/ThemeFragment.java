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
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theme, container, false);
        setUpGUI();
        trackSoundBarProgress();
        trackBrightnessBarProgress();
        return view;
    }


    void setUpGUI() {
        brightnessSeekBar = view.findViewById(R.id.brightnessSeekBar);
        soundSeekBar = view.findViewById(R.id.volumeSeekBar);
        bulbBrightnessIcon = view.findViewById(R.id.brightness_icon);
        volumeIcon = view.findViewById(R.id.volume_icon);
    }


    /**
     * Code for the volume bar which allows the user to increase or decrease the lamps volume
     */

    void trackSoundBarProgress() {
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
    }

    /**
     * Code for the brightness bar which allows the user to increase or decrease the lamps brightness
     */

    void trackBrightnessBarProgress(){
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

    }

}
