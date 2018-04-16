package com.example.fengcheng.main.bmwassignment;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    TextView sortTypeTv;
    double mLastlatitude, mLastlongtitude;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    JsonArrayRequest jsonArrayRequest;
    private String url = "http://localsearch.azurewebsites.net/api/Locations";

    Button byNameBtn, byArrivalTimeBtn, byDistanceBtn;
    List<LocationBean> locationList;
    RequestQueue mRequestQueue;
    RecyclerView locationRv;
    RvAdapter rvAdapter;
    private static final String TAG = "MainActivity";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        initView();

        fetchData(url);

        clickListener();


    }

    private void clickListener() {
        byNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(locationList, new NameComparator());
                rvAdapter.notifyDataSetChanged();
                sortTypeTv.setText(R.string.sort_by_name);

            }
        });

        byArrivalTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(locationList, new TimeComparator());
                rvAdapter.notifyDataSetChanged();
                sortTypeTv.setText(R.string.sort_by_time);


            }
        });

        byDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(locationList, new DistanceComparator(mLastlatitude, mLastlongtitude));
                rvAdapter.notifyDataSetChanged();
                sortTypeTv.setText(R.string.sort_by_distance);


            }
        });
    }

    /**
     * @param url given url
     * utilize volley library to do a Get request fetch data from given url
     */

    private void fetchData(String url) {

        locationList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Toast.makeText(getBaseContext(), response.toString(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = response;
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LocationBean myLocation = new LocationBean(
                                jsonObject.getString("ID"),
                                jsonObject.getString("Name"),
                                jsonObject.getString("Latitude"),
                                jsonObject.getString("Longitude"),
                                jsonObject.getString("Address"),
                                jsonObject.getString("ArrivalTime"));
                        locationList.add(myLocation);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                rvAdapter = new RvAdapter(MainActivity.this, locationList);
                locationRv.setHasFixedSize(true);
                locationRv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                locationRv.setAdapter(rvAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(jsonArrayRequest);
    }

    /**
     * initiate ui widget here
     */

    private void initView() {
        locationRv = findViewById(R.id.recycler);
        byNameBtn = findViewById(R.id.by_name);
        byArrivalTimeBtn = findViewById(R.id.by_arrivaltime);
        byDistanceBtn = findViewById(R.id.by_distance);
        sortTypeTv = findViewById(R.id.title_tv);
        sortTypeTv.setText(R.string.sort_type);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected: ");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnection");
        googleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastlatitude = location.getLatitude();
        mLastlongtitude = location.getLongitude();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



}
