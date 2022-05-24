package com.cashix.playPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class playSignUp extends AppCompatActivity {
    EditText name ,email , pass , mobile;
    FirebaseAuth auth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_sign_up);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        name = findViewById(R.id.nameText);
        pass = findViewById(R.id.passText);
        email = findViewById(R.id.emailText);
        mobile = findViewById(R.id.mobileText);
        Button button = findViewById(R.id.addAccountButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameT = name.getText().toString();
                String passT = pass.getText().toString();
                String emailT = email.getText().toString();
                String mobileT = mobile.getText().toString();
                if (TextUtils.isEmpty(nameT)){
                    return;
                }
                if (TextUtils.isEmpty(passT)){
                    return;
                }
                if (TextUtils.isEmpty(emailT)){
                    return;
                }
                if (TextUtils.isEmpty(mobileT)){
                    return;
                }
                button.setEnabled(false);
                button.setText("signing up");
                Map<String , String> map = new HashMap<>();
                map.put("name" , nameT);
                map.put("email" , emailT);
                map.put("mobile" , mobileT);
                auth.createUserWithEmailAndPassword(emailT , passT).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(@NonNull AuthResult authResult) {
                        db.collection("users").document(auth.getCurrentUser().getUid()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {
                                STATIC.makeToast(getApplicationContext() , "SignUp Complete");
                                startActivity(new Intent(getApplicationContext(), pplayMain.class));
                                overridePendingTransition(0,0);
                            }
                        });
                    }
                });
            }
        });
    }
}