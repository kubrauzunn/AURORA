package com.example.aurora;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;


public class BlobFragment extends Fragment {
    private AnimatedVectorDrawableCompat blob;
    private ImageView blobView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.blob_fragment, container, false);
        blobView = (ImageView) v.findViewById(R.id.blob_o);
        blob = AnimatedVectorDrawableCompat.create(getContext(), R.drawable.blob_ocean);
        blobView.setImageDrawable(blob);
        blob.start();
        return v;

    }

}


