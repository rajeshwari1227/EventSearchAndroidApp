package com.example.ticketmaster;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ticketmaster.databinding.FragmentMainBinding;
import com.example.ticketmaster.ui.main.PageViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_fragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText editText;


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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    private RadioGroup radioGroup;
    private RadioButton current;
    private RadioButton other;
    public Button button;
    public Search_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_fragment newInstance(String param1, String param2) {
        Search_fragment fragment = new Search_fragment();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        editText = (EditText) view.findViewById(R.id.editTextNumber2);
        System.out.println("heloooooooo11111");
//        System.out.println(editText);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_fragment, container, false);
        keyword = rootView.findViewById(R.id.keyword);

        search = rootView.findViewById(R.id.search);

        //location text box
        location1 = (EditText) rootView.findViewById(R.id.loc_text);
        distance = (EditText) rootView.findViewById(R.id.distance);
        category = (Spinner) rootView.findViewById(R.id.category_spinner);
        units = (Spinner) rootView.findViewById(R.id.units_spinner);




        //fusedlocationprovider

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mQueue = Volley.newRequestQueue(getContext());

        //location radio

        radioGroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);
        current_loc = (RadioButton) rootView.findViewById(R.id.current);
        other_loc = (RadioButton) rootView.findViewById(R.id.other_loc);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (current_loc.isChecked()) {
                    System.out.println("current loc");
                    location1.setEnabled(false);
                    location1.setText("");
                    location1.setError(null);


                    loc_type = "here";

                }
                if (other_loc.isChecked()) {
                    System.out.println("other loc");
                    location1.setEnabled(true);
                    loc_type = "location";
                }

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get location





                System.out.println("hey i am button");
                //  keyword edittext
                keyempty = keyword.getText().toString().trim();
                if (keyempty.isEmpty()) {
                    keyword.setError("Please enter mandatory field");
                } else {
                    keyword.setError(null);
                }

                if (loc_type == "location") {
                    location_val = location1.getText().toString().trim();

                    if (location_val.isEmpty()) {
                        location1.setError("Please enter mandatory field");
                    } else {
                        location1.setError(null);
                    }
                }


                if (!keyempty.isEmpty() && (loc_type == "here" || !location_val.isEmpty())){


                    //category spinner
                    System.out.println(keyempty);
                    category_val = category.getSelectedItem().toString();
                    if (category_val.equals("Arts & Theatre")){
                        category_val = "Arts";
                    }
                    System.out.println(category_val);
                    //units spinner
                    units_val = units.getSelectedItem().toString();
                    System.out.println(units_val);


                    //location text box validation


                    System.out.println(location_val);
                    //distance editetxt

                    distance_val = distance.getText().toString().trim();
                    System.out.println(lat);
//                    getURLDetails();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {

                            getLocation();


                        } else {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
                        }

                    }
                }



            }
        });


        clear = (Button) rootView.findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                keyword.setError(null);
                location1.setError(null);
                keyword.setText("");
                location1.setText("");
                distance.setText("10");
                category.setSelection(0);
                units.setSelection(0);
                current_loc.setChecked(true);
                location1.setText("");
                location1.setEnabled(false);


            }
        });
        return rootView;
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



            @Override
    public void onClick(View v) {

    }
}