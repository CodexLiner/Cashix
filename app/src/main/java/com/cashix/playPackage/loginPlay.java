package com.cashix.playPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginPlay extends AppCompatActivity {
    private EditText email  , pass;
    Button login , signup;
    FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_play);
        email = findViewById(R.id.logintext);
        pass = findViewById(R.id.loginpass);
        pass = findViewById(R.id.loginpass);
        login = findViewById(R.id.addAccountButton);
        signup = findViewById(R.id.creatAcout);
        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("name" , MODE_PRIVATE);
        editor = sharedPreferences.edit();

           Log.d("TAG", "onCreateUSER: ");
           if (auth.getCurrentUser()!=null){
               startActivity(new Intent(getApplicationContext() , pplayMain.class));
               overridePendingTransition(0,0);
           }

        signup .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), playSignUp.class));
                overridePendingTransition(0,0);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
                login.setText("logging in");
                String name = email.getText().toString().trim();
                String passs = pass.getText().toString().trim();
                auth.signInWithEmailAndPassword(name , passs).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(@NonNull AuthResult authResult) {
                        editor.putBoolean("user" ,true);
                        STATIC.makeToast(getApplicationContext() , "Sign Success");
                        startActivity(new Intent(getApplicationContext(), pplayMain.class));
                        overridePendingTransition(0,0);
                    }
                });
            }
        });
    }
}