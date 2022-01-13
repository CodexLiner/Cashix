package com.crepaid.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crepaid.R;
import com.crepaid.UI.customButton;
import com.crepaid.constants.STATIC;
import com.crepaid.payment.CheckoutActivity;

public class payRent extends AppCompatActivity {
    EditText holderName , holderIfsc , holderAccount , holderReason , holderAmount;
    View cButton;
    Button addAccountButton;
    customButton next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_rent);
        holderAccount = findViewById(R.id.holderAccount);
        holderIfsc = findViewById(R.id.holderIFSC);
        holderAmount = findViewById(R.id.holderAmount);
        holderName = findViewById(R.id.holderName);
        holderReason = findViewById(R.id.holderReason);
        addAccountButton = findViewById(R.id.addAccountButton);
        cButton = findViewById(R.id.customButton);
        next = new customButton(getApplicationContext() , cButton);
    cButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String uName = holderName.getText().toString().trim();
              String uAmount = holderAmount.getText().toString().trim();
              String uIfsc = holderIfsc.getText().toString().trim();
              String uAccount = holderAccount.getText().toString().trim();
              String uDecription = holderReason.getText().toString().trim();
              if (TextUtils.isEmpty(uName)){
                  holderAmount.requestFocus();
                  return;
              }
              if (TextUtils.isEmpty(uName)){
                  holderName.requestFocus();
                  return;
              }
              if (TextUtils.isEmpty(uName)){
                  holderName.requestFocus();
                  return;
              }
              if (TextUtils.isEmpty(uAccount)){
                  holderAccount.requestFocus();
                  return;
              }
              if (TextUtils.isEmpty(uIfsc)){
                  holderIfsc.setError("required");
                  return;
              }
              next.activeButton("PLEASE WAIT");
              startPaymentActivity(uName , uIfsc , uAccount , uDecription, uAmount);
          }
        });

        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = holderName.getText().toString().trim();
                String uAmount = holderAmount.getText().toString().trim();
                String uIfsc = holderIfsc.getText().toString().trim();
                String uAccount = holderAccount.getText().toString().trim();
                String uDecription = holderReason.getText().toString().trim();
                if (TextUtils.isEmpty(uName)){
                    holderAmount.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(uName)){
                    holderName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(uName)){
                    holderName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(uAccount)){
                    holderAccount.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(uIfsc)){
                    holderIfsc.setError("required");
                    return;
                }
                startPaymentActivity(uName , uIfsc , uAccount , uDecription, uAmount);
                STATIC.makeToast(getApplicationContext() , "ACCOUNT ADDED");
            }
        });
    }

    private void startPaymentActivity(String uName, String uIfsc, String uAccount, String uDecription , String uAmount) {
        Bundle bundle = new Bundle();
        bundle.putString("uName" , uName);
        bundle.putString("uIfsc" , uIfsc);
        bundle.putString("uAccount" , uAccount);
        bundle.putString("uDecription" , uDecription);
        bundle.putBoolean("rentType" , true);
        bundle.putInt("amount" , Integer.parseInt(uAmount));
        Intent intent = new Intent(getApplicationContext() , CheckoutActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (next!=null){
            next.setEnabled(false);
            next.deActiveButton();
        }
    }
}