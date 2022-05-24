package com.cashix.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.cashix.database.BankDataHelper;
import com.cashix.database.bankDataModel;
import com.cashix.databinding.ActivityBankDetailsBinding;

public class BankDetails extends AppCompatActivity {
    ActivityBankDetailsBinding binding;
    boolean isEnabled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setDataIfAvailable();
        binding.bankSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!isEnabled){
                   editBankData();
               }else {
                   submittedData();
               }
            }
        });

    }

    private void submittedData() {
        isEnabled = false;
        allowEditing(false);
        String name = binding.bankName.getText().toString();
        String account = binding.bankAc.getText().toString();
        String ifsc = binding.bankIFSC.getText().toString();
        if (TextUtils.isEmpty(name)){
            binding.bankName.requestFocus();
        }
        if (TextUtils.isEmpty(account)){
            binding.bankAc.requestFocus();
        }
        if (TextUtils.isEmpty(ifsc)){
            binding.bankIFSC.requestFocus();
        }
        binding.bankSubmit.setText("Edit");
    }

    private void editBankData() {
        binding.bankSubmit.setText("Submit");
        isEnabled = true;
        allowEditing(true);
    }

    private void allowEditing(boolean bool) {
        binding.bankName.setEnabled(bool);
        binding.bankAc.setEnabled(bool);
        binding.bankIFSC.setEnabled(bool);
    }

    private void setDataIfAvailable() {
        allowEditing(false);
        BankDataHelper db = new BankDataHelper(getApplicationContext());
        bankDataModel model = db.getBank();
        if (model!=null){
            binding.bankSubmit.setText("Edit");
            isEnabled = false;
            binding.bankName.setText(model.getNAME());
            binding.bankIFSC.setText(model.getIFSC());
            binding.bankAc.setText(model.getACCOUNT());
        }

    }

}