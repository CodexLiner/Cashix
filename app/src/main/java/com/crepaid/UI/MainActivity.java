package com.crepaid.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.crepaid.R;
import com.crepaid.constants.Async_task_otp_send;
import com.crepaid.constants.STATIC;
import com.crepaid.database.userDatabaseHelper;
import com.crepaid.database.userDatabaseModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    private EditText mobileText;
    private Button send_button;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mobileText = findViewById(R.id.mobileText);
        send_button = findViewById(R.id.send_otp_button);
        send_button.setEnabled(false);
        sharedPreferences = getSharedPreferences("Crepaid" ,MODE_PRIVATE);
        userDatabaseHelper db = new userDatabaseHelper(getApplicationContext());
        userDatabaseModel model = db.getNote(1);
        if (model!= null && model.getAuth().length()>1){
            Intent intent = new Intent(getApplicationContext() , Home_Activity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        }
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mobileText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==10){
                    send_button.setEnabled(true);
                }else {
                    send_button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtpViaServer(mobileText.getText().toString().trim());
//                String mobile = mobileText.getText().toString().toLowerCase(Locale.ROOT).trim();
//                Intent intent = new Intent(MainActivity.this , Send_Otp_Activity.class);
//                intent.putExtra("mobileNumber" , mobile);
//                intent.putExtra("otpCode" , GenrateOtp(mobile));
//                startActivity(intent);
//                overridePendingTransition(0,0);
            }
        });
    }

    private void sendOtpViaServer(String trim) {
        send_button.setEnabled(false);
        Map<String , String> map = new HashMap<>();
        map.put("mobile" , trim);
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
        final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
        Request request = new Request.Builder().url(STATIC.baseUrlbackend +"crepaid_login/verify").post(requestBody).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        STATIC.makeToast(getApplicationContext() , "Failed To Send OTP");
                        send_button.setEnabled(true);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject;
                    jsonObject = new JSONObject(response.body().string());
                    if (!jsonObject.optString("lastToken").equals(" ") || !jsonObject.optString("lastToken").isEmpty()){
                        Bundle bundle = new Bundle();
                        bundle.putString("token" , jsonObject.optString("lastToken") );
                        Log.d("TAG", "onResponse: "+ jsonObject.optString("lastToken"));
                        bundle.putString("mobile" , trim);
                        Intent intent = new Intent(getApplicationContext() , Send_Otp_Activity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private int GenrateOtp(String mobile) {
        Random random = new Random();
        int low = 111111;
        int high = 999999;
        int otpIs = (int) ((Math.random()*900000)+100000);
        sendToUser(mobile ,otpIs);
        return otpIs;
    }
    private void sendToUser(String mobile, int otp) {
        Async_task_otp_send send = new Async_task_otp_send(mobile , otp);
        send.execute();
//        APIs.sendOtp(mobile,otp);
    }
}