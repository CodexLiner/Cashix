package com.crepaid.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.crepaid.R;
import com.crepaid.adapters.TransactionAdapters;
import com.crepaid.adapters.TransactionModel;
import com.crepaid.constants.STATIC;
import com.crepaid.database.userDatabaseHelper;
import com.crepaid.database.userDatabaseModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Home_Activity extends AppCompatActivity {
    ImageView recyclerImage , popUpMenu;
    private static final String TAG = "TAGS";
    private Bundle bundle ;
    RecyclerView TransactionRecycler;
    TransactionAdapters transactionAdapters;
    TransactionModel model;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        popUpMenu = findViewById(R.id.popUpMenu);
        bundle = getIntent().getExtras();
        LinearLayout BankTransfer = findViewById(R.id.BankTransfer);
        recyclerImage = findViewById(R.id.recyclerImage);
        TransactionRecycler = findViewById(R.id.TransactionRecycler);
        userDatabaseHelper db = new userDatabaseHelper(this);
        userDatabaseModel userDatabaseModel = db.getNote(1);
        Log.d(TAG, "onCreate: "+userDatabaseModel.toString());

//        adding Recycler
        layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        TransactionRecycler.setLayoutManager(layoutManager);
        ArrayList<TransactionModel> list = getTransactions();

        BankTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , bankTransfer.class);
//                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(0 ,0);
            }
        });
        popUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(getApplicationContext() , v);
            }
        });
    }

    private void showMenu(Context context, View v) {

    }


    private ArrayList<TransactionModel> getTransactions()  {
        boolean bool = true;
        ArrayList<TransactionModel> list = new ArrayList<>();
        Request request = new Request.Builder().url(STATIC.baseUrlbackend +"payments/"+"req.body.authkey11").get().build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerImage.setVisibility(View.VISIBLE);
                    }
                });
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Log.d(TAG, "jsonResponse: ");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TransactionModel model = gson.fromJson(jsonArray.getString(i) , TransactionModel.class);
                        list.add(model);
                        if (i == jsonArray.length() - 1){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (list !=null){
                                        recyclerImage.setVisibility(View.GONE);
                                        transactionAdapters = new TransactionAdapters(list , "key");
                                        TransactionRecycler.setAdapter(transactionAdapters);
                                    }else {
                                        recyclerImage.setVisibility(View.VISIBLE);

                                    }

                                }
                            });

                        }
                    }
                } catch (JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            recyclerImage.setVisibility(View.VISIBLE);
                        }
                    });
                    Log.d("TAG", "jsonResponseERR: "+e);
                }
            }
        });
        Log.d(TAG, "onCreates: "+list.size());
        return list;
    }
}