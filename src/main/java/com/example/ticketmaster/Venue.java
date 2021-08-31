package com.example.ticketmaster;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Venue#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Venue extends Fragment  implements  OnMapReadyCallback{

    private RequestQueue mQueue;

    GoogleMap map;
    MapView mapView;
    double latitude;
    double longitude;
    View root;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Venue() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Venue.
     */
    // TODO: Rename and change types and number of parameters
    public static Venue newInstance(String param1, String param2) {
        Venue fragment = new Venue();
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



        root = inflater.inflate(R.layout.fragment_venue, container, false);




        Map<String, String> dictionary = new HashMap<String, String>();

        dictionary.put("name","Name");
        dictionary.put("address","Address");
        dictionary.put("city","City");
        dictionary.put("open","Open Hours");
        dictionary.put("general","General Rules");
        dictionary.put("child","Child Rules");

        List keys = new ArrayList<String>();
        keys.add("name");
        keys.add("address");
        keys.add("city");
        keys.add("open");
        keys.add("general");
        keys.add("child");




        EventDetails activity = (EventDetails) getActivity();


//        getVenueDetails(activity.getVenueId());


        mQueue = Volley.newRequestQueue(getContext());

        String url = "https://eng-empire-315722.wl.r.appspot.com/venueDetails?venue=" + activity.getVenueId();
//        String url = "https://eng-empire-315722.wl.r.appspot.com/eventDetails?event="+eventId;
        System.out.println(url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);

                try {
//                    JSONArray res = new JSONArray(response);

                    TableLayout venue_table = (TableLayout) root.findViewById(R.id.venue_table);
                    venue_table.setStretchAllColumns(true);
                    venue_table.bringToFront();

                    JSONObject myobj = response.getJSONObject(0);

                    latitude = myobj.getDouble("lat");
                    longitude = myobj.getDouble("lng");

                    mapView = root.findViewById(R.id.mapView);
                    if(mapView != null){
                        mapView.onCreate(null);
                        mapView.onResume();
                        mapView.getMapAsync(Venue.this);
                    }



//                    myobj.keySet().forEach(keyStr ->
//                    {
//
//                    });

//                    for (Iterator<String> it = myobj.keys(); it.hasNext(); ) {
//                        String key = it.next();
//                        try {
//                            if(!myobj.getString(key).isEmpty() && dictionary.get(key)!=null) {
//                                View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
//                                TextView a1 = tr.findViewById(R.id.table_keys);
//                                a1.setText(dictionary.get(key));
//                                TextView a2 = tr.findViewById(R.id.table_values);
//                                a2.setText(myobj.getString(key));
//                                venue_table.addView(tr);
//                            } }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
                    for(int i = 0;i<keys.size();i++)
                    {
                        String key = (String) keys.get(i);
                        // search  for value
//                        String key = dictionary.get(name);
//                        System.out.println("Key = " + name + ", Value = " + url);


                        //                        String key = it.next();
                        try {
                            if(!myobj.getString(key).isEmpty() && dictionary.get(key)!=null) {
                                View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                                TextView a1 = tr.findViewById(R.id.table_keys);
                                a1.setText(dictionary.get(key));
                                TextView a2 = tr.findViewById(R.id.table_values);
                                a2.setText(myobj.getString(key));
                                venue_table.addView(tr);
                            } }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

//                    try {
//                        if(!myobj.getString("name").isEmpty()) {
//                            TableRow tr = new TableRow(getContext());
//                            TextView a1 = new TextView(getContext());
//                            a1.setText("Name");
//                            TextView a2 = new TextView(getContext());
//
//
//
//                            a2.setText(myobj.getString("name"));
//                            tr.addView(a1);
//                            tr.addView(a2);
//                            venue_table.addView(tr);
//                        } }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        if(!myobj.getString("address").isEmpty()) {
//                            TableRow tr = new TableRow(getContext());
//                            TextView a1 = new TextView(getContext());
//                            a1.setText("Address");
//                            TextView a2 = new TextView(getContext());
//                            a2.setText(myobj.getString("address"));
//                            tr.addView(a1);
//                            tr.addView(a2);
//                            venue_table.addView(tr);
//                        } }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        if(!myobj.getString("city").isEmpty()) {
//                            TableRow tr = new TableRow(getContext());
//                            TextView a1 = new TextView(getContext());
//                            a1.setText("City");
//                            TextView a2 = new TextView(getContext());
//                            a2.setText(myobj.getString("city"));
//                            tr.addView(a1);
//                            tr.addView(a2);
//                            venue_table.addView(tr);
//                        } }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        if(!myobj.getString("phone").isEmpty()) {
//                            TableRow tr = new TableRow(getContext());
//                            TextView a1 = new TextView(getContext());
//                            a1.setText("Phone Number");
//                            TextView a2 = new TextView(getContext());
//                            a2.setText(myobj.getString("phone"));
//                            tr.addView(a1);
//                            tr.addView(a2);
//                            venue_table.addView(tr);
//                        } }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        if(!myobj.getString("open").isEmpty()) {
//                            TableRow tr = new TableRow(getContext());
//                            TextView a1 = new TextView(getContext());
//                            a1.setText("Open Hours");
//                            TextView a2 = new TextView(getContext());
//                            a2.setText(myobj.getString("open"));
//                            tr.addView(a1);
//                            tr.addView(a2);
//                            venue_table.addView(tr);
//                        } }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        if(!myobj.getString("general").isEmpty()) {
//                            TableRow tr = new TableRow(getContext());
//                            TextView a1 = new TextView(getContext());
//                            a1.setText("General Rule");
//                            TextView a2 = new TextView(getContext());
//                            a2.setText(myobj.getString("general"));
//                            tr.addView(a1);
//                            tr.addView(a2);
//                            venue_table.addView(tr);
//                        } }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        if(!myobj.getString("child").isEmpty()) {
//                            TableRow tr = new TableRow(getContext());
//                            TextView a1 = new TextView(getContext());
//                            a1.setText("Child Rule");
//
//                            TextView a2 = new TextView(getContext());
//                            a2.setText(myobj.getString("child"));
//
//                            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                                    TableRow.LayoutParams.WRAP_CONTENT));
//
//
//                            tr.addView(a1,60,80);
//                            tr.addView(a2, 60,80);
//                            venue_table.addView(tr);
//                        } }
//                    catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }








            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        mQueue.add(request);


        // Inflate the layout for this fragment
        return root;

        // Inflate the layout for this fragment
//        return root;
    }


    @Override
    public void onViewCreated(@NonNull View mview, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(mview, savedInstanceState);
//        mapView = root.findViewById(R.id.mapView);
//        if(mapView != null){
//            mapView.onCreate(null);
//            mapView.onResume();
//            mapView.getMapAsync(this);
//        }


    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        CameraPosition position = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(14).bearing(0).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));

    }
}