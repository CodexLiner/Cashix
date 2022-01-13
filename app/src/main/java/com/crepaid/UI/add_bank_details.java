package com.crepaid.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.crepaid.R;
import com.crepaid.constants.STATIC;
import com.crepaid.database.userDatabaseHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class add_bank_details extends AppCompatActivity {
    private Bundle bundle;
    public Spinner spinner;
    private EditText BankIfsc , BankAccount  , IdNumber ;
    private final String IdType = "NaN";
    public String[] idList = {"Select ID optional" , "Adhaar Card" , "Pan Card" , "Votor ID" , "Bank Passbook"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_details);
        bundle = getIntent().getExtras();
//        Log.d("TAG", "onCreateT: "+bundle.get(STATIC.UserNumber));
        Button cButton = findViewById(R.id.otp_continue_button);
        Button skipButton = findViewById(R.id.otp_skip_button);
        spinner =(Spinner) findViewById(R.id.spinnerView);
        BankAccount = findViewById(R.id.accountNumber);
        BankIfsc = findViewById(R.id.ifscCode);
        IdNumber = findViewById(R.id.idNumber);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,
                idList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "onItemSelected: "+idList[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String accountNumber = BankAccount.getText().toString().trim();
                final String ifscCode = BankIfsc.getText().toString().trim();
                final String idNumber = IdNumber.getText().toString().trim();
                postbankDetails(accountNumber , ifscCode , idNumber , IdType);
//                startActivity(new Intent(getApplicationContext() , Home_Activity.class));
//                Toast.makeText(add_bank_details.this, "Account Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerWithMobileOnly(bundle.getString("token") , bundle.getString("mobile"));
                Intent intent = new Intent(getApplicationContext() , Home_Activity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                overridePendingTransition(0,0);
            }
        });
    }

    private void registerWithMobileOnly(String token ,String mobile) {
        Log.d("TAG", "registerWithMobileOnly: a"+token);
        Map<String , String> map = new HashMap<>();
        map.put("mobiles" , "mymobile");
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
        final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
        Request request = new Request.Builder().url(STATIC.baseUrlbackend +"crepaid_login/add_bank").addHeader("authorization" , "Bearer "+token).post(requestBody).build();
    new OkHttpClient()
        .newCall(request)
        .enqueue(
            new Callback() {
              @Override
              public void onFailure(@NonNull Call call, @NonNull IOException e) {
                  runOnUiThread(
                    new Runnable() {
                      @Override
                      public void run() {
                        Log.d("TAG", "registerWithMobileOnly: a"+e.getMessage());
                        STATIC.makeToast(getApplicationContext(), "something went wrong");
                      }
                    });
              }

              @Override
              public void onResponse(@NonNull Call call, @NonNull Response response)
                  throws IOException {
                  if (response.code() == 200) {
                  userDatabaseHelper db = new userDatabaseHelper(getApplicationContext());
                  long res = db.insertNote(bundle.getString("mobile"), token, 1);
                  if (res > -1) {
                    startActivity(new Intent(getApplicationContext(), Home_Activity.class));
                    overridePendingTransition(0, 0);
                    finishAffinity();
                  }
                } else {
                  Log.d("TAG", "registerWithMobileOnly: else ");
                }
              }
            });
    }

    private void postbankDetails(String accountNumber, String ifscCode, String idNumber, String idType) {
        Map<String , String> map = new HashMap<>();
        map.put("_id" , bundle.getString(STATIC.AuthKey));
        map.put(STATIC.AuthKey , bundle.getString(STATIC.AuthKey));
        map.put(STATIC.UserAccount , accountNumber);
        map.put(STATIC.UserIfsc , ifscCode);
        map.put(STATIC.UserName , accountNumber);
        map.put(STATIC.UserKycId , idNumber);
        map.put(STATIC.UserKycIdType , idType);
        Gson gson = new Gson();
        String postString = gson.toJson(map);
        Log.d("TAG", "postbankDetails: "+postString);
        try {
            final RequestBody requestBody = RequestBody.create(postString , MediaType.get(STATIC.mediaType));
            Request request = new Request.Builder().url(STATIC.baseUrlbackend +"crepaid_bank_details").post(requestBody).build();
            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("TAG", "onFailure: failed");
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                }
            });
            Toast.makeText(add_bank_details.this, "Details Saved Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(add_bank_details.this, Home_Activity.class);
            if (bundle!=null){
                intent.putExtras(bundle);
            }
            startActivity(intent);
            overridePendingTransition(0,0);
        }catch (Exception e){
            Log.d("TAG", "postbankDetails: "+e.toString());
        }
    }
}