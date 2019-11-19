package com.example.aurora;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;



public class BlobFragment extends Fragment {
    private AnimatedVectorDrawable blob;
    private ImageView blobView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.blob_fragment, container, false);
        blobView = (ImageView) v.findViewById(R.id.blob_o);
        blob = (AnimatedVectorDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.blob_ocean, null);
        blobView.setImageDrawable(blob);
        blob.start();
        return v;
    }
}
