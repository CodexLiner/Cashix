package com.cashix.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.cashix.keyboard.custom_keyboard;
import com.cashix.payment.CheckoutActivity;

public class bankTransfer extends AppCompatActivity {
    EditText ammoutText;
    Button swipePayButton;
    Bundle bundle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);
        bundle = new Bundle();
        ammoutText = (EditText) findViewById(R.id.amountText);
        swipePayButton = findViewById(R.id.swipePayButton);
        swipePayButton.setEnabled(false);
        ammoutText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        ammoutText.setTextIsSelectable(false);
        ammoutText.setShowSoftInputOnFocus(false);
        ammoutText.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() >= 1 && !s.equals("0")) {
              Log.d("TAG", "onTextChanged: true");
                swipePayButton.setEnabled(true);
            }else {
                swipePayButton.setEnabled(false);
            }
          }

          @Override
          public void afterTextChanged(Editable s) {}
        });
        custom_keyboard customKeyboard = (custom_keyboard) findViewById(R.id.customKeyboard);
        InputConnection ic = ammoutText.onCreateInputConnection(new EditorInfo());
        customKeyboard.setInputConnection(ic);
        swipePayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = ammoutText.getText().toString().toString();
                if (TextUtils.isEmpty(amount) || Integer.parseInt(amount) < 1 ){
                    ammoutText.requestFocus();
                    return;
                }
                int bundleAmount = Integer.parseInt(amount);
                bundle.putInt(STATIC.Amount , bundleAmount);
                bundle.putString(STATIC.TransactionType , "bank transfer");
                Intent intent = new Intent(getApplicationContext() , CheckoutActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        ammoutText.setText("");
    }
}