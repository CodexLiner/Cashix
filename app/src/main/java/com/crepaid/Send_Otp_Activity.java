package com.crepaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chaos.view.PinView;

public class Send_Otp_Activity extends AppCompatActivity {
    private String mMobileNumber;
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

      cButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(Send_Otp_Activity.this , add_bank_details.class));
              overridePendingTransition(0,0);
          }
      });

    }
}