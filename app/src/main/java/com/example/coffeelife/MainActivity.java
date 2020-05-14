package com.example.coffeelife;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.coffeelife.data.LoginDataSource;
import com.example.coffeelife.data.LoginRepository;
import com.example.coffeelife.data.model.LoggedInUser;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Message;
import android.preference.PreferenceManager;
import android.se.omapi.Session;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingMenuLayout;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.opencensus.tags.Tag;
import pl.droidsonroids.gif.GifImageView;            //ИМПО//

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    // ПЕРЕМЕННЫЕ
    private AppBarConfiguration mAppBarConfiguration;          // КОНФИГУРАТОР ОКОН
    private FirebaseAuth mAuth;                                // АУТЕНТИФИКАТОР
    private EditText eemail;                                   // ПОЛЕ ВВОДА EMAIL
    private EditText epassword;                                // ПОЛЕ ВВОДА ПАРОЛЯ
    private DatabaseReference myRef;                           // ССЫЛКА НА БД
    private ArrayList myData = new ArrayList();                // ДАННЫЕ ПОЛЬЗОВАТЕЛЯ


    // МЕТОДЫ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        String t = getIntent().getStringExtra("r");
        if(t != null){
            /*
            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                    R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
             */
        }else {
            SharedPreferences myPreferences
                    = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            if(!myPreferences.getString("login", "none").equals("none")){
                mAuth.signInWithEmailAndPassword(myPreferences.getString("login", "none"), myPreferences.getString("pass", "none"))
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    myRef = FirebaseDatabase.getInstance().getReference();
                                    myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Button name = findViewById(R.id.website);
                                            Button exit = findViewById(R.id.exit);
                                            exit.setClickable(false);
                                            DataLoad dataLoad = new DataLoad();
                                            dataLoad.run(dataSnapshot, myData, name);
                                            exit.setClickable(true);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    // BEGIN
                                    setContentView(R.layout.activity_main);
                                    ScrollView inf = findViewById(R.id.information);
                                    inf.setVisibility(View.GONE);
                                    FlowingDrawer mDrawer = findViewById(R.id.drawerlayout);
                                    mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
                                    mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
                                        @Override
                                        public void onDrawerStateChange(int oldState, int newState) {
                                            if (newState == ElasticDrawer.STATE_CLOSED) {
                                                Log.i("MainActivity", "Drawer STATE_CLOSED");
                                            }
                                        }

                                        @Override
                                        public void onDrawerSlide(float openRatio, int offsetPixels) {
                                            Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
                                        }
                                    });
                                    mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
                                    mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
                                    mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
                                        @Override
                                        public void onDrawerStateChange(int oldState, int newState) {
                                            if (newState == ElasticDrawer.STATE_CLOSED) {
                                                Log.i("MainActivity", "Drawer STATE_CLOSED");
                                            }
                                        }

                                        @Override
                                        public void onDrawerSlide(float openRatio, int offsetPixels) {
                                            Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
                                        }
                                    });


                                    //END
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else{
                setContentView(R.layout.activity_login2);
            }
        }
        eemail = findViewById(R.id.username);
        epassword = findViewById(R.id.password);
        //gifImageView.setVisibility(View.GONE);
    }   // ПРИ ЗАПУСКЕ
    public void webClick(View view){
        Intent brow = new Intent(Intent.ACTION_VIEW, Uri.parse("http://coffeelife24.ru"));
        startActivity(brow);
    }                       // КЛИК НА ССЫЛКУ
    public void sendClick(View view){

    }                         // КЛИК КУДА-ТО
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }        // ЭТО НЕ МОЁ
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }                 // И ЭТО НЕ МОЁ
    public void loginClick(View view){
        eemail = findViewById(R.id.username);
        epassword = findViewById(R.id.password);
        mAuth.signInWithEmailAndPassword(eemail.getText().toString(), epassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            myRef = FirebaseDatabase.getInstance().getReference();
                            myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Button name = findViewById(R.id.website);
                                    Button exit = findViewById(R.id.exit);
                                    exit.setClickable(false);
                                    DataLoad dataLoad = new DataLoad();
                                    dataLoad.run(dataSnapshot, myData, name);
                                    SharedPreferences myPreferences
                                            = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                    SharedPreferences.Editor myEditor = myPreferences.edit();
                                    myEditor.putString("login", eemail.getText().toString());
                                    myEditor.putString("pass", epassword.getText().toString());
                                    myEditor.apply();
                                    try {
                                        Thread.sleep(500);
                                        exit.setClickable(true);
                                    } catch (InterruptedException ex) {
                                        exit.setClickable(true);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            // BEGIN
                            setContentView(R.layout.activity_main);
                            //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            //        .findFragmentById(R.id.mapView);
                            ScrollView inf = findViewById(R.id.information);
                            inf.setVisibility(View.GONE);
                            FlowingDrawer mDrawer = findViewById(R.id.drawerlayout);
                            mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
                            mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
                                @Override
                                public void onDrawerStateChange(int oldState, int newState) {
                                    if (newState == ElasticDrawer.STATE_CLOSED) {
                                        Log.i("MainActivity", "Drawer STATE_CLOSED");
                                    }
                                }

                                @Override
                                public void onDrawerSlide(float openRatio, int offsetPixels) {
                                    Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
                                }
                            });
                            mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
                            mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
                            mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
                                @Override
                                public void onDrawerStateChange(int oldState, int newState) {
                                    if (newState == ElasticDrawer.STATE_CLOSED) {
                                        Log.i("MainActivity", "Drawer STATE_CLOSED");
                                    }
                                }

                                @Override
                                public void onDrawerSlide(float openRatio, int offsetPixels) {
                                    Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
                                }
                            });


                            //END
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }                     // ВХОД В СИСТЕМУ
    public void registerClick(View view){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }                  // РЕГИСТРАЦИЯ
    public void main(String [] args){

    }                         // ГЛАВНАЯ ФУНКЦИЯ
    public void Buy(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){            // ЕСЛИ ПОЛЬЗОВАТЕЛЬ ВОШЕЛ
            Intent intent = new Intent(this, BuyTicket.class);
            startActivity(intent);
        }
    }                            // КУПИТЬ АБОНЕМЕНТ
    public void BuyCup(View view){
        Intent start = new Intent(this, BuyCup.class);
        startActivity(start);
    }                         // КУПИТЬ КОФЕ
    public void OpenMap(View view){
        Intent brow = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.ru/maps/place/Coffee+Brothers/@55.160389,61.374527,19z/data=!4m8!1m2!3m1!2sCoffee+Brothers!3m4!1s0x43c592b86d8bff0d:0xa81b2372f9d4f1b6!8m2!3d55.160281!4d61.3751033"));
        startActivity(brow);
        String s = "oo";
        System.out.println(s.toString()+s.toString());
    }                        // НАВИГАЦИЯ
    public void OpenMenu(View view){
        FlowingDrawer flow = findViewById(R.id.drawerlayout);
        flow.openMenu(true);
    }                       // ОТКРЫТЬ МЕНЮ ПО НАЖАТИЮ КНОПКИ
    public void InfoShow(View view){
        ScrollView inf = findViewById(R.id.information);
        ScrollView caf = findViewById(R.id.cafes);
        inf.setVisibility(View.VISIBLE);
        caf.setVisibility(View.GONE);
    }                       // ИНФОРМАЦИЯ О ПРИЛОЖЕНИИ
    public void CafeShow(View view){
        ScrollView inf = findViewById(R.id.information);
        ScrollView caf = findViewById(R.id.cafes);
        inf.setVisibility(View.GONE);
        caf.setVisibility(View.VISIBLE);
    }                       // КОФЕЙНИ РЯДОМ
    public void PassBuy(View view){
        Intent intent = new Intent(MainActivity.this, BuyTicket.class);
        startActivity(intent);
    }                        // КУПИТЬ АБОНЕМЕНТ
    public void CoffeeBuy(View view){
        //Intent intent = new Intent(MainActivity.this, BuyCup.class);
        //startActivity(intent);
    }                         // КУПИТЬ КОФЕ
    public void Exit(View view){
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        myPreferences.edit().remove("login").apply();
        myPreferences.edit().remove("pass").apply();
        setContentView(R.layout.activity_login2);
    }                           // ВЫХОД ИЗ АККАУНТА
    @Override
    public void onMapReady(GoogleMap googleMap) {
         GoogleMap mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }          // ПОКА НЕ РАБОЧАЯ КАРТА
    /*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.nav_send){
            Intent brow = new Intent(Intent.ACTION_VIEW, Uri.parse("https://e.mail.ru/compose/?to=coffeelife.f@mail.ru"));
            startActivity(brow);
        }
        if(id == R.id.nav_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Coffee Life - лучший агрегатор кофеен в твоем городе! <Ссылочка в описании>");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent,"Поделиться"));
        }
        if (id == R.id.nav_gallery){
            //setContentView(R.layout.fragment_gallery);
        }
        return false;
    }
     */                                                    // ЧТО-ТО ВРОДЕ НУЖНОЕ
}

class DataLoad extends Thread {
    public void run(DataSnapshot dataSnapshot, ArrayList myData, Button name) {
        Log.e("КРАСАВА", "Mой поток запущен...");
        for (Integer i = 0; i < 5; i++){
            String data;
            data = dataSnapshot.child("UserData").child(i.toString()).getValue(String.class);
            //Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            while(true){
                try{
                    myData.add(data);
                    break;
                }catch (Exception e){

                }
            }
        }
        if(myData != null){
            //Toast.makeText(MainActivity.this, myData.get(0).toString(), Toast.LENGTH_SHORT).show();
            name.setText(myData.get(1).toString() + " " + myData.get(0).toString());
        }
    }
}                            // ЗАГРУЗКА ДАННЫХ С СЕРВЕРА