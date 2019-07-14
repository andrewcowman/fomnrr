package com.cowman.fomnrr;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cowman.models.Site;
import com.cowman.utils.VolleyController;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initializeMap();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(42.8489722, -97.4680556), 10, 0, 0)));
    }

    //http://10.0.2.2:56683

    private void initializeMap() {
        JsonArrayRequest request = new JsonArrayRequest("http://api.cowman.a2hosted.com/api/sites", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Site> list = new ArrayList<Site>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        list.add(new Site(obj.getInt("id"), obj.getString("title"), obj.getDouble("latitude"), obj.getDouble("longitude")));
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                for(int i = 0; i < list.size(); i++) {
                    MarkerOptions options = new MarkerOptions();
                    options.position(new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude()));

                    Marker marker = mMap.addMarker(options);
                    marker.setTag(list.get(i).getId());
                }

                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude()), 8f));
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent intent = new Intent(MapsActivity.this, SiteActivity.class);
                        intent.putExtra("siteId", marker.getTag().toString());
                        startActivity(intent);
                        return false;
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("-------------------------------------error" + error.getMessage());

                // set view to generic error
                setContentView(R.layout.view_error);
            }
        });

        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}