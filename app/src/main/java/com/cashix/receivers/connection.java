package com.cashix.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class connection extends BroadcastReceiver {
    private boolean isConnected = false , isStarted = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        isConnected = checkConection(context);
        isStarted = checkConection(context);
        Log.d("TAG", "onReceive: "+isConnected);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                isConnected = checkConection(context);
                if (!isConnected){
                    Intent i = new Intent();
                    i.setClassName("com.cashix", "com.cashix.UI.no_internet");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (!isStarted){
                        isStarted = true;
                        context.startActivity(i);
                    }
                }
                if (isConnected && isStarted){
                    isStarted = false;
                }
            }
        }, 500 , 1000);
    }

    private boolean checkConection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info !=null && info.isConnected()){
            return true;
        }
        return false;
    }
    public boolean reCheckConection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info !=null && info.isConnected()){
            return true;
        }
        return false;
    }
}
