package com.cashix.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cashix.R;
import com.cashix.adapters.TransactionAdapters;
import com.cashix.adapters.TransactionModel;
import com.cashix.constants.STATIC;
import com.cashix.database.userDatabaseHelper;
import com.cashix.database.userDatabaseModel;
import com.cashix.databinding.ActivityTransactionsBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Transactions extends AppCompatActivity {
    RecyclerView transactionAllRecy;
    TransactionAdapters adapters;
    userDatabaseModel model;
    ActivityTransactionsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        transactionAllRecy = findViewById(R.id.transactionAllRecy);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this , RecyclerView.VERTICAL , false);
        transactionAllRecy.setLayoutManager(layoutManager);
        findTransactions();
        userDatabaseHelper db = new userDatabaseHelper(this);
        model = db.getNote(1);
        getTransactions();
    }

    private void findTransactions() {

    }
    private ArrayList<TransactionModel> getTransactions()  {
        String token = "";
        if (model!= null && model.getAuth().length()>1){
            token = model.getAuth();
            Log.d("TAG", "getTransactions: "+token);
        }
        boolean bool = true;
        ArrayList<TransactionModel> list = new ArrayList<>();
        Map<String , String> map = new HashMap<>();
        map.put("mobiles" , "mymobile");
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
        final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
        Request request = new Request.Builder().url(STATIC.baseBackend +"payments/list").addHeader("authorization" , "Bearer "+token).get().build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       binding.tempImage.setVisibility(View.VISIBLE);
                    }
                });
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    Log.d("TAG", "jsonResponse: ");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TransactionModel model = gson.fromJson(jsonArray.getString(i) , TransactionModel.class);
                        list.add(model);
                        if (i == jsonArray.length() - 1){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.tempImage.setVisibility(View.GONE);
                                    adapters = new TransactionAdapters(list , model.getAuthkey());
                                    transactionAllRecy.setAdapter(adapters);

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
        Log.d("TAG", "onCreates: "+list.size());
        return list;
    }
}