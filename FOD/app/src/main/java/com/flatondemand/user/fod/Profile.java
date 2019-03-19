package com.flatondemand.user.fod;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.flatondemand.user.fod.App.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private ShimmerFrameLayout mShimmerViewContainer ,shimmerFrameLayout;
    RelativeLayout relativeLayout , noBooking;
    TextView nameView, mobileView,emailView , fatherName , address, fatherMobile ;
    TextView fodName , fodAddress , fodContactPerson , fodContactMobile;
    String userMobile ="9835555982";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Account");
        mShimmerViewContainer =(ShimmerFrameLayout)findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout=(ShimmerFrameLayout)findViewById(R.id.shimmer_view_container_fod);
        //Toast.makeText(getContext() , ""+map ,Toast.LENGTH_LONG).show();
        relativeLayout=(RelativeLayout)findViewById(R.id.no_detailsFoundsLayout);
        noBooking=(RelativeLayout)findViewById(R.id.no_fodFoundsLayout);
        //Text view
        nameView=(TextView)findViewById(R.id.name);
        mobileView=(TextView)findViewById(R.id.mobile);
        emailView=(TextView)findViewById(R.id.emailUser);
        fatherName=(TextView)findViewById(R.id.fatherName);
        address=(TextView)findViewById(R.id.address);
        fatherMobile=(TextView)findViewById(R.id.fatherMobile);
        fodName=(TextView)findViewById(R.id.fod_name);
        fodAddress=(TextView)findViewById(R.id.fod_address);
        fodContactPerson=(TextView)findViewById(R.id.fod_contact_name);
        fodContactMobile=(TextView)findViewById(R.id.fod_contact_mobile);
        try{
            mShimmerViewContainer.startShimmerAnimation();
            fetchUserDetails(userMobile);
            fetchFodDetails(userMobile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void fetchFodDetails(final String userMobile) {
        shimmerFrameLayout.startShimmerAnimation();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Constant.CURRENT_FOD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.d("TAG" , response);
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    Boolean error=jsonObject.getBoolean("error");
                    if (error == false){
                        shimmerFrameLayout.stopShimmerAnimation();
                        String propertyName = jsonObject.getString("propertyName");
                        String propertyAddress=jsonObject.getString("propertyAddress");
                        String caretaker=jsonObject.getString("propertyCareTakerName");
                        String mobile=jsonObject.getString("propertyCareTakerMobile");
                        fodName.setBackgroundColor(Color.parseColor("#ffffff"));
                        fodAddress.setBackgroundColor(Color.parseColor("#ffffff"));
                        fodContactPerson.setBackgroundColor(Color.parseColor("#ffffff"));
                        fodContactMobile.setBackgroundColor(Color.parseColor("#ffffff"));
                        fodName.setText(propertyName);
                        fodAddress.setText(propertyAddress);
                        fodContactPerson.setText("MR. "+caretaker);
                        fodContactMobile.setText("+91-"+mobile);
                    }else{
                        shimmerFrameLayout.setVisibility(View.GONE);
                        noBooking.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String , String> map= new HashMap<>();
                map.put("mobile" , userMobile);
                return map;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void fetchUserDetails(final String userMobile) {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Constant.USER_COMPLETE_DETAILS, new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                Log.d("TAG" , response);
                mShimmerViewContainer.stopShimmerAnimation();
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    Boolean error=jsonObject.getBoolean("error");
                    if (error == false){
                        fatherName.setBackgroundColor(Color.parseColor("#ffffff"));
                        fatherMobile.setBackgroundColor(Color.parseColor("#ffffff"));
                        address.setBackgroundColor(Color.parseColor("#ffffff"));
                        String fatherNAme = jsonObject.getString("fatherName");
                        String father_Mobile = jsonObject.getString("fatherMobile");
                        String addressV=jsonObject.getString("address");
                        fatherName.setText(fatherNAme);
                        fatherMobile.setText(father_Mobile);
                        address.setText(addressV);
                    }else{
                        mShimmerViewContainer.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String , String> map= new HashMap<>();
                map.put("mobile" , userMobile);
                return map;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
