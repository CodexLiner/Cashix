package com.crepaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crepaid.constants.APIs;
import com.crepaid.constants.Async_task_otp_send;
import com.crepaid.constants.STATIC;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
        if (sharedPreferences.contains(STATIC.AuthKey)) {
            Bundle bundle = new Bundle();
            bundle.putString(STATIC.AuthKey , sharedPreferences.getString(STATIC.AuthKey , "NaN"));
            bundle.putString(STATIC.UserNumber , sharedPreferences.getString(STATIC.UserNumber , "NaN"));
            Intent intent = new Intent(getApplicationContext() , Home_Activity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(0,0);
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
                String mobile = mobileText.getText().toString().toLowerCase(Locale.ROOT).trim();
                Intent intent = new Intent(MainActivity.this , Send_Otp_Activity.class);
                intent.putExtra("mobileNumber" , mobile);
                intent.putExtra("otpCode" , GenrateOtp(mobile));
                startActivity(intent);
                overridePendingTransition(0,0);
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