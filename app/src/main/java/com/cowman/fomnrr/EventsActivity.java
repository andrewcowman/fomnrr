package com.cowman.fomnrr;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cowman.adapters.EventsAdapter;
import com.cowman.models.Event;
import com.cowman.utils.VolleyController;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EventsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerView = findViewById(R.id.events_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = findViewById(R.id.loading_spinner);

        initializeEvents();
    }

    private void initializeEvents() {
        JsonArrayRequest request = new JsonArrayRequest("http://api.cowman.a2hosted.com/api/events", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Event> list = new ArrayList<Event>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        System.out.println("----------------------------------" + DateTime.parse(obj.getString("date")).toString());
                        list.add(new Event(obj.getString("name"), DateTime.parse(obj.getString("date")),
                                obj.getString("description"), obj.getString("startLocation"), obj.getString("endLocation")));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                recyclerAdapter = new EventsAdapter(EventsActivity.this, list);
                recyclerView.setAdapter(recyclerAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("-------------------------------------error" + error.getMessage());
                progressBar.setVisibility(View.INVISIBLE);

                // set view to generic error
                setContentView(R.layout.view_error);
            }
        });

        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
