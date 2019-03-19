package com.flatondemand.user.fod;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flatondemand.user.fod.App.NetworkStatus;
import com.flatondemand.user.fod.App.SQLiteHandler;
import com.flatondemand.user.fod.App.SessionManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity {
    RelativeLayout parent;
    SessionManager sessionManager;
    private static int SPLASH_TIME = 2500;
    SQLiteHandler sqLiteHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * calligraphy
         *
         * */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/roboto_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        sessionManager= new SessionManager(getApplicationContext());
        parent=(RelativeLayout)findViewById(R.id.parentPanel);
        sqLiteHandler =  new SQLiteHandler(getApplicationContext());
        //on start check for internet connection
        /*
        * then check whether user is logegin or not
        * check for current user
        * */
        if (NetworkStatus.isInternetAvailable(MainActivity.this)){
            //Toast.makeText(getApplicationContext() , "No internert ",Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (sessionManager.isLoggedIn()){
                        //direct to after logged in
                        Intent dash= new Intent(getApplicationContext() , Dash.class);
                        //dash.putExtra("userMobile",phonenumber);
                        dash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(dash);
                        finish();
                    }else{
                        //makeSnackBar("User is not Logged in" , parent);
                        /*
                         * send user to lgin screen panel
                         * */
                        Intent loginActivity= new Intent(getApplicationContext() , Login.class);
                        loginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginActivity);
                        overridePendingTransition(android.support.design.R.anim.abc_fade_in, android.support.design.R.anim.abc_fade_out);
                        finish();
                    }
                }
            },SPLASH_TIME);

        }else{
            //Toast.makeText(getApplicationContext() , "No internert ",Toast.LENGTH_LONG).show();
            makeSnackBar( "No Internet Conenction",parent);
        }



    }
    public  void makeSnackBar(String msg , RelativeLayout relativeLayout){
       Snackbar snackbar =Snackbar.make(relativeLayout ,msg ,Snackbar.LENGTH_SHORT);
       snackbar.show();
    }
}
/*
* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if user is already logged in or not
                if (session.isLoggedIn()) {
                    // User is already logged in. Take him to main activity
                    Intent intent = new Intent(getApplicationContext(), Dash.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent goToHome= new Intent(getApplicationContext() ,Home.class );
                    startActivity(goToHome);
                    finish();
                }

            }
        },SPLASH_TIME);
* */