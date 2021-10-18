package com.example.productinventory;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.example.oauth1.HMACSha1SignatureService;
import com.example.oauth1.TimestampServiceImpl;
import com.google.android.material.navigation.NavigationView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
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

                    drawer.closeDrawer(GravityCompat.START);

                    break;
                case R.id.action_orders:
                    Log.d("Debug", "Selected orders");

                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,
                            new BlankFragment()).commit();

                    drawer.closeDrawer(GravityCompat.START);

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

    //https://github.com/rameshvoltella/WoocommerceAndroidOAuth1
    public static String getOauth1Url(String endpoint, String params, String method, Context context)
    {
        final String url = context.getResources().getString(R.string.baseUrl) + endpoint;
        final String costumerKey = "ck_f3b32711fac61482761242158e246d82ede689da";
        String costumerSecret = "cs_407cc13c32d9fa1eaaaefe5b30eae52efe7adb0b";

        final String nonce = new TimestampServiceImpl().getNonce();
        final String timestamp = new TimestampServiceImpl().getTimestampInSeconds();

        // GENERATED NONCE and TIME STAMP
        Log.d("nonce", nonce);
        Log.d("time", timestamp);

        String firstEncodedString = method + "&" + encodeUrl(url);
        Log.d("firstEncodedString", firstEncodedString);

        String parameterString = "oauth_consumer_key=" + costumerKey + "&oauth_nonce=" + nonce + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timestamp + "&oauth_version=1.0" + (!params.isEmpty() ? "&" + params : "");
        String secondEncodedString = "&" + encodeUrl(parameterString);

        Log.d("secondEncodedString", secondEncodedString);

        String baseString = firstEncodedString + secondEncodedString;

        //THE BASE STRING AND COSTUMER_SECRET KEY IS USED FOR GENERATING SIGNATURE
        Log.d("baseString",baseString);

        String signature = new HMACSha1SignatureService().getSignature(baseString, costumerSecret,"");
        Log.d("SignatureBefore", signature);

        //Signature is encoded before parsing (ONLY FOR THIS EXAMPLE NOT NECESSARY FOR LIB LIKE RETROFIT,OKHTTP)
        signature = encodeUrl(signature);

        Log.d("SignatureAfter ENCODING", signature);

        return context.getResources().getString(R.string.baseUrl) + endpoint + "?oauth_signature_method=HMAC-SHA1&oauth_consumer_key=" + costumerKey + "&oauth_version=1.0&oauth_timestamp=" + timestamp + "&oauth_nonce=" + nonce + "&oauth_signature=" + signature + (!params.isEmpty() ? "&" + params : "");
    }

    public static String encodeUrl(String url)
    {
        String encodedurl = "";
        try {
            encodedurl = URLEncoder.encode(url,"UTF-8");
            Log.d("Encodeurl", encodedurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedurl;
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