package com.flatondemand.user.fod.App;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkStatus {
    private static final String TAG = NetworkStatus.class.getSimpleName();
    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info==null){
            Log.d(TAG,"No internet connectivity");
            return  false;
        }else{
            if (info.isConnected()){
                Log.d(TAG , "Intenet Connection Available");
                return  true;
            }else{
                Log.d(TAG," internet connection");
                return true;
            }
        }
    }


}
