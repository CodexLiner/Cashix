package com.cashix.playPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class settingPP extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    LinearLayout p , t , pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pp2);
        bottomNavigationView = findViewById(R.id.bottomNav);
        p = findViewById(R.id.privacyPolicy);
        t = findViewById(R.id.termCondtion);
        pr = findViewById(R.id.userDetails);
        bottomNavigationView.setSelectedItemId(R.id.more);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.home :{
                        startActivity(new Intent(getApplicationContext() , pplayMain.class));
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    }
                    case R.id.more: {
                        return true;
                    }
                }
                return false;
            }
        });
        pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() ,playprofile.class));
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runner("https://pages.flycricket.io/cashix-web-portal/privacy.html");
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runner("https://pages.flycricket.io/cashix-web-portal/terms.html");
            }
        });
    }
    public void runner(String url){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setShowTitle(true);
        builder.setUrlBarHidingEnabled(true);
        CustomTabsIntent intent = builder.build();
        intent.launchUrl(this, Uri.parse(url));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext() , pplayMain.class));
        overridePendingTransition(0,0);
        finish();
    }
}