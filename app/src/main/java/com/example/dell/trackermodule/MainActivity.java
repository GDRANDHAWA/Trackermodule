package com.example.dell.trackermodule;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dell.trackermodule.R.id.map;


public class MainActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting reference to the SupportMapFragmegetMap();nt of activity_main.xml
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);

        // Getting GoogleMap object from the fragment
        fm.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                JsonArrayRequest req = new JsonArrayRequest("http://192.168.1.102/Cartracking/locations.php", new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0; i <= response.length(); i++)
                        {
                            JSONObject obj= null;
                            try {
                                obj = (JSONObject) response.get(i);

                                String lati=obj.getString("Latitude");
                                String longi = obj.getString("Longitude");

                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.valueOf(lati),Double.valueOf(longi)))
                                        .title(Double.valueOf(lati).toString() + "," +Double.valueOf(longi).toString()));
                                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                                LatLng coordinate= new LatLng(Double.valueOf(lati),Double.valueOf(longi));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate,16));



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


                AppController app = new AppController(MainActivity.this);
                app.addToRequestQueue(req);

            }
        });










    }
}