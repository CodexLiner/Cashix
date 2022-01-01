package com.crepaid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

import com.crepaid.keyboard.custom_keyboard;

public class bankTransfer extends AppCompatActivity {
    EditText ammoutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);
        ammoutText = (EditText) findViewById(R.id.ammoutText);
        ammoutText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        ammoutText.setTextIsSelectable(false);
        ammoutText.setShowSoftInputOnFocus(false);
        ammoutText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        custom_keyboard customKeyboard = (custom_keyboard) findViewById(R.id.customKeyboard);
        InputConnection ic = ammoutText.onCreateInputConnection(new EditorInfo());
        customKeyboard.setInputConnection(ic);


    }
}