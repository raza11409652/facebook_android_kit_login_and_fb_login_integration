package com.flatondemand.user.fod;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flatondemand.user.fod.App.Constant;
import com.flatondemand.user.fod.App.SQLiteHandler;
import com.flatondemand.user.fod.App.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class OTP extends AppCompatActivity {
    EditText et1,et2,et3,et4;
    TextView mobilenumber ,edit;
    LinearLayout linearLayout;
    Button verify;
    String otpVal , phonenumber;
    ProgressDialog progressDialog;
    SessionManager session;
    SQLiteHandler sqLiteHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        phonenumber = getIntent().getStringExtra("mobile");
        progressDialog= new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        session= new SessionManager(getApplicationContext());
        sqLiteHandler= new SQLiteHandler(getApplicationContext());
        //editbox
        et1 = findViewById(R.id.first);
        et2 = findViewById(R.id.second);
        et3 = findViewById(R.id.third);
        et4 = findViewById(R.id.fourth);
        //textviews
        mobilenumber=(TextView)findViewById(R.id.mobile_number_wrapper);
        mobilenumber.setText("On +91-"+phonenumber);
        edit=(TextView)findViewById(R.id.edit);
        /*Button*/
        verify=(Button)findViewById(R.id.btn_verify);
        //linear lauout
        linearLayout=(LinearLayout)findViewById(R.id.parentPanel);
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et1.getText().toString().length() ==1){
                    et2.requestFocus();
                }
            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et2.getText().toString().length() ==1){
                    et3.requestFocus();
                }
            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et3.getText().toString().length() ==1){
                    et4.requestFocus();
                }
            }
        });
        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) { otpVal=null;
            otpVal=et1.getText().toString();
            otpVal+=et2.getText().toString();
            otpVal+=et3.getText().toString();
            otpVal+=et4.getText().toString();
                //Toast.makeText(getApplicationContext() , otpVal  ,Toast.LENGTH_LONG).show();

            }
        });

        //edit on click listener
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login= new Intent(getApplicationContext() , Login.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                finish();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpVal==null){
                makeSnackBar("Please Enter OTP" , linearLayout) ;
                }else if (otpVal.length()<4){
                makeSnackBar("Please Enter 4 Digit OTP" ,linearLayout);
                }else{
                    verifyOtp(otpVal);
                }

            }
        });
    }

    private void verifyOtp(final String otpVal) {
        progressDialog.setMessage("Please Wait while we verify your OTP");
        progressDialog.show();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Constant.VERIFY_OTP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject= new JSONObject(response);
                    Boolean error=jsonObject.getBoolean("error");
                    if(error == false){
                        isUserProfileisCompleted(phonenumber);
                    }else{
                        ///Toast.makeText(getApplicationContext() , jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        makeSnackBar(jsonObject.getString("message"),linearLayout);
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
                Map<String,String> params= new HashMap<>();
                params.put("otp",otpVal);
                params.put("mobile",phonenumber);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void isUserProfileisCompleted(final String phonenumber) {

        StringRequest stringRequest= new StringRequest(Request.Method.POST, Constant.USER_PROFILE_COMPLETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    Boolean error=jsonObject.getBoolean("error");
                    if(error ==false)
                    {
                        Boolean isProfile=jsonObject.getBoolean("isProfileComplete");
                        if(isProfile == true)
                        {
                            String name=jsonObject.getString("name");
                            String email=jsonObject.getString("email");
                            session.setLoggedInMobile(phonenumber);
                            session.setLogin(true);
                            sqLiteHandler.insertUser(name , email , phonenumber);
                            Intent dash= new Intent(getApplicationContext() , Dash.class);
                            dash.putExtra("userMobile",phonenumber);
                            dash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dash);
                            finish();

                        }
                        else{
                            //go to profile creation
                            Intent profileCreation = new Intent(getApplicationContext() , ProfileCreation.class);
                            profileCreation.putExtra("mobile",phonenumber);
                            profileCreation.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(profileCreation);
                            finish();
                        }
                    }else{
                        Toast.makeText(getApplicationContext() , jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Map<String , String> param=new HashMap<>();
                param.put("mobile",phonenumber);
                return  param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public  void makeSnackBar(String Msg , LinearLayout linearLayout){
        Snackbar snackbar= Snackbar.make(linearLayout , Msg , Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
