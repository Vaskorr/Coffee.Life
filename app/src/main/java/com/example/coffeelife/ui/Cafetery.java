package com.example.coffeelife.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.lifecycle.ViewModel;

import com.example.coffeelife.MainActivity;
import com.example.coffeelife.R;

public class Cafetery extends Activity {
    private String name;
    private int id;
    private Drawable pic;

    public void setPic(Drawable pic){
        StringBuilder sb = new StringBuilder();
        sb.append("Photo_");
        sb.append(id);
        String res = sb.toString();
    }
}
