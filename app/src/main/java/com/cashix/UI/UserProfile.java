package com.cashix.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.cashix.database.userDatabaseHelper;
import com.cashix.database.userDatabaseModel;
import com.cashix.databinding.ActivityUserProfileBinding;

public class UserProfile extends AppCompatActivity {
    ActivityUserProfileBinding binding;
    View view ;
    customButton button;
    Button button2;
    boolean buttonStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setDataIfAvailable();
        view = findViewById(R.id.customBtn);
        button2 = findViewById(R.id.editProfile);
        setDataIfAvailable();
        isWellFormated();
//        disable layouts
        allowEditing(false);
//        custome button
        button = new customButton(this , view);
        button.setText("Edit Profile");;
        button.setEnabled(true);
//        if (button.isEnable()){
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    button.setText("Save");
//                    allowEditing();
//                }
//            });
//        }else {
//            button.setText("Edit ");
//        }
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!buttonStatus){
                   buttonStatus = true;
                   button2.setText("Save Profile");
                   allowEditing(true);
               }else{
                   submitProfileData();
               }
            }
        });
    }


    private void setDataIfAvailable() {
        userDatabaseHelper dbs = new userDatabaseHelper(this);
        userDatabaseModel model = dbs.getNote(1);
        binding.userDOBEdit.setText(model.getDate());
        binding.userEmailEdit.setText(model.getEmail());
        binding.userMobileEdit.setText(model.getMobile());
        binding.profilName.setText(model.getAuth());
    }

    private void submitProfileData() {
        if (binding.userDOBEdit.isActivated()){
            binding.userDOBEdit.setHint("DD/MM/YYYY");
        }
        String newName = binding.profilName.getText().toString().trim();
        String newEmail = binding.userEmailEdit.getText().toString().trim();
        String newDate = binding.userDOBEdit.getText().toString().trim();
        binding.userDOBEdit.addTextChangedListener(new TextWatcher() {
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
        if (TextUtils.isEmpty(newName)){
            binding.nameProfile.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(newDate) && isWellFormated()){
            binding.userDOBBox.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(newEmail)){
            binding.userEmailBox.requestFocus();
            return;
        }
        buttonStatus = false;
        button2.setText("Edit Profile");
        allowEditing(false);
    }

    private boolean isWellFormated() {
        String date = "15/07/1998";
        String day =(date.substring(0, 3));
        String month = date.substring( 3 , 5);
        String year =(date.substring(6));
       return true;

    }

    private void allowEditing(boolean bool) {
                binding.nameProfile.setEnabled(bool);
                binding.userDOBBox.setEnabled(bool);
//                binding.userMobileBox.setEnabled(bool);
                binding.userEmailBox.setEnabled(bool);
    }
}