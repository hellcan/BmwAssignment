package com.example.fengcheng.main.bmwassignment;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * google map activity display address with marker on map
 */


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String latitude;
    String longitude;
    String id;
    String name;
    String address;
    String arrivaltime;

    TextView name_tv;
    TextView location_tv;
    TextView arrivalTime_tv;
    TextView coordiance_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //get data from intent
        //this is latitude , longitude
        latitude = getIntent().getStringExtra("lati");
        longitude = getIntent().getStringExtra("longti");
        //address
        address = getIntent().getStringExtra("address");
        //name
        name = getIntent().getStringExtra("name");
        //arrival time
        arrivaltime = getIntent().getStringExtra("arrivetime");


        location_tv = findViewById(R.id.location_tv);
        name_tv = findViewById(R.id.name_tv);
        arrivalTime_tv = findViewById(R.id.arrivateTime_tv);
        coordiance_tv = findViewById(R.id.coordiance);

        //get item information from MainActivity
        location_tv.setText("location: " + address);
        name_tv.setText("name: " + name);
        arrivalTime_tv.setText("arrival time: " + arrivaltime);
        coordiance_tv.setText("latitude: " + latitude + "   longitude: " + longitude);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker on map
        LatLng sydney = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        mMap.addMarker(new MarkerOptions().position(sydney).title(address));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
