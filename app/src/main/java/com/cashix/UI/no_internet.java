package com.cashix.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.cashix.receivers.connection;

public class no_internet extends AppCompatActivity {
    private Button reCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        reCheck = findViewById(R.id.reCheck);
        reCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recheckConnection(getApplicationContext());
            }
        });
    }

    private void recheckConnection(Context context) {
    Log.d("TAG", "recheckConnection: ");
        connection cn = new connection();
        if (cn.reCheckConection(context)){
            Log.d("TAG", "recheckConnection: ");
            STATIC.makeToast(context , "conection is back");
            finish();
        }else {
//            STATIC.makeToast(context , "no connection");

        }

    }

    @Override
    public void onBackPressed() {

    }
}