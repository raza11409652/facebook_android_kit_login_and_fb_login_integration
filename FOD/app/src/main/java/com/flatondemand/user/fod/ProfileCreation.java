package com.flatondemand.user.fod;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flatondemand.user.fod.App.Constant;
import com.flatondemand.user.fod.App.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileCreation extends AppCompatActivity {
    String phone;
    Button save;
    EditText emailInput , nameInput;
    String name , email;
    ProgressDialog progressDialog;
    private SessionManager session;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);
        phone=getIntent().getStringExtra("mobile");
        save=(Button)findViewById(R.id.save);
        emailInput=(EditText)findViewById(R.id.email);
        nameInput=(EditText)findViewById(R.id.name);
        Toast.makeText(getApplicationContext() , phone ,Toast.LENGTH_LONG).show();
        linearLayout=(LinearLayout)findViewById(R.id.parentPanel);
        // Session manager
        session = new SessionManager(getApplicationContext());
        progressDialog= new ProgressDialog(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=nameInput.getText().toString().trim();
                email=emailInput.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    nameInput.setError("Name is required");
                    return;
                }else if(TextUtils.isEmpty(email)){
                    emailInput.setError("Email is required");
                }else if(isValidEmail(email)){
                    createProfile(name , email , phone);
                }else{
                   // Toast.makeText(getApplicationContext() , "",Toast.LENGTH_SHORT).show();
                    makeSnackBar("Email is invalid" , linearLayout);

                }
            }
        });
    }

    private void createProfile(final String name, final String email, final String phone) {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        //Toast.makeText(getApplicationContext() , name +email + phone , Toast.LENGTH_SHORT).show();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Constant.USER_PROFILE_CREATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
        progressDialog.dismiss();
                try {
                    Log.d("Error",response);
                    progressDialog.dismiss();
                    JSONObject jsonObject= new JSONObject(response);
                    Boolean error=jsonObject.getBoolean("error");
                    if(error==false)
                    {
                        session.setLoggedInMobile(phone);
                        session.setLogin(true);

                        Intent dash= new Intent(getApplicationContext() , Dash.class);
                        dash.putExtra("userMobile",phone);
                        dash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(dash);
                        finish();

                    }else{
                       makeSnackBar(jsonObject.getString("message") , linearLayout);
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
                Map<String , String>param=new HashMap<>();
                param.put("mobile",phone);
                param.put("name",name);
                param.put("email", email);
                return  param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public  void makeSnackBar(String Msg , LinearLayout linearLayout){
        Snackbar snackbar= Snackbar.make(linearLayout , Msg , Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
