package com.example.ticketmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView noRecs;
    JSONArray mJsonObject;


    @Override
    public void onRestart()
    {
        super.onRestart();
        List<EventListClass> event_list_array = new ArrayList<EventListClass>();

        recyclerView = (RecyclerView) findViewById(R.id.event_list_rv);
        noRecs = (TextView) findViewById(R.id.no_recs);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        try {
            noRecs.setVisibility(View.GONE);
            mJsonObject= new JSONArray(getIntent().getStringExtra("searchResults"));
            System.out.println(mJsonObject);

            for(int i = 0;i<mJsonObject.length();i++){
                JSONObject jsonobject = mJsonObject.getJSONObject(i);

                String date = jsonobject.getString("0");
                String event_name = jsonobject.getString("1");
                String category = jsonobject.getString("2");
                String venue = jsonobject.getString("3");
                String event_id = jsonobject.getString("4");
                String venue_id = jsonobject.getString("5");
                EventListClass item = new EventListClass(date,event_name,event_id,category,venue,venue_id);
                event_list_array.add(item);



            }
            System.out.println(event_list_array);
            mAdapter = new RecycleViewAdapter(SearchResults.this,event_list_array);
            recyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
//            e.printStackTrace();
            noRecs.setVisibility(View.VISIBLE);


        }
        // do some stuff here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recycler_fragment);
        List<EventListClass> event_list_array = new ArrayList<EventListClass>();

        recyclerView = (RecyclerView) findViewById(R.id.event_list_rv);
        noRecs = (TextView) findViewById(R.id.no_recs);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        try {
            noRecs.setVisibility(View.GONE);
             mJsonObject= new JSONArray(getIntent().getStringExtra("searchResults"));
            System.out.println(mJsonObject);

            for(int i = 0;i<mJsonObject.length();i++){
                JSONObject jsonobject = mJsonObject.getJSONObject(i);

                String date = jsonobject.getString("0");
                String event_name = jsonobject.getString("1");
                String category = jsonobject.getString("2");
                String venue = jsonobject.getString("3");
                String event_id = jsonobject.getString("4");
                String venue_id = jsonobject.getString("5");
                EventListClass item = new EventListClass(date,event_name,event_id,category,venue,venue_id);
                event_list_array.add(item);



            }
            System.out.println(event_list_array);
            mAdapter = new RecycleViewAdapter(SearchResults.this,event_list_array);
            recyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
//            e.printStackTrace();
            noRecs.setVisibility(View.VISIBLE);


        }


        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
//        Intent prev = getIntent();
//        Bundle b = prev.getExtras();



//        System.out.println(b);



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillresultdata(){


    }
}