package com.example.ticketmaster.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketmaster.EventListClass;
import com.example.ticketmaster.Favoriterecycleradapter;
import com.example.ticketmaster.Favorites_fragment;
import com.example.ticketmaster.R;
import com.example.ticketmaster.RecycleViewAdapter;
import com.example.ticketmaster.SearchResults;
import com.example.ticketmaster.Search_fragment;
import com.example.ticketmaster.databinding.FragmentMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;
    private Button search, clear;
    private Spinner category, units;
    private EditText keyword, location1, distance;
    private RadioGroup radioGroup;
    private RadioButton current_loc, other_loc;
    FusedLocationProviderClient fusedLocationProviderClient;
    private RequestQueue mQueue;
    private String lat,category_val;
    private String loc_type = "here";
    private String keyempty;
    private String distance_val="10",units_val = "miles";
    private String location_val = "";
    private Favoriterecycleradapter favoriterecycleradapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<EventListClass> event_list_array = new ArrayList<EventListClass>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String sharedPreferencesName;



    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
//            View rootView = inflater.inflate(R.layout.fragment_search_fragment, container, false);
//            keyword = rootView.findViewById(R.id.keyword);
//
//            search = rootView.findViewById(R.id.search);
//
//            //location text box
//            location1 = (EditText) rootView.findViewById(R.id.loc_text);
//            distance = (EditText) rootView.findViewById(R.id.distance);
//            category = (Spinner) rootView.findViewById(R.id.category_spinner);
//            units = (Spinner) rootView.findViewById(R.id.units_spinner);
//
//
//
//
//            //fusedlocationprovider
//
//            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//
//            mQueue = Volley.newRequestQueue(getContext());
//
//            //location radio
//
//            radioGroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);
//            current_loc = (RadioButton) rootView.findViewById(R.id.current);
//            other_loc = (RadioButton) rootView.findViewById(R.id.other_loc);
//            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    if (current_loc.isChecked()) {
//                        System.out.println("current loc");
//                        location1.setEnabled(false);
//                        location1.setText("");
//                        location1.setError(null);
//
//
//                        loc_type = "here";
//
//                    }
//                    if (other_loc.isChecked()) {
//                        System.out.println("other loc");
//                        location1.setEnabled(true);
//                        loc_type = "location";
//                    }
//
//                }
//            });
//
//            search.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    //get location
//
//
//
//
//
//                    System.out.println("hey i am button");
//                    //  keyword edittext
//                    keyempty = keyword.getText().toString().trim();
//                    if (keyempty.isEmpty()) {
//                        keyword.setError("Please enter mandatory field");
//                    } else {
//                        keyword.setError(null);
//                    }
//
//                    if (loc_type == "location") {
//                        location_val = location1.getText().toString().trim();
//
//                        if (location_val.isEmpty()) {
//                            location1.setError("Please enter mandatory field");
//                        } else {
//                            location1.setError(null);
//                        }
//                    }
//
//
//                    if (!keyempty.isEmpty() && (loc_type == "here" || !location_val.isEmpty())){
//
//
//                        //category spinner
//                        System.out.println(keyempty);
//                        category_val = category.getSelectedItem().toString();
//                        if (category_val.equals("Arts & Theatre")){
//                            category_val = "Arts";
//                        }
//                        System.out.println(category_val);
//                        //units spinner
//                        units_val = units.getSelectedItem().toString();
//                        System.out.println(units_val);
//
//
//                        //location text box validation
//
//
//                        System.out.println(location_val);
//                        //distance editetxt
//
//                        distance_val = distance.getText().toString().trim();
//                        System.out.println(lat);
////                    getURLDetails();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                                    == PackageManager.PERMISSION_GRANTED && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
//                                    == PackageManager.PERMISSION_GRANTED) {
//                                getLocation();
//
//                            } else {
//                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
//                            }
//
//                        }
//                    }
//
//
//
//                }
//            });
//
//
//            clear = (Button) rootView.findViewById(R.id.clear);
//
//            clear.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    keyword.setError(null);
//                    location1.setError(null);
//                    keyword.setText("");
//                    location1.setText("");
//                    distance.setText("10");
//                    category.setSelection(0);
//                    units.setSelection(0);
//                    current_loc.setChecked(true);
//                    location1.setText("");
//                    location1.setEnabled(false);
//
//
//                }
//            });
//            return rootView;

//            return new Search_fragment();

        }
        else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {

//            View rootView1 = inflater.inflate(R.layout.fragment_favorites_fragment, container, false);





//
//
//
//            View rootView1 = inflater.inflate(R.layout.fragment_favorites_fragment, container, false);
//            //share preference
//
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
//
//            return rootView1;
        } else {

            final TextView textView = binding.sectionLabel;
            pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    textView.setText(s);
                }
            });
        }
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode ==100 && (grantResults.length>0)&&(grantResults[0]+grantResults[1] ==PackageManager.PERMISSION_GRANTED)){
            getLocation();
        }else {
            Toast.makeText(getContext(),"no loc",Toast.LENGTH_LONG).show();
        }

    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {


            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {

                @SuppressLint("MissingPermission")
                @Override
                public void onComplete(@NonNull @NotNull Task<Location> task) {

                    Location location = task.getResult();
                    if (location != null) {
                        System.out.println(location.getLatitude());
                        lat = Double.toString(location.getLatitude())+","+Double.toString(location.getLongitude());
                        getURLDetails();
                    } else {
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(1000)
                                .setFastestInterval(1000).setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {

                                Location location2 = locationResult.getLastLocation();
                                lat = Double.toString(location2.getLatitude())+","+Double.toString(location2.getLongitude());
                                getURLDetails();
                            }
                        };


                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }

                }
            });

        }else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }


    public void getURLDetails(){

        String url = "https://eng-empire-315722.wl.r.appspot.com/eventlists?here="+lat+"&category="+category_val+"&distance="+distance_val+"&keyword="+keyempty+"&loctype="+loc_type+"&units="+units_val+"&location="+location_val;
        System.out.println(url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);

                Intent intent = new Intent(getActivity(), SearchResults.class);
                intent.putExtra("searchResults",  response.toString());
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(getActivity(), SearchResults.class);
                intent.putExtra("searchResults",  "FALSE");
                startActivity(intent);
            }
        });
    mQueue.add(request);
    }


    public void clear(){

    }
}