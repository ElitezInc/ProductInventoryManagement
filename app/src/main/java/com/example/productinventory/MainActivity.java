package com.example.productinventory;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG ="TAG";
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    RecyclerView post_list;

    public static List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        post_list = findViewById(R.id.post_list);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        navigationView = findViewById(R.id.nav_view);
        //navigationView.inflateMenu(R.menu.nav_menu);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new HomeFragment()).commit();

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId())
            {
                case R.id.action_products:
                    Log.d("Debug", "Selected products");

                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,
                            new HomeFragment()).commit();

                    break;
                case R.id.action_orders:
                    Log.d("Debug", "Selected orders");

                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,
                            new BlankFragment()).commit();

                    break;
                case R.id.action_discussions:
                    Log.d("Debug", "Selected discussions");
                    break;
                case R.id.action_stock:
                    Log.d("Debug", "Selected stock");
                    break;
                case R.id.action_messaging:
                    Log.d("Debug", "Selected messaging");
                    break;
                default:
                    break;
            }

            return true;
        });
    }
}

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Log.d("Debug", "Selected home");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */