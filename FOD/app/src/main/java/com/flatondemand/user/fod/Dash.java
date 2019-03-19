package com.flatondemand.user.fod;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.flatondemand.user.fod.App.SQLiteHandler;
import com.flatondemand.user.fod.App.SessionManager;

import java.util.HashMap;
import java.util.Map;

public class Dash extends AppCompatActivity {
    private ActionBar toolbar;
    Map<String,String> userMap= new HashMap<>();
    SessionManager sessionManager;
    SQLiteHandler sqLiteHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
            sessionManager= new SessionManager(this);
            sqLiteHandler= new SQLiteHandler(this);
            userMap=sqLiteHandler.getUserDetails();
        Toast.makeText(getApplicationContext() , ""+userMap , Toast.LENGTH_SHORT).show();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new Search());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_explore:
                    loadFragment(new Search());
                    return true;
                case R.id.navigation_dash:
                    Intent intent= new Intent(getApplicationContext() , HomeDash.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                    overridePendingTransition(android.support.design.R.anim.abc_fade_in, android.support.design.R.anim.abc_fade_out);
                    return true;
                case R.id.navigation_more:
                    loadFragment(new More());
                    return true;
            }
            return false;
        }
    };
    @Override
    public void onBackPressed()
    {
        return;
    }
}

