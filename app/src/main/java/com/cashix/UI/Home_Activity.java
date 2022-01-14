package com.cashix.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cashix.R;
import com.cashix.adapters.TransactionAdapters;
import com.cashix.adapters.TransactionModel;
import com.cashix.adapters.noticeAdapter;
import com.cashix.adapters.noticeModel;
import com.cashix.constants.STATIC;
import com.cashix.database.userDatabaseHelper;
import com.cashix.database.userDatabaseModel;
import com.cashix.receivers.connection;
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

public class Home_Activity extends AppCompatActivity {
    private Button reaMore;
    ImageView recyclerImage , popUpMenu;
    private static final String TAG = "TAGS";
    private Bundle bundle ;
    RecyclerView TransactionRecycler , NoticeRecycler;
    TransactionAdapters transactionAdapters;
    noticeAdapter noticeAdapter;
    userDatabaseModel model;
    RecyclerView.LayoutManager layoutManager , noticLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        popUpMenu = findViewById(R.id.popUpMenu);
        bundle = getIntent().getExtras();
        LinearLayout BankTransfer = findViewById(R.id.BankTransfer);
        LinearLayout payRent = findViewById(R.id.payRent);
        LinearLayout walletButton = findViewById(R.id.walletButton);
        LinearLayout moreButton = findViewById(R.id.moreButton);
        reaMore = findViewById(R.id.reaMore);
        recyclerImage = findViewById(R.id.recyclerImage);
        TransactionRecycler = findViewById(R.id.TransactionRecycler);
        NoticeRecycler = findViewById(R.id.NoticeRecycler);
        userDatabaseHelper db = new userDatabaseHelper(this);
        model = db.getNote(1);
        checkConnection();
//        adding Recycler
        noticLayoutManager = new LinearLayoutManager(this ,RecyclerView.HORIZONTAL , false);
        NoticeRecycler.setLayoutManager(noticLayoutManager);
        ArrayList<noticeModel> Notlist = new ArrayList<>();
        Notlist.add(new noticeModel("https://www.torrancelearning.com/wp-content/uploads/2019/10/FLIP-hero-banner-1600-X-500-v2.jpg"));
        Notlist.add(new noticeModel("https://indiahikes.com/wp-content/uploads/2016/05/Hampta-Pass-Banner-1600-x-500.jpg"));
        Notlist.add(new noticeModel("https://www.mitla.org.mt/wp-content/uploads/2018/04/0000-0000-MITLA-GDPR-Conference-WP-Banner-1600-x-500.jpg"));
        noticeAdapter = new noticeAdapter(Notlist);
        NoticeRecycler.setAdapter(noticeAdapter);
        layoutManager = new LinearLayoutManager(this , LinearLayout.VERTICAL , true){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        TransactionRecycler.setLayoutManager(layoutManager);
        ArrayList<TransactionModel> list = getTransactions();
        recyclerImage.setVisibility(View.VISIBLE);
        // action Buttons
        BankTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , bankTransfer.class);
//                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(0 ,0);
            }
        });
        payRent.setOnClickListener((View v) ->{
            Intent intent = new Intent(getApplicationContext() , com.cashix.UI.payRent.class);
//                intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(0 ,0);
        });
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , MoreActivity.class);
//                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(0 ,0);
            }
        });
        walletButton.setOnClickListener((View v)->{
            Intent intent = new Intent(getApplicationContext() , pay_wallet.class);
//                intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(0 ,0);
        });

        popUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(getApplicationContext() , v);
            }
        });
        reaMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readMore();
            }
        });
    }

    private void readMore() {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setShowTitle(true);
        builder.setUrlBarHidingEnabled(true);
        CustomTabsIntent intent = builder.build();
        intent.launchUrl(this, Uri.parse("https://coinswitch.co/kuber-terms"));
    }

    private void checkConnection() {
        connection conn = new connection();
        conn.onReceive(getApplicationContext() , null);
    }

    private void showMenu(Context context, View v) {

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
        Request request = new Request.Builder().url(STATIC.baseUrlbackend +"payments/list").addHeader("authorization" , "Bearer "+token).get().build();
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
                                    recyclerImage.setVisibility(View.GONE);
                                    transactionAdapters = new TransactionAdapters(list , model.getAuthkey());
                                    TransactionRecycler.setAdapter(transactionAdapters);

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

    @Override
    protected void onResume() {
        super.onResume();
        getTransactions();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getTransactions();
    }
}