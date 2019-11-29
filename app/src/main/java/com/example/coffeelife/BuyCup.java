package com.example.coffeelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BuyCup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_cup);
    }
    public void Back(View view){
        Intent back = new Intent(this, MainActivity.class);
        String r = "1";
        back.putExtra("r", r);
        startActivity(back);
    }
}
