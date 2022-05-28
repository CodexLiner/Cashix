package com.cashix.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.cashix.R;
import com.cashix.constants.STATIC;
import com.cashix.database.user.userDatabaseHelper;
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
    private TextView resendOtp , resendOtpButton , ForceExit;
    private int mOtp;
    private Editable uOtp;
    private PinView pinView;
    private Button cButton;
    private ScrollView ScrollOtp;
    Bundle bundle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        ScrollOtp = findViewById(R.id.ScrollOtp);
        pinView = findViewById(R.id.firstPinView);
        cButton = findViewById(R.id.submit_otp_button);
        resendOtp = findViewById(R.id.resendOtp);
        ForceExit = findViewById(R.id.ForceExit);
        resendOtpButton = findViewById(R.id.resendOtpButton);
        bundle = getIntent().getExtras();
        sharedPreferences = getSharedPreferences("Crepaid", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Log.d("TAG", "onCreateShared: "+sharedPreferences.getString("login" , "nhi aaya"));

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
        ResendOtpCounter();
        cButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              uOtp = pinView.getText();
              int lastEntered = - 1;
              RelativeLayout relativeLayout = findViewById(R.id.mainLayout);
              if (uOtp == null || uOtp.toString().equals("")){
                  Snackbar.make(relativeLayout, "PLEASE ENTER OTP !" ,  BaseTransientBottomBar.LENGTH_LONG).show();
              }
              if (uOtp != null) {
                  String uOtpString = uOtp.toString();
                  if (!uOtpString.equals("")){
                      lastEntered = Integer.parseInt(uOtpString);
                  }
              }
              if (bundle!=null){
                  UserValidator( bundle.getString("mobile") ,bundle.getString("token") , uOtp.toString().trim());
              }
          }
      });
        resendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STATIC.makeToast(getApplicationContext() , "otp sent successfully");
            }
        });
        ForceExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    private void UserValidator(String mMobileNumber , String token ,String code) {
        Map<String , String> map = new HashMap<>();
        map.put("mobile" , mMobileNumber);
        map.put("code" , code);
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
            final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
            Request request = new Request.Builder().url(STATIC.baseBackend +"crepaid_login/validator").addHeader("authorization" , "Bearer "+token).post(requestBody).build();
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
                        runOnUiThread(
                            new Runnable() {
                              @Override
                              public void run() {
                                STATIC.makeToast(getApplicationContext(), "something went wrong");
                              }
                            });
                      }
                    });
              }

              @Override
              public void onResponse(@NonNull Call call, @NonNull Response response)
                  throws IOException {
                Bundle bundle = new Bundle();
                try {

                  JSONObject jsonObject;
                  jsonObject = new JSONObject(response.body().string());
                  if (jsonObject.getBoolean("oldUser")) {
                    bundle.putString("token", jsonObject.optString("token"));
                    bundle.putString("mobile", mMobileNumber);
                    userDatabaseHelper db = new userDatabaseHelper(getApplicationContext());
                    long res = db.insertNote(mMobileNumber, jsonObject.optString("token"), 1);
                    Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                  } else {
                    Log.d("TAG", "token is : "+jsonObject.optString("token"));
                    bundle.putString("token", jsonObject.optString("token"));
                    bundle.putString("mobile", mMobileNumber);
                    Intent intent = new Intent(getApplicationContext(), add_bank_details.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                  }

                } catch (JSONException e) {
                  if (response.code() == 406) {
                    STATIC.makeToast(getApplicationContext(), "invalid otp");
                  }
                }
              }
            });
    }

//    private void validUser(String mMobileNumber) {
//        Request request = new Request.Builder().url(STATIC.baseUrlbackend+"crepaid_login/"+mMobileNumber).get().build();
//        new OkHttpClient().newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.d("TAG", "onFailure: login failed ");
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//
//                try {
//                    Bundle bundle = new Bundle();
//                    JSONObject jsonObject = new JSONObject(response.body().string());
////                    putting on SharedPreferences
//                    editor.putString(STATIC.AuthKey , jsonObject.optString("authkey"));
//                    editor.putString(STATIC.UserNumber , jsonObject.optString("mobile"));
////                    putting on Bundle
//                    bundle.putString(STATIC.AuthKey ,jsonObject.optString("authkey") );
//                    bundle.putString(STATIC.UserNumber ,jsonObject.optString("mobile") );
//
//                    Log.d("TAG", "onResponse: "+jsonObject.optString("authkey"));
//                    Intent intent = new Intent(Send_Otp_Activity.this ,add_bank_details.class );
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    overridePendingTransition(0,0);
//
//                }catch (Exception e){
//                    UserValidator(mMobileNumber);
//                }
//            }
//        });
//
//    }
    public void ResendOtpCounter(){
        new CountDownTimer(60000 , 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s = "RESEND OTP IN "+millisUntilFinished / 1000;
                        resendOtp.setText(s);
                    }
                });
            }

            @Override
            public void onFinish() {
                Log.d("TAG", "onTick: ");
                resendOtp.setVisibility(View.GONE);
                resendOtpButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}