package com.cashix.UI;

import static com.cashix.constants.STATIC.SHARED_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.cashix.databinding.ActivitySendOtpBinding;
import com.cashix.network.LoginVerifyRequestBody;
import com.cashix.network.RetrofitClient;
import com.cashix.network.loginSendOtpResponse;
import com.cashix.R;
import com.cashix.constants.STATIC;
import com.cashix.database.userDatabaseHelper;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class VerifyOTPActivity extends AppCompatActivity {
    private Editable uOtp;
    Bundle bundle;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ActivitySendOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySendOtpBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        bundle = getIntent().getExtras();
        sharedPreferences = getSharedPreferences(SHARED_, MODE_PRIVATE);
        editor = sharedPreferences.edit();


        binding.firstPinView.setOnClickListener(v -> binding.ScrollOtp.postDelayed(() -> {
            View lastChild = binding.ScrollOtp.getChildAt(binding.ScrollOtp.getChildCount() - 1);
            int bottom = lastChild.getBottom() + binding.ScrollOtp.getPaddingBottom();
            int sy = binding.ScrollOtp.getScrollY();
            int sh = binding.ScrollOtp.getHeight();
            int delta = bottom - (sy + sh);
            binding.ScrollOtp.smoothScrollBy(0, delta);
        }, 200));

        ResendOtpCounter();
        binding.submitOtpButton.setOnClickListener(v -> {
            uOtp = binding.firstPinView.getText();
            RelativeLayout relativeLayout = findViewById(R.id.mainLayout);
            if (uOtp == null || uOtp.toString().equals("")) {
                Snackbar.make(relativeLayout, "PLEASE ENTER OTP !", BaseTransientBottomBar.LENGTH_LONG).show();
            }
            if (bundle != null) {
                UserValidator(bundle.getString("mobile"), bundle.getString("token"), uOtp.toString().trim());
            }
        });
        binding.resendOtpButton.setOnClickListener(v -> STATIC.makeToast(getApplicationContext(), "otp sent successfully"));
        binding.ForceExit.setOnClickListener(v -> finishAffinity());

    }

    private void UserValidator(String mobile, String token, String code) {
        retrofit2.Call<loginSendOtpResponse> call = RetrofitClient.getInstance(token).getApi().loginVerifyOtp(new LoginVerifyRequestBody(code, mobile));
        call.enqueue(new retrofit2.Callback<loginSendOtpResponse>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<loginSendOtpResponse> call, @NonNull retrofit2.Response<loginSendOtpResponse> response) {
                Log.d("TAG", "onResponseMine: "+response.body());
                if (response.body() != null) {
                    if (response.body().isOldUser()) {
                        bundle.putString("token", response.body().getToken());
                        bundle.putString("mobile", mobile);

                        userDatabaseHelper db = new userDatabaseHelper(getApplicationContext());
                        db.insertNote(mobile, response.body().getToken(), 1);
                        Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    } else {
                        bundle.putString("token", response.body().getToken());
                        bundle.putString("mobile", mobile);
                        Intent intent = new Intent(getApplicationContext(), add_bank_details.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<loginSendOtpResponse> call, Throwable t) {
                runOnUiThread(() -> STATIC.makeToast(getApplicationContext(), "something went wrong"));
            }
        });
    }

    public void ResendOtpCounter() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                runOnUiThread(() -> {
                    String s = "RESEND OTP IN " + millisUntilFinished / 1000;
                    binding.resendOtp.setText(s);
                });
            }

            @Override
            public void onFinish() {
                binding.resendOtp.setVisibility(View.GONE);
                binding.resendOtpButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}