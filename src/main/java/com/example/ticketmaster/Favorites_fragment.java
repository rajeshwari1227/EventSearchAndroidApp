package com.example.ticketmaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Favorites_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Favorites_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Favoriterecycleradapter favoriterecycleradapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    View rootView1;
    String sharedPreferencesName;

    public Favorites_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favorites_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Favorites_fragment newInstance(String param1, String param2) {
        Favorites_fragment fragment = new Favorites_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        System.out.println("hello frag fav");
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_favorites_fragment, container, false);



        rootView1 = inflater.inflate(R.layout.fragment_favorites_fragment, container, false);
//            //share preference
////
//            TextView no_fav = (TextView) rootView1.findViewById(R.id.no_favs);
//            sharedPreferences = getContext().getSharedPreferences("favorite", Context.MODE_PRIVATE);editor = sharedPreferences.edit();
//
//
//
//            JSONObject jsonobject;
//            recyclerView = (RecyclerView) rootView1.findViewById(R.id.fav_recycler);
//            layoutManager = new LinearLayoutManager(getContext());
//            recyclerView.setLayoutManager(layoutManager);
//
//            try{
//            Map<String, ?> allEntries = sharedPreferences.getAll();
//            if(allEntries.size()>0) {
//                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
//                    no_fav.setVisibility(View.GONE);
//                    jsonobject = new JSONObject(entry.getValue().toString());
//                    String date = jsonobject.getString("date");
//                    String event_name = jsonobject.getString("eventname");
//                    String category = jsonobject.getString("category");
//                    String venue = jsonobject.getString("venue");
//                    String event_id = jsonobject.getString("eventid");
//                    String venue_id = jsonobject.getString("venueid");
//                    EventListClass item = new EventListClass(date, event_name, event_id, category, venue, venue_id);
//                    event_list_array.add(item);
//                }
//
//
//                System.out.println(event_list_array);
//                mAdapter = new Favoriterecycleradapter(getContext(), event_list_array);
//                recyclerView.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();
//            }else {
//                no_fav.setVisibility(View.VISIBLE);
//            }
////                    Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

        return rootView1;

    }


    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);
        if (isVisible) {


            TextView no_fav = (TextView) rootView1.findViewById(R.id.no_favs);
            sharedPreferences = getContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);editor = sharedPreferences.edit();


            List<EventListClass> event_list_array = new ArrayList<EventListClass>();

            JSONObject jsonobject;

            recyclerView = (RecyclerView) rootView1.findViewById(R.id.fav_recycler);

            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            try{
                Map<String, ?> allEntries = sharedPreferences.getAll();
                if(allEntries.size()>0) {
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        no_fav.setVisibility(View.GONE);
                        jsonobject = new JSONObject(entry.getValue().toString());
                        String date = jsonobject.getString("date");
                        String event_name = jsonobject.getString("eventname");
                        String category = jsonobject.getString("category");
                        String venue = jsonobject.getString("venue");
                        String event_id = jsonobject.getString("eventid");
                        String venue_id = jsonobject.getString("venueid");
                        EventListClass item = new EventListClass(date,event_name,event_id,category,venue,venue_id);

                        event_list_array.add(item);
                    }


                    System.out.println(event_list_array);
                    mAdapter = new Favoriterecycleradapter(getContext(), event_list_array);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }else {
                    no_fav.setVisibility(View.VISIBLE);
                }
//                    Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            }catch (Exception e){
                e.printStackTrace();
            }

//            FragmentTransaction ftr = getFragmentManager().beginTransaction();
//            ftr.detach(this).attach(this).commit();
        }
    }





}