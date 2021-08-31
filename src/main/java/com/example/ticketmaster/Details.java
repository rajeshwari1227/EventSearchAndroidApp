package com.example.ticketmaster;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Details extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Details.
     */
    // TODO: Rename and change types and number of parameters
    public static Details newInstance(String param1, String param2) {
        Details fragment = new Details();
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

        View root = inflater.inflate(R.layout.fragment_details, container, false);

        EventDetails activity = (EventDetails) getActivity() ;
        JSONObject myobj = activity.sendEventDetails();

        System.out.println(myobj);


        TableLayout event_table = (TableLayout) root.findViewById(R.id.event_table);
        event_table.setStretchAllColumns(true);
        event_table.bringToFront();


        try {
            JSONArray artist = myobj.getJSONArray("artist");
        if(artist.length() != 0) {



            View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
            TextView a1 = tr.findViewById(R.id.table_keys);
            TextView a2 = tr.findViewById(R.id.table_values);
            a1.setText("Artist(s)/Teams");
            a2.setText((artist.join(", ")).replaceAll("\"",""));

            event_table.addView(tr);
            } }
        catch (JSONException e) {
                e.printStackTrace();
            }

        try {
        if(!myobj.getString("venue").isEmpty()){

            View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
            TextView a1 = tr.findViewById(R.id.table_keys);
            TextView a2 = tr.findViewById(R.id.table_values);
            a1.setText("Venue");

            a2.setText(myobj.getString("venue"));

            event_table.addView(tr);
            }
        }
        catch (JSONException e){
                e.printStackTrace();
            }

        try {
            if(!myobj.getString("date").isEmpty()){

                View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                TextView a1 = tr.findViewById(R.id.table_keys);
                TextView a2 = tr.findViewById(R.id.table_values);
                a1.setText("Date");

                a2.setText(myobj.getString("date"));


                event_table.addView(tr);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        try {
            if(!myobj.getString("category").isEmpty()){

                View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                TextView d1 = tr.findViewById(R.id.table_keys);
                TextView d2 = tr.findViewById(R.id.table_values);
                d1.setText("Category");

                d2.setText(myobj.getString("category"));

                event_table.addView(tr);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        try {
            if(!myobj.getString("price").isEmpty()){

                View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                TextView a1 = tr.findViewById(R.id.table_keys);
                TextView a2 = tr.findViewById(R.id.table_values);
                a1.setText("Price Range");

                a2.setText(myobj.getString("price")+" USD");


                event_table.addView(tr);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }


        try {
            if(!myobj.getString("status").isEmpty()){

                View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                TextView a1 = tr.findViewById(R.id.table_keys);
                TextView a2 = tr.findViewById(R.id.table_values);
                a1.setText("Ticket Status");

                a2.setText(myobj.getString("status"));

                event_table.addView(tr);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        try {
            if(!myobj.getString("ticket").isEmpty()){

                View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                TextView a1 = tr.findViewById(R.id.table_keys);
                TextView a2 = tr.findViewById(R.id.table_values);
                a1.setText("Buy Ticket At");
                a2.setClickable(true);
                a2.setMovementMethod(LinkMovementMethod.getInstance());
                String UrlText = "<a href=\""+myobj.getString("ticket")+"\" > Ticketmaster </a>";
                a2.setText(Html.fromHtml(UrlText));

//                a2.setText("Ticketmaster");

                event_table.addView(tr);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        try {
            if(!myobj.getString("seatmap").isEmpty()){

                View tr = getLayoutInflater ().inflate (R.layout.table_row, null, false);
                TextView a1 = tr.findViewById(R.id.table_keys);
                TextView a2 = tr.findViewById(R.id.table_values);
                a1.setText("Seat Map");


                a2.setClickable(true);
                a2.setMovementMethod(LinkMovementMethod.getInstance());
                String UrlText = "<a href=\""+myobj.getString("seatmap")+"\" style=\"teaxt-decoration:none;color:blue;\" > View Seat Map Here </a>";
//                a2.UnderlineText = false;
                a2.setText(Html.fromHtml(UrlText));

//                a2.setText("View Seat Map Here");

                event_table.addView(tr);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }


        return root;
    }
}