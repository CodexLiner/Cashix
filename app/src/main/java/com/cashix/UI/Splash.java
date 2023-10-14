package com.cashix.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cashix.database.user.UserInfo;
import com.cashix.databinding.ActivitySplashBinding;
import com.cashix.receivers.connection;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private boolean isHome = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
//        checkConnection();
//        if ( new UserInfo(getApplicationContext()).getModel()!=null){
//            if (new UserInfo(getApplicationContext()).getModel().getAuth()!=null){
//                isHome = true;
//            }
//        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (isHome){
//                            startActivity(new Intent(getApplicationContext() , HomeActivity.class));
//                        }else {
//                            startActivity(new Intent(getApplicationContext() , MainActivity.class));
//                        }
//                       overridePendingTransition(0,0);
//                       finishAffinity();
//                    }
//                });
            }
        },2000);
        setContentView(binding.getRoot());
    }
    private void checkConnection() {
        connection conn = new connection();
        conn.onReceive(getApplicationContext(), null);
    }
}