package com.crepaid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.chaos.view.PinView;
import com.crepaid.constants.STATIC;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

public class Send_Otp_Activity extends AppCompatActivity {
    private String mMobileNumber;
    private int mOtp;
    private Editable uOtp;
    private PinView pinView;
    private Button cButton;
    private ScrollView ScrollOtp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        ScrollOtp = findViewById(R.id.ScrollOtp);
        pinView = findViewById(R.id.firstPinView);
        cButton = findViewById(R.id.submit_otp_button);
        pinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ScrollOtp.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       View lastChild = ScrollOtp.getChildAt(ScrollOtp.getChildCount() - 1);
                       int bottom = lastChild.getBottom() + ScrollOtp.getPaddingBottom();
                       int sy = ScrollOtp.getScrollY();
                       int sh = ScrollOtp.getHeight();
                       int delta = bottom - (sy + sh);
                       ScrollOtp.smoothScrollBy(0, delta);
                   }
               }, 200);
            }
        });
        mMobileNumber = getIntent().getStringExtra("mobileNumber" );
        mOtp = getIntent().getIntExtra("otpCode" , 0 );

        cButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              uOtp = pinView.getText();
              int lastEntered = - 1;
              RelativeLayout relativeLayout = findViewById(R.id.mainLayout);
              if (uOtp == null || uOtp.toString().equals("")){
                  Snackbar.make(relativeLayout, "Please enter otp !" ,  BaseTransientBottomBar.LENGTH_LONG).show();
              }

              if (uOtp != null) {
                  String uOtpString = uOtp.toString();
                  if (!uOtpString.equals("")){
                      lastEntered = Integer.parseInt(uOtpString);
                  }
              }
              if (lastEntered > 0 && lastEntered == mOtp){
                  STATIC.makeToast(getApplicationContext() , "Login Success");
//                  startActivity(new Intent(Send_Otp_Activity.this , add_bank_details.class));
                  overridePendingTransition(0,0);
                  validUser(mMobileNumber);
              }else
                 STATIC.makeToast(getApplicationContext() , String.valueOf(mOtp));

//              startActivity(new Intent(Send_Otp_Activity.this , add_bank_details.class));
//              overridePendingTransition(0,0);
          }
      });

    }

    private void UserValidator(String mMobileNumber) {
        Map<String , String> map = new HashMap<>();
        map.put("mobile" , mMobileNumber);
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
//        Log.d("TAG", "UserValidator: "+jsonString);
            final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
            Request request = new Request.Builder().url(STATIC.baseUrlbackend +"crepaid_login").post(requestBody).build();
            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Bundle bundle = new Bundle();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        bundle.putString(STATIC.AuthKey ,jsonObject.optString("authkey") );
                        bundle.putString(STATIC.UserNumber ,jsonObject.optString("mobile") );
                        Log.d("TAG", "onResponse: "+jsonObject.optString("authkey"));
                        Intent intent = new Intent(Send_Otp_Activity.this ,add_bank_details.class );
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

    }

    private void validUser(String mMobileNumber) {
        Request request = new Request.Builder().url(STATIC.baseUrlbackend+"crepaid_login/"+mMobileNumber).get().build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("TAG", "onFailure: login failed ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                try {
                    Bundle bundle = new Bundle();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    bundle.putString(STATIC.AuthKey ,jsonObject.optString("authkey") );
                    bundle.putString(STATIC.UserNumber ,jsonObject.optString("mobile") );
                    Log.d("TAG", "onResponse: "+jsonObject.optString("authkey"));
                    Intent intent = new Intent(Send_Otp_Activity.this ,add_bank_details.class );
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(0,0);

                }catch (Exception e){
                    UserValidator(mMobileNumber);
                }
            }
        });

    }
}