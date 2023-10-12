package com.cashix.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.cashix.database.user.userDatabaseHelper;
import com.cashix.database.user.userDatabaseModel;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class pay_wallet extends AppCompatActivity implements PaymentResultListener {
    userDatabaseModel model;
    userDatabaseHelper db ;
    View view;
    customButton button;
    EditText walleAmount , walleReason;
    String payAmount = "" , DESC = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_wallet);
        view = findViewById(R.id.customButton);
        walleAmount = findViewById(R.id.walletAmount);
        walleReason = findViewById(R.id.walletReson);
        button = new customButton(getApplicationContext() , view);
        button.setText("PAY NOW");
        db = new userDatabaseHelper(getApplicationContext());
        model = db.getUser(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = walleAmount.getText().toString().trim();
                String reason = walleAmount.getText().toString().trim();
                if (TextUtils.isEmpty(amount)){
                    walleAmount.requestFocus();
                    return;
                }
                DESC = reason;
                runPaymentFunction(amount);
            }
        });
    }

    private void runPaymentFunction(String amount) {
        try{

            payAmount = amount;
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
//            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            addTransactionstoDb("success" , "");
            Toast.makeText(this, "Payment Successful: ", Toast.LENGTH_SHORT).show();
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
            addTransactionstoDb("failed" , "");
            Log.d("TAG", +code+" startPayment: "+response);
          //  Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentError", e);
        }
    }
    private void addTransactionstoDb(String tStatus, String Tid) {
        try{
            Map<String , String> map = new HashMap<>();
            map.put(STATIC.TransactionID ,String.valueOf( System.currentTimeMillis()));
            map.put(STATIC.Amount , payAmount);
            map.put(STATIC.DESC , DESC);
            map.put(STATIC.TransactionType ,  "wallet");
            map.put(STATIC.TransactionStatus , tStatus);
            map.put(STATIC.AuthKey , model.getMobile());
            Gson gson = new Gson();
            String jsonString = gson.toJson(map);

            final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
            Request request = new Request.Builder().url(STATIC.baseUrlbackend +"payments").addHeader("authorization" , "Bearer "+model.getAuth()).post(requestBody).build();
            new OkHttpClient()
                    .newCall(request)
                    .enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    Log.d("TAG", "onResponse: payment failed for some reason");
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    Log.d("TAG", "onResponse: payment successful");
                                }
                            });
        }catch (Exception ignored){ }
    }

}