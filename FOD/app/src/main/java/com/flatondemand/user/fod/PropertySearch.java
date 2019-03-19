package com.flatondemand.user.fod;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.flatondemand.user.fod.App.PropertyAdapter;
import com.flatondemand.user.fod.App.SQLiteHandler;
import com.flatondemand.user.fod.Model.Property;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class PropertySearch extends AppCompatActivity {
    SQLiteHandler sqLiteHandler;
    HashMap<String ,String> map= new HashMap<String ,String >();
    private List<Property> propertyLists= new ArrayList<>();
    String currentLocation;
    RelativeLayout relativeLayout , loaderHolder;
    LinearLayout linearLayout;
    TextView loaderLocation , locationTextView;
    PropertyAdapter propertyAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_search);


        sqLiteHandler= new SQLiteHandler(getApplicationContext());
        map=sqLiteHandler.getLocation();
        relativeLayout=(RelativeLayout)findViewById(R.id.emptyIconwrapper);
        loaderHolder=(RelativeLayout)findViewById(R.id.loaderHolder);
        linearLayout=(LinearLayout)findViewById(R.id.propertyHolder);
        currentLocation=map.get("location");
        loaderLocation=(TextView)findViewById(R.id.progress_location);
        locationTextView=(TextView)findViewById(R.id.locationText);
        recyclerView = (RecyclerView)findViewById(R.id.listProperty);
        recyclerView.setHasFixedSize(true);
        //LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        try{
            setTitle("Avaliable FOD ");
            loaderLocation.setText("Searching FOD for "+currentLocation);
            jsoncall(currentLocation);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void jsoncall(final String currentLocation) {
        StringRequest request= new StringRequest(Request.Method.POST, Constant.PROPERTY_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    String count=jsonObject.getString("count");
                    int counter=Integer.parseInt(count);
                    if(counter>0){
                    loaderHolder.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                        locationTextView.setText(count+" Result Found for "+currentLocation ) ;
                        JSONArray arrayList= new JSONArray();
                        arrayList=jsonObject.getJSONArray("records");
                        for(int i=0;i<arrayList.length() ; i++){
                            Property property=new Property();
                            JSONObject jsonObjectProperty= new JSONObject();
                           // PropertyList propertyList= new PropertyList();
                            jsonObjectProperty= arrayList.getJSONObject(i);
                            String name=jsonObjectProperty.getString("property");
                            String price=jsonObjectProperty.getString("price");
                            String coverImage=jsonObjectProperty.getString("coverImage");
                            String address=jsonObjectProperty.getString("adress");
                            String uid=jsonObjectProperty.getString("uid");
                            property.setAddress(address);
                            property.setProperty(name);
                            property.setCoverImage(coverImage);
                            property.setPrice(price);
                            property.setUid(uid);
                            propertyLists.add(property);


                        }
                        //Toast.makeText(getApplicationContext() ,)

                    }else{
                        loaderHolder.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                    setRvadapter(propertyLists);
                   // setRvadapter(propertyLists);
                    //Toast.makeText(getApplicationContext() , count.toString() , Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext() , error.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> map= new HashMap<>();
                map.put("location", currentLocation);
                return map;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void setRvadapter(List<Property> propertyLists) {

        PropertyAdapter propertyAdapter = new PropertyAdapter(this , propertyLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(propertyAdapter);
    }
}
