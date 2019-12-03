package com.example.aurora;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.larswerkman.holocolorpicker.ColorPicker;

import androidx.fragment.app.Fragment;

/*
 Copyright 2012 Lars Werkman

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */


public class ColorPickerFragment extends Fragment implements ColorPicker.OnColorChangedListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //return all views in UI
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.color_picker_fragment, container, false);

        //initializing and animating UI widgets
        getColorPicker();
        return view;
    }

    public void getColorPicker() {
        final ColorPicker picker = (ColorPicker) view.findViewById(R.id.picker);
        view.findViewById(R.id.picker);
        //To get the color

        picker.getColor();
        int curColor;
        int red = Color.red(picker.getColor());
        int green = Color.green(picker.getColor());
        int blue = Color.blue(picker.getColor());
        curColor = Color.rgb(red, green, blue);
        System.out.println("color picker color " + picker.getColor() + "cur rgb " + curColor );

        //To set the old selected color u can do it like this
        picker.setOldCenterColor(picker.getColor());
        //adds listener to the colorpicker which is implemented in the activity
        picker.setOnColorChangedListener(this);

        //to turn of showing the old color
        picker.setShowOldCenterColor(false);


        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
               int green = Color.green(color);
               int red = Color.red(color);
               int blue = Color.blue(color);


               int pickedColor = Color.rgb(red, green, blue);

                System.out.println("red " + red + " gr " + green + " blue " + blue );
                System.out.println("picked color" + pickedColor);

            }
        });

    }

    @Override
    public void onColorChanged(int color) {
        int green = Color.green(color);
        int red = Color.red(color);
        int blue = Color.blue(color);


        int pickedColor = Color.rgb(red, green, blue);
        System.out.println("red " + red + " gr " + green + " blue " + blue );
        System.out.println("picked color" + pickedColor);

        //thread.send (red x, green y, blue z)
    }




}


