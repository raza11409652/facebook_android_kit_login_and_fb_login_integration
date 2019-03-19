package com.flatondemand.user.fod;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flatondemand.user.fod.App.Location;
import com.flatondemand.user.fod.App.LocationActivityAdapter;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class LocationSearch extends AppCompatActivity {
    LocationActivityAdapter activityAdapter;
    SearchView searchView;
    RecyclerView recyclerView;
    List<Location> list = new ArrayList<>();
    Button btnLocation;
    Boolean isLocationPermission = false;
    protected LocationManager locationManager;
    // flag for GPS status
    public boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    android.location.Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
        setTitle("Locate Yourself");
        list.add(new Location("Deep Nagar", R.drawable.loc_pin));
        list.add(new Location("Law gate ", R.drawable.loc_pin));
        list.add(new Location("jalandhar", R.drawable.loc_pin));
        list.add(new Location("Phagwara", R.drawable.loc_pin));
        list.add(new Location("Jalandhar Cant", R.drawable.loc_pin));
        recyclerView = (RecyclerView) findViewById(R.id.locationList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        activityAdapter = new LocationActivityAdapter(list, LocationSearch.this);
        recyclerView.setAdapter(activityAdapter);
        //Button
        btnLocation = (Button) findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isLocationPermission = checkLocationPermission();
                if (isLocationPermission == false) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(LocationSearch.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                    }
                } else {
                    getLocation();
                }
            }
        });
        isLocationPermission = checkLocationPermission();
        if (isLocationPermission == false) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void getLocation() {
        try {
            Intent propertySearch = new Intent(getApplicationContext() , PropertySearch.class);
            propertySearch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(propertySearch);
        }catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext() , "lat :"+latitude+"long:"+longitude + location,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_loc, menu);
        final MenuItem menuItem=menu.findItem(R.id.search);
        searchView=(SearchView) menuItem.getActionView();
        changeSearchViewTextColor(searchView);
        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!searchView.isIconified())
                {
                    searchView.setIconified(true);
                }
                menuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                final  List<Location> filtermodelist=filter(list,s);
                activityAdapter.setFilter(filtermodelist);
                return true;

            }

        });


        return true;
    }
    private List<Location> filter(List<Location> pl,String query) {
        query=query.toLowerCase();
        final List<Location> filteredModeList=new ArrayList<>();
        for (Location model:pl)
        {
            final String text=model.getName().toLowerCase();
            if (text.startsWith(query))
            {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }
    private void changeSearchViewTextColor(View view) {
        if(view !=null)
        {
            if(view instanceof TextView)
            {
                ((TextView) view).setTextColor(Color.WHITE);
            }else if(view instanceof ViewGroup){
                ViewGroup viewGroup= (ViewGroup)view;
                for(int i=0; i <viewGroup.getChildCount() ; i++)
                {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }
}
