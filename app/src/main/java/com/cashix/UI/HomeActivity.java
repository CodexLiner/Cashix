package com.cashix.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.cashix.R;
import com.cashix.UIFragments.HomeFragment;
import com.cashix.constants.STATIC;
import com.cashix.network.AsyncResponse;
import com.cashix.network.BackendCall;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements AsyncResponse {
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.MainFrame, HomeFragment.class, null)
                .commit();
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void Result(JSONObject jsonObject) { }

    @Override
    public void onBackPressed() {
        FragmentManager fn = getSupportFragmentManager();
        if (fn.getBackStackEntryCount()==0){
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Snackbar snackbar  =  Snackbar.make(findViewById(R.id.HomeLayout) , "0", Snackbar.LENGTH_SHORT);
            View customSnackView = getLayoutInflater().inflate(R.layout.snackbar, null);
            snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
            Snackbar.SnackbarLayout snackLayout = (Snackbar.SnackbarLayout) snackbar.getView();
            snackLayout.addView(customSnackView);
            snackbar.show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }else{ super.onBackPressed();}


    }
}