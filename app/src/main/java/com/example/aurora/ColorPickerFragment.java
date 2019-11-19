

package com.example.aurora;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.larswerkman.holocolorpicker.ColorPicker;


public class ColorPickerFragment extends Fragment {

    private AnimatedVectorDrawable blob;
    private AnimatedVectorDrawable pause;
    private AnimatedVectorDrawable play;
    private ImageView blobView;
    private ImageView pp_button;
    private boolean tick = true;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //return all views in UI
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.color_picker_fragment, container, false);

        //initializing and animating UI widgets
        getColorPicker();
        return view;
    }

    public void getColorPicker(){
        ColorPicker picker = (ColorPicker) view.findViewById(R.id.picker);
        view.findViewById(R.id.picker);
        //To get the color
        picker.getColor();

        //To set the old selected color u can do it like this
        //picker.setOldCenterColor(picker.getColor());
        //adds listener to the colorpicker which is implemented in the activity
        //picker.setOnColorChangedListener((ColorPicker.OnColorChangedListener) this);

        //to turn of showing the old color
        //picker.setShowOldCenterColor(false);
    }




}


