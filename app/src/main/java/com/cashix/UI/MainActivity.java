package com.cashix.UI;

import static com.cashix.constants.STATIC.SHARED_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.cashix.R;
import com.cashix.database.userDatabaseHelper;
import com.cashix.database.userDatabaseModel;
import com.cashix.network.LoginVerifyRequestBody;
import com.cashix.network.RetrofitClient;
import com.cashix.network.loginSendOtpResponse;
import com.cashix.receivers.connection;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    private EditText mobileText;
    private Button sendButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // checking internet
        checkConnection();
        setTheme(R.style.Theme_Crepaid);
        setContentView(R.layout.activity_main);
        mobileText = findViewById(R.id.mobileText);
        sendButton = findViewById(R.id.send_otp_button);
        sendButton.setEnabled(false);

        sharedPreferences = getSharedPreferences(SHARED_, MODE_PRIVATE);
        userDatabaseHelper db = new userDatabaseHelper(getApplicationContext());

        userDatabaseModel model = db.getUser(1);
        if (model != null && model.getAuth().length() > 1) {
            Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
        mobileText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendButton.setEnabled(s.length() == 10);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sendButton.setOnClickListener(v -> sendOtpViaServer(mobileText.getText().toString().trim()));
    }

    private void sendOtpViaServer(String mobile) {
        sendButton.setEnabled(false);

        retrofit2.Call<loginSendOtpResponse> call = RetrofitClient.getInstance("").getApi().loginSendOtp(new LoginVerifyRequestBody(mobile , mobile));
        call.enqueue(new retrofit2.Callback<loginSendOtpResponse>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<loginSendOtpResponse> call, @NonNull retrofit2.Response<loginSendOtpResponse> response) {
                if (response.body() != null && Objects.equals(response.body().getStatus(), "success")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("token", response.body().getToken());
                    bundle.putString("mobile", mobile);
                    Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
            @Override
            public void onFailure(@NonNull retrofit2.Call<loginSendOtpResponse> call, @NonNull Throwable t) {
                sendButton.setEnabled(false);
            }
        });
    }

    private void checkConnection() {
        connection conn = new connection();
        conn.onReceive(getApplicationContext(), null);
    }
}