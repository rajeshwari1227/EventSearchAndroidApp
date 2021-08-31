package com.example.ticketmaster;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Artist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Artist extends Fragment {

    private RequestQueue mQueue;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView no_artists;

    public Artist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Artist.
     */
    // TODO: Rename and change types and number of parameters
    public static Artist newInstance(String param1, String param2) {
        Artist fragment = new Artist();
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

        View root = inflater.inflate(R.layout.fragment_artist, container, false);
        EventDetails activity = (EventDetails) getActivity();
        no_artists = (TextView) root.findViewById(R.id.no_artists);


        JSONArray artists = activity.getArtist_array();
        mQueue = Volley.newRequestQueue(getContext());

        if (artists.length()>0){
            no_artists.setVisibility(View.GONE);
            try {
                Artist1(artists.getString(0),root,artists);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            no_artists.setVisibility(View.VISIBLE);

        }




        // Inflate the layout for this fragment
        return root;
    }

    public void Artist1(String artist1 ,View root,JSONArray artists){
        try {
            no_artists.setVisibility(View.GONE);

                String url = null;

                Map<String, String> artist_dict = new HashMap<String, String>();

                artist_dict.put("name", "Name");
                artist_dict.put("followers", "Followers");
                artist_dict.put("popularity", "Popularity");
                artist_dict.put("check", "Check At");


                url = "https://eng-empire-315722.wl.r.appspot.com/spotify?key=" + artist1;
                System.out.println(url);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        int not_null_count=0;
                        TableLayout artist_table = (TableLayout) root.findViewById(R.id.artist_table);
                        artist_table.setStretchAllColumns(true);
                        artist_table.bringToFront();

                        try {

                        for (Iterator<String> it = response.keys(); it.hasNext(); ) {
                            String key = it.next();
                                if (!response.getString(key).isEmpty() && artist_dict.get(key) != null) {
                                    not_null_count++;

                                    View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                                    TextView a1 = tr.findViewById(R.id.table_keys);
                                    a1.setText(artist_dict.get(key));
                                    TextView a2 = tr.findViewById(R.id.table_values);
                                    if (key.equals("check")){
                                        a2.setClickable(true);
                                        a2.setMovementMethod(LinkMovementMethod.getInstance());
                                        String UrlText = "<a href=\""+response.getString(key)+"\" style=\"teaxt-decoration:none;color:blue;\" > Spotify </a>";
//                a2.UnderlineText = false;
                                        a2.setText(Html.fromHtml(UrlText));
                                    }
                                    else {
                                        a2.setText(response.getString(key));
                                    }
//                                    a2.setText(response.getString(key));
                                    artist_table.addView(tr);


                                }


                            }

                            if (not_null_count ==0){
                                View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                                TextView a1 = tr.findViewById(R.id.table_keys);
                                a1.setText(artist1);
                                TextView a2 = tr.findViewById(R.id.table_values);
                                a2.setText("No details.");
                                artist_table.addView(tr);
                            }
                            if (artists.length() > 1) {
                                Artist2(artists.getString(1), root);
                            }
                        }

                         catch (JSONException e) {
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


        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public void Artist2(String artist1 ,View root){
        try {
            no_artists.setVisibility(View.GONE);

            String url = null;

            Map<String, String> artist_dict = new HashMap<String, String>();

            artist_dict.put("name", "Name");
            artist_dict.put("followers", "Followers");
            artist_dict.put("popularity", "Popularity");
            artist_dict.put("check", "Check At");


            url = "https://eng-empire-315722.wl.r.appspot.com/spotify?key=" + artist1;
            System.out.println(url);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(response);
                    int not_null_count=0;

                    TableLayout artist_table = (TableLayout) root.findViewById(R.id.artist_table);
                    artist_table.setStretchAllColumns(true);
                    artist_table.bringToFront();


                    for (Iterator<String> it = response.keys(); it.hasNext(); ) {
                        String key = it.next();
                        try {
                            if (!response.getString(key).isEmpty() && artist_dict.get(key) != null) {
                                not_null_count++;
                                View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                                TextView a1 = tr.findViewById(R.id.table_keys);
                                a1.setText(artist_dict.get(key));
                                TextView a2 = tr.findViewById(R.id.table_values);
                                if (key.equals("check")){
                                    a2.setClickable(true);
                                    a2.setMovementMethod(LinkMovementMethod.getInstance());
                                    String UrlText = "<a href=\""+response.getString(key)+"\" style=\"teaxt-decoration:none;color:blue;\" > Spotify </a>";
//                a2.UnderlineText = false;
                                    a2.setText(Html.fromHtml(UrlText));
                                }
                                else {
                                    a2.setText(response.getString(key));
                                }
                                artist_table.addView(tr);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    if (not_null_count ==0){
                        View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                        TextView a1 = tr.findViewById(R.id.table_keys);
                        a1.setText(artist1);
                        TextView a2 = tr.findViewById(R.id.table_values);


                        a2.setText("No details.");
                        artist_table.addView(tr);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                }
            });
            mQueue.add(request);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


