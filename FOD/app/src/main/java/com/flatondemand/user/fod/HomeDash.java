package com.flatondemand.user.fod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeDash extends AppCompatActivity {
CardView paymentCard , bookingCard , serviceCard , yourFod;
Button explore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dash);
        //card
        paymentCard=(CardView)findViewById(R.id.paymentCard);
        bookingCard=(CardView)findViewById(R.id.bookingCard);
        serviceCard=(CardView)findViewById(R.id.serviceCard);
        yourFod=(CardView)findViewById(R.id.yourFodCard);

//Button
        explore=(Button)findViewById(R.id.explore);
        paymentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent payment=new Intent(getApplicationContext() , Payments.class);
              payment.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(payment);
            }
        });
        bookingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToast("Booking CArd");
            }
        });
        serviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent dash= new Intent(getApplicationContext() , ServiceRequest.class);
                //dash.putExtra("userMobile",phonenumber);
                dash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dash);
            }
        });
        yourFod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash= new Intent(getApplicationContext() , KnowFod.class);
                //dash.putExtra("userMobile",phonenumber);
                dash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dash);
            }
        });
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash= new Intent(getApplicationContext() , LocationSearch.class);
                //dash.putExtra("userMobile",phonenumber);
                dash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dash);
              //  finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_account:
                //open profile
                Intent dash= new Intent(getApplicationContext() , Profile.class);
                //dash.putExtra("userMobile",phonenumber);
                dash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dash);
                break;
            case android.R.id.home:

                Intent dash1= new Intent(getApplicationContext() , Dash.class);
                //dash.putExtra("userMobile",phonenumber);
                dash1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dash1);
                break;
        }
       return  true;
    }

    public void makeToast(String msg){
        Toast.makeText(getApplicationContext() , msg     , Toast.LENGTH_LONG).show();
    }
}
