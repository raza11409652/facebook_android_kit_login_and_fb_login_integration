package com.flatondemand.user.fod.App;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.flatondemand.user.fod.R;

public class GenericTextWatcher implements TextWatcher {
    private View view;
    public GenericTextWatcher(View view)
    {
        this.view = view;
    }
    @Override
    public void afterTextChanged(Editable editable) {
        // TODO Auto-generated method stub
        String text = editable.toString();
        switch(view.getId())
        {
            case R.id.first:
                if (text.length()==1){

                }

        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }
}
