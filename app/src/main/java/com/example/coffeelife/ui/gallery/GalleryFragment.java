package com.example.coffeelife.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.coffeelife.R;

public class GalleryFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private GalleryViewModel galleryViewModel;
    private TextView mTextView;
    private TextView roubles;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        return root;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int life = 200+(seekBar.getProgress()*200);
        String st = "Выбрано Life'ов: ";
        mTextView.setText(st + life);
        roubles.setText("(" + "RUB " + (double)life/2 + ")");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}