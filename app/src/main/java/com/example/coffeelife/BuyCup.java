package com.example.coffeelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class BuyCup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_cup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public void Back(View view){
        Intent back = new Intent(this, MainActivity.class);
        String r = "1";
        back.putExtra("r", r);
        startActivity(back);
    }         //НАЗАД
    public void Buy(View view){
         Toast.makeText(getApplicationContext(), "Заказ сделан!", Toast.LENGTH_LONG).show();
    }          //ЗАКАЗ
}
