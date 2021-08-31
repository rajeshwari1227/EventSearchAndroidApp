package com.example.ticketmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>{
 Context context;
 List<EventListClass> eventlist;
    private RequestQueue mQueue;
    private JSONObject res,event ;
    List<String> res_list = new ArrayList<>();
    SharedPreferences sharedpreferences;




    public String MyPREFERENCES = "favorites" ;
    public String Name = "nameKey";
    public String Category = "category";
    public String date = "date";


    public RecycleViewAdapter(Context context, List<EventListClass> eventlist) {
        this.context = context;
        this.eventlist = eventlist;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_layout,parent,false);
        MyViewHolder holder = new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
//        holder.event_name.setText(jsonArray[0][0]);
        holder.event_name.setText(eventlist.get(position).getEventName());
        holder.event_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getVenueDetails(eventlist.get(position).getVenueId());
                getEventDetails(eventlist.get(position).getEventId(), eventlist.get(position).getVenueId(),eventlist.get(position).getEventName(),eventlist.get(position).getVenueId(),eventlist.get(position).getCategory(),eventlist.get(position).getDate());
                System.out.println(res_list);
                System.out.println("hello");
                System.out.println(event);
//                Intent intent = new Intent(context, EventDetails.class);
//                intent.putExtra("searchResults",  "FALSE");
//                context.startActivity(intent);
            }
        });

        holder.event_date.setText(String.valueOf(eventlist.get(position).getDate()));
        holder.venue_name.setText(String.valueOf(eventlist.get(position).getVenue()));
//        Glide.with(this.context).load()
        String cat = eventlist.get(position).getCategory();
        if (cat.contains("Music")) {
            Glide.with(this.context).load("").error(R.drawable.music_icon).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.cat_img);
        } else if (cat.contains("Miscellaneous")) {
            Glide.with(this.context).load("").error(R.drawable.miscellaneous_icon).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.cat_img);
        } else if (cat.contains("Arts & Theatre")) {
            Glide.with(this.context).load("").error(R.drawable.art_icon).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.cat_img);
        } else if (cat.contains("Sports")) {
            Glide.with(this.context).load("").error(R.drawable.ic_sport_icon).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.cat_img);
        } else if (cat.contains("Film")) {
            Glide.with(this.context).load("").error(R.drawable.film_icon).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.cat_img);
        } else {
            Glide.with(this.context).load("").error(R.drawable.miscellaneous_icon).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.cat_img);
        }
//        Glide.with(this.context).load("").error(R.drawable.heart_outline_black).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.favorites);
//        holder.favorites.setTag("not_fav");



        SharedPreferences sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String sharedPreferencesName = eventlist.get(position).getEventId();

        if (sharedPreferences.contains(sharedPreferencesName)) {
            holder.favorites.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_fill_red));

        } else {
            holder.favorites.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_outline_black));

        }

        holder.favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                JSONObject mydata = new JSONObject();
                try {
                    mydata.put("eventname",eventlist.get(position).getEventName());
                    mydata.put("eventid",eventlist.get(position).getEventId());
                    mydata.put("category",eventlist.get(position).getCategory());
                    mydata.put("date",eventlist.get(position).getDate());
                    mydata.put("venue",eventlist.get(position).getVenue());
                    mydata.put("venueid",eventlist.get(position).getVenueId());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (sharedPreferences.contains(sharedPreferencesName)) {
                    Toast.makeText(context, sharedPreferencesName + " was removed from favorites", Toast.LENGTH_SHORT).show();
                     holder.favorites.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_outline_black));


                    editor.remove(sharedPreferencesName);
                    editor.commit();
                } else {
                    Toast.makeText(context, sharedPreferencesName + " was added to favorites", Toast.LENGTH_SHORT).show();
                    holder.favorites.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_fill_red));
                    editor.putString(sharedPreferencesName, mydata.toString());
                    editor.apply();

                }
            }


//        holder.cat_img
        });
    }

    @Override
    public int getItemCount() {
        return eventlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        ImageView cat_img;
        TextView event_name;
        TextView event_date;
        TextView venue_name;
        ImageView favorites;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cat_img = itemView.findViewById(R.id.category_img);
            event_name = itemView.findViewById(R.id.event_name);
            event_date = itemView.findViewById(R.id.event_time);
            venue_name = itemView.findViewById(R.id.venue_txtbox);
            favorites = itemView.findViewById(R.id.favorites);
            mQueue = Volley.newRequestQueue(context);
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



        }
    }


    public void getEventDetails(String eventId,String venueId,String eventName,String venue,String date,String category){



//        String url = "https://eng-empire-315722.wl.r.appspot.com/venueDetails?venue="+venueId;
        String url = "https://eng-empire-315722.wl.r.appspot.com/eventDetails?event="+eventId;
        System.out.println(url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);

//                res_list.add(response.toString());
                Intent intent = new Intent(context, EventDetails.class);
                intent.putExtra("eventdetails",  response.toString());
                intent.putExtra("eventId",eventId);
                intent.putExtra("venueId",  venueId);
                intent.putExtra("venue",  venue);
                intent.putExtra("date",  date);
                intent.putExtra("category",  date);
                intent.putExtra("eventname",  eventName);
                context.startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        mQueue.add(request);
    }


}
