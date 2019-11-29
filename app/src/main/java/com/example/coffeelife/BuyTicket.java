package com.example.coffeelife;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;

public class BuyTicket extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView mTextView;
    private TextView roubles;
    public int life;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBarLifes);
        seekBar.setOnSeekBarChangeListener(this);

        mTextView = findViewById(R.id.Lifes);

        roubles = findViewById(R.id.rub);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        life = 200+(seekBar.getProgress()*200);
        mTextView.setText("Выбрано Life'ов: " + life);
        roubles.setText("(" + "RUB " + (double)life/2 + ")");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void Back(View view){
        Intent back = new Intent(this, MainActivity.class);
        String r = "1";
        back.putExtra("r", r);
        startActivity(back);
    }

    public void Payment(View view){
        Toast.makeText(getApplicationContext(), "Оплата успешно произведена! Сумма:" + (double)life/2 + "RUB", Toast.LENGTH_LONG).show();
    }
}
