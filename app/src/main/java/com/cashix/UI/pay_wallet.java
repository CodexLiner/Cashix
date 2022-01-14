package com.cashix.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cashix.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class pay_wallet extends AppCompatActivity implements PaymentResultListener {
    View view;
    customButton button;
    EditText walleAmount , walleReason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_wallet);
        view = findViewById(R.id.customButton);
        walleAmount = findViewById(R.id.walletAmount);
        walleReason = findViewById(R.id.walletReson);
        button = new customButton(getApplicationContext() , view);
        button.setText("PAY NOW");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = walleAmount.getText().toString().trim();
                String reason = walleAmount.getText().toString().trim();
                if (TextUtils.isEmpty(amount)){
                    walleAmount.requestFocus();
                    return;
                }
                runPaymentFunction();
            }
        });
    }

    private void runPaymentFunction() {
        try{
            startPayment();
        }catch (Exception e){
      Log.d("TAG", "runPaymentFunction: "+e);
        }
    }
    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setImage(R.drawable.splash);


        try {
            JSONObject options = new JSONObject();
//            options.put("name", "Razorpay Corp");
//            options.put("description", "Demoing Charges");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "meenagopal051@gmail.com");
            preFill.put("contact", "9399846909");
            preFill.put("method", "wallet");

            JSONObject theme = new JSONObject();
            theme.put("hide_topbar" ,true);
            theme.put("color" ,"#6C63FF");
            theme.put("backdrop_color" ,"#2ebf91");

            JSONObject modal = new JSONObject();
            modal.put("animation" , false);
            modal.put("confirm_close" , false);


            options.put("prefill", preFill);
            options.put("modal", modal);
            options.put("theme" , theme);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("TAG", "startPayment: "+e);
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentError", e);
        }
    }
}