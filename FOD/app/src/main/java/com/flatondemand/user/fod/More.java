package com.flatondemand.user.fod;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flatondemand.user.fod.App.Constant;


/**
 * A simple {@link Fragment} subclass.
 */
public class More extends Fragment {
CardView feedBack ,notification ,listProperty , policy ,terms;
View view;
    public More() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_more, container, false);
        feedBack=(CardView)view.findViewById(R.id.feedback);
        notification=(CardView)view.findViewById(R.id.notification);
        listProperty=(CardView)view.findViewById(R.id.listProperty);
        policy=(CardView)view.findViewById(R.id.policy);
        terms=(CardView)view.findViewById(R.id.terms);
        listProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext() , WebView.class);
                intent.putExtra("URL", Constant.listProperty);
                intent.putExtra("Title", "List Your Property");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext() , WebView.class);
                intent.putExtra("URL", Constant.policy);
                intent.putExtra("Title", "Policy");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext() , WebView.class);
                intent.putExtra("URL", Constant.terms);
                intent.putExtra("Title", "Terms And Condition");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        return view;
    }

}
