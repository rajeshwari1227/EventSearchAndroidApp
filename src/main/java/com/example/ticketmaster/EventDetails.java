package com.example.ticketmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticketmaster.ui.main.SectionsPagerAdapter;
import com.example.ticketmaster.databinding.ActivityEventDetailsBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventDetails extends AppCompatActivity {

    private GoogleMap mMap;


    private SectionsPagerAdapter1 mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.info_outline,
            R.drawable.artist,
            R.drawable.venue

    };



    public JSONObject mJsonObject;
    public String venueId,eventId,eventtitle,venue,date,category;
    public JSONArray artist_array =null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String sharedPreferencesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        try {
            mJsonObject= new JSONObject(getIntent().getStringExtra("eventdetails"));
            artist_array = mJsonObject.getJSONArray("artist");
            venueId = getIntent().getStringExtra("venueId");
            venue = getIntent().getStringExtra("venue");
            category = getIntent().getStringExtra("category");
            date = getIntent().getStringExtra("date");
            eventId = getIntent().getStringExtra("eventId");
            sharedPreferences = this.getSharedPreferences("favorites", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            sharedPreferencesName = eventId;
            eventtitle = getIntent().getStringExtra("eventname");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(eventtitle);
        actionBar.setDisplayShowTitleEnabled(true);


        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);



        mSectionsPagerAdapter = new SectionsPagerAdapter1(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager1);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //do stuff here
                System.out.println(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                System.out.println(tab.getPosition());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                System.out.println(tab.getPosition());


            }
        });

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

    }

    public JSONObject sendEventDetails(){
        return mJsonObject;
    }
    public String getVenueId(){


        return venueId;
    }

    public JSONArray getArtist_array(){


        return artist_array;
    }




    public class SectionsPagerAdapter1 extends FragmentPagerAdapter {

        public SectionsPagerAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new Details();
                case 1:
                    return new Artist();
                case 2:
                    return new Venue();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "EVENTS";
                case 1:
                    return "ARTIST(S)";
                case 2:
                    return "VENUE";
            }
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.twitter:

                Twitter(mJsonObject);
                return true;
            case R.id.fav_button:


                Fav(mJsonObject,item);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);

        try{

            if (sharedPreferences.contains(sharedPreferencesName)) {
                menu.findItem(R.id.fav_button).setIcon(R.drawable.heart_fill_white);

            } else {
                menu.findItem(R.id.fav_button).setIcon(R.drawable.heart_outline_white);

            }}catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public void Twitter(JSONObject json){
        try {
            System.out.println("hello twitter");
            String eventname = json.getString("name");
            String venue = json.getString("name");
            String twitterURL = "https://twitter.com/intent/tweet?text=Checkout "
                    + eventname
                    + " at " + venue
                    + " %23CSCI571EventSearch";

            System.out.println(twitterURL);
            Uri uriUrl = Uri.parse(twitterURL);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        }catch (JSONException e){
            e.printStackTrace();

        }
    }


    public void Fav(JSONObject favorites,MenuItem item){


        JSONObject mydata = new JSONObject();
        try {
            mydata.put("eventname",eventtitle);
            mydata.put("eventid",eventId);
            mydata.put("category",category);
            mydata.put("date",date);
            mydata.put("venue",venue);
            mydata.put("venueid",venueId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sharedPreferences.contains(sharedPreferencesName)) {
            Toast.makeText(this, sharedPreferencesName + " was removed from favorites", Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.heart_outline_white);


            editor.remove(sharedPreferencesName);
            editor.commit();
        } else {
            Toast.makeText(this, sharedPreferencesName + " was added to favorites", Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.heart_fill_white);
            editor.putString(sharedPreferencesName, mydata.toString());
            editor.apply();

        }

    }






}