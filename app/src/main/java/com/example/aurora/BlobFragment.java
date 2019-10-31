package com.example.aurora;

import android.content.res.Resources;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import static androidx.core.view.ViewCompat.animate;

public class BlobFragment extends Fragment { private AnimatedVectorDrawable blob;
    private AnimatedVectorDrawable pause;
    private AnimatedVectorDrawable play;
    private ImageView blobView;
    private ImageView pp_button;
    private boolean tick = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //return all views in UI
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.blob_fragment, container, false);

        //initializing and animating buttons
        pp_button = (ImageView) v.findViewById(R.id.pause_play_b);
        pause =  (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_pause_play_button, null);
        play = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.avd_play_pause_button, null);
        pp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(null);
            }
        });

        //initializing and animating blob
        blobView = (ImageView) v.findViewById(R.id.blob_o);
        blob = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.blob_ocean, null);
        blobView.setImageDrawable(blob);
        blob.start();

        return v;
    }

    public void animate(View view) {
        AnimatedVectorDrawable drawable = tick ? pause : play;
        pp_button.setImageDrawable(drawable);
        drawable.start();
        tick = !tick;
    }
}
