package com.flatondemand.user.fod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;
import com.flatondemand.user.fod.App.Constant;
import com.flatondemand.user.fod.App.InclusionAdapter;
import com.flatondemand.user.fod.App.SQLiteHandler;
import com.flatondemand.user.fod.Model.InclusionProperty;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class PropertyView extends AppCompatActivity implements SlyCalendarDialog.Callback {
    RecyclerView recyclerViewInclusion;
    SQLiteHandler sqLiteHandler;
    CardView cardView;
    Animation animation;
    ImageView imageView;
    TextView propertyName , propertyAdd,securityMoney , priceView;
    String CoverImage , adress, fulladdress , uid , price , property;
    RequestOptions options ;
    EditText startDate , endDate;
    List<InclusionProperty> inclusionProperties = new ArrayList<>();
    HashMap<String , String>map= new HashMap<>();
    Button book , visit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_view);
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar);

        sqLiteHandler =  new SQLiteHandler(getApplicationContext());
        map = sqLiteHandler.getProperty();
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.image_logo_back)
                .error(R.drawable.image_logo_back);
        /*
        *  map.put("propertyName",cursor.getString(1));
            map.put("propertyCoverImage",cursor.getString(2));
            map.put("propertyPrice",cursor.getString(3));
            map.put("propertyUid",cursor.getString(4));
            map.put("propertyAdd",cursor.getString(5));
        * */
        //Edit text
        startDate = (EditText)findViewById(R.id.startDate);

        property = map.get("propertyName");
        CoverImage = map.get("propertyCoverImage");
        adress = map.get("propertyAdd");
        price = map.get("propertyPrice");
        uid = map.get("propertyUid");
        property = property.toLowerCase();
        property = StringUtils.capitalize(property);
        setSupportActionBar(toolbar);
       // toolbar.setLogo(R.drawable.icon);
        toolbar.setTitle(property);
        if (getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Image View
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setContentDescription(property);
//cardview
        cardView = (CardView)findViewById(R.id.propertyCard);
        animation = AnimationUtils.loadAnimation(getApplicationContext() ,R.anim.fade_up);
        cardView.setAnimation(animation);
            //Textview
        propertyName = (TextView)findViewById(R.id.propertyName);
        propertyAdd = (TextView)findViewById(R.id.propertyAdd);
        priceView = (TextView)findViewById(R.id.monthly_payment);
        securityMoney = (TextView)findViewById(R.id.security_money);
        //Button
        book = (Button)findViewById(R.id.fod_booking);
        visit = (Button)findViewById(R.id.visitBook);
        //Inclusion RecyclerView
        recyclerViewInclusion = (RecyclerView)findViewById(R.id.inclusion);
        recyclerViewInclusion.setHasFixedSize(true);
       // Toast.makeText(getApplicationContext() , ""+map , Toast.LENGTH_SHORT).show();
        getInclusion(uid);

        propertyName.setText(property);
        propertyAdd.setText(adress);
        priceView.setText("Rs. "+price);
        securityMoney.setText("Rs. "+price);
        Glide.with(getApplicationContext()).load(CoverImage).apply(options).into(imageView);

        //Image gallery
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext() , "Image gallery" , Toast.LENGTH_SHORT).show();
            }
        });
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext() , VisitBooking.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_up , android.support.design.R.anim.abc_fade_out);
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext() , ReviewBooking.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_up , android.support.design.R.anim.abc_fade_out);
            }
        });
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlyCalendarDialog()
                        .setSingle(false)
                        .setCallback(PropertyView.this)
                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
            }
        });
    }
    private void getInclusion(final String uid) {

        //Toast.makeText(getApplicationContext() , uid.toString() , Toast.LENGTH_SHORT).show();
        StringRequest stringRequest= new StringRequest(com.android.volley.Request.Method.POST, Constant.INCLUSION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // progressDialog.dismiss();
                // Toast.makeText(getApplicationContext() , response , Toast.LENGTH_SHORT).show();
                Log.d("response",response);
                // Toast.makeText(getApplicationContext() , ""+map,Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    JSONArray arrayList= new JSONArray();
                    arrayList=jsonObject.getJSONArray("records");
                    // Toast.makeText(getApplicationContext() , ""+arrayList.length(),Toast.LENGTH_SHORT).show();
                    //JSONObject jsonObjectProperty= new JSONObject();
                    for (int i=0;i<arrayList.length() ; i++){
                        JSONObject jsonObjectProperty= new JSONObject();
                        InclusionProperty inclusionProperty= new InclusionProperty();
                        jsonObjectProperty= arrayList.getJSONObject(i);
                        String text=jsonObjectProperty.getString("text");
                        String image=jsonObjectProperty.getString("image");
                        inclusionProperty.setText(text);
                        inclusionProperty.setImage(Constant.ROOT_IMAGE_URL+image);
                        inclusionProperties.add(inclusionProperty);
                    }

                    setInclusionAdapter(inclusionProperties);
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
                Map<String , String>map= new HashMap<>();
                map.put("uid" , uid);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void setInclusionAdapter(List<InclusionProperty> inclusionProperties) {

        InclusionAdapter inclusionAdapter= new InclusionAdapter(this , inclusionProperties);
        recyclerViewInclusion.setLayoutManager( new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewInclusion.setAdapter(inclusionAdapter);
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onDataSelected(Calendar calendar, Calendar calendarend, int i, int i1) {

    }
}
