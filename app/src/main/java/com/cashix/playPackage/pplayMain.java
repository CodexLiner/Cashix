package com.cashix.playPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        recyclerView = findViewById(R.id.playRec);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this , RecyclerView.VERTICAL , false);
        recyclerView.setLayoutManager(lm);
        adapter = new adapter();
        recyclerView.setAdapter(adapter);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.home :{
                        return true;
                    }
                    case R.id.more: {
                        STATIC.makeToast(getApplicationContext() , "fh");
                        break;
                    }
                }
                return false;
            }
        });

    }
}