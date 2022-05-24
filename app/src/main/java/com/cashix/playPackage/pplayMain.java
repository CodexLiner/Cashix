package com.cashix.playPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class pplayMain extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView ;
    adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pplay_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setSelectedItemId(R.id.home);
        recyclerView = findViewById(R.id.playRec);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this , RecyclerView.VERTICAL , false);
        recyclerView.setLayoutManager(lm);
        ArrayList<model> list = new ArrayList<>();
        list.add(new model(R.drawable.wwlogo , 400,"Logo Design" , "premium logo logo design at Cheapest Rate with our expert designer"));
        list.add(new model(R.drawable.wwlwebsite ,5000, "Website Design" , "html css webiste design at Cheapest Rate we will finish your work withing 15 days"));
        list.add(new model(R.drawable.wwmobile , 15000 ,"Mobile App Design" , "Al types of android application design in cheapest rate Cheapest Rate"));
        list.add(new model(R.drawable.wwwordpress , 7600, "Wordpress Design" , "Wordpress website design ecommerce , blog , news , website design at Cheapest Rate"));
        adapter = new adapter(list);
        recyclerView.setAdapter(adapter);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.home :{
                        return true;
                    }
                    case R.id.more: {
                        startActivity(new Intent(getApplicationContext() , settingPP.class));
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    }
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}