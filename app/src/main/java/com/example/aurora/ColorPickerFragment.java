package com.example.aurora;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.larswerkman.holocolorpicker.ColorPicker;
import androidx.fragment.app.Fragment;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;


public class ColorPickerFragment extends Fragment {

    /*******Variable will be used later on**********/
    private AnimatedVectorDrawableCompat blob;
    private AnimatedVectorDrawableCompat pause;
    private AnimatedVectorDrawableCompat play;
    private ImageView blobView;
    private ImageView pp_button;
    /***********************************************/

    private boolean tick = true;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.color_picker_fragment, container, false);
        getColorPicker();
        //initializeButtons();
        //animateButtons();
        return view;
    }

    public void getColorPicker() {
        ColorPicker picker = (ColorPicker) view.findViewById(R.id.picker);
        view.findViewById(R.id.picker);
        picker.getColor();

        //To set the old selected color u can do it like this
        //picker.setOldCenterColor(picker.getColor());
        //adds listener to the colorpicker which is implemented in the activity
        //picker.setOnColorChangedListener((ColorPicker.OnColorChangedListener) this);

        //to turn of showing the old color
        //picker.setShowOldCenterColor(false);
    }}



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


    void initializeButtons(){
        pp_button = (ImageView) view.findViewById(R.id.pause_play_b);
        //pause =  (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_pause_play_button, null);
        //play = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_play_pause_button, null);
    }

    void animateButtons(){
        pp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(null);
            }
        });
    }*/



