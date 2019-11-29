package com.example.coffeelife;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.coffeelife.data.LoginDataSource;
import com.example.coffeelife.data.LoginRepository;
import com.example.coffeelife.data.model.LoggedInUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Message;
import android.se.omapi.Session;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.PasswordAuthentication;
import java.util.Properties;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        String t = getIntent().getStringExtra("r");
        if(t != null){
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
        }else {
            setContentView(R.layout.activity_login2);
        }
    }
    public void webClick(View view){
        Intent brow = new Intent(Intent.ACTION_VIEW, Uri.parse("http://coffeelife24.ru"));
        startActivity(brow);
    }
    public void sendClick(View view){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void loginClick(View view){
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
        //navigationView.setNavigationItemSelectedListener(this);
    }

    public void main(String [] args){

    }

    public void Buy(View view){
        Intent intent = new Intent(this, BuyTicket.class);
        startActivity(intent);
    }

    public void BuyCup(View view){
        Intent start = new Intent(this, BuyCup.class);
        startActivity(start);
    }
    public void OpenMap(View view){
        Intent brow = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.ru/maps/place/Coffee+Brothers/@55.160389,61.374527,19z/data=!4m8!1m2!3m1!2sCoffee+Brothers!3m4!1s0x43c592b86d8bff0d:0xa81b2372f9d4f1b6!8m2!3d55.160281!4d61.3751033"));
        startActivity(brow);
    }


/*    @Override
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
 */
}
