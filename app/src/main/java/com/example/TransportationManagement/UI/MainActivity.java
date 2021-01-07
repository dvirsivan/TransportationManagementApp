package com.example.TransportationManagement.UI;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Model.RegisteredItem;
import com.example.TransportationManagement.R;
import com.example.TransportationManagement.UI.ui.home.HomeFragment;
import com.example.TransportationManagement.UI.ui.slideshow.SlideshowFragment;
import com.example.TransportationManagement.adapter.RegisteredAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView registeredList = (ListView)findViewById(R.id.registeredList);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

       // navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

        //});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ListView registeredList = (ListView)findViewById(R.id.registeredList);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        EditText email = ((EditText)findViewById(R.id.email));
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        ListView registeredList = (ListView)findViewById(R.id.registeredList);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        EditText email = ((EditText)findViewById(R.id.email));
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

}