package com.flatondemand.user.fod;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flatondemand.user.fod.App.Constant;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class ReviewBooking extends AppCompatActivity  implements PaymentResultListener {
Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_booking);
        setTitle("Review Your Boooking");
        pay = (Button)findViewById(R.id.payNow);
        Checkout.preload(getApplicationContext());
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
    public void startPayment() {
        final Activity activity = this;
        int image = R.mipmap.ic_launcher; // Can be any drawable

        final Checkout co = new Checkout();
        co.setImage(image);
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Flatsondemand");
            options.put("description", "Booking Payment for FOD");
            //You can omit the image option to fetch the image from dashboard
            //options.put("image", "http://192.168.1.6/flat/assets/image/logo_small.png");
            options.put("currency", "INR");
            options.put("amount", 500*100);
            options.put("theme",new JSONObject("{color: '#1A283A'}"));
            JSONObject preFill = new JSONObject();
            preFill.put("email", "abc@gmail.com");
            preFill.put("contact","9835555982");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }

    }
}
