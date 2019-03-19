package com.flatondemand.user.fod;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flatondemand.user.fod.App.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Login extends AppCompatActivity {
Button login;
EditText mobile;
String mobileNumber=null;
LinearLayout linearLayout;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/roboto_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        progressDialog= new ProgressDialog(this);
        mobile=(EditText)findViewById(R.id.inputmobile);
        linearLayout=(LinearLayout)findViewById(R.id.parentPanel);
        login=(Button)findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext() , OTP.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();*/
                mobileNumber=mobile.getText().toString().trim();
                if (TextUtils.isEmpty(mobileNumber)){
                    makeSnackBar("Enter Mobile Number" , linearLayout);
                    mobile.setError("Required");
                    mobile.requestFocus();
                }else {
                    init(mobileNumber);
                }

            }
        });
    }

    private void init(final String mobileNumber) {
        progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.setTitle("FOD");
        progressDialog.setMessage("Please Wait. While we send you OTP");
        progressDialog.show();
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Constant.SEND_OTP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Boolean error=jsonObject.getBoolean("error");
                    if (error==false){
                        Intent intent = new Intent(getApplicationContext() , OTP.class);
                        intent.putExtra("mobile", mobileNumber);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else{
                        String msg= jsonObject.getString("message") ;
                        makeSnackBar(msg , linearLayout);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                makeSnackBar("Error :  "+error.getMessage()  , linearLayout);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("mobile",mobileNumber);
                return  params;
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
