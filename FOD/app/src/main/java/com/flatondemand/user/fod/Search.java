package com.flatondemand.user.fod;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {

View view;
    private EditText locationInput;
    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       view= inflater.inflate(R.layout.fragment_search, container, false);
       locationInput=(EditText)view.findViewById(R.id.locationBox);
       locationInput.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent location = new Intent(getContext() , LocationSearch.class);
               location.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(location);
               getActivity().overridePendingTransition(android.support.design.R.anim.abc_grow_fade_in_from_bottom ,android.support.design.R.anim.abc_tooltip_exit);
           }
       });
       return view;
    }

}
