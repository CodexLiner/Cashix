package com.cashix.payment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.cashix.database.user.userDatabaseHelper;
import com.cashix.database.user.userDatabaseModel;
import com.google.gson.Gson;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CheckoutActivity extends AppCompatActivity {
    private static final String TAG = "CheckoutActivity";
    private static final String BACKEND_URL = "http://192.168.0.108:4242";

    private String paymentIntentClientSecret;
    private PaymentSheet paymentSheet;
    private int payAmount ;
    private double CreditableMoney;
    Dialog alertDialog;
    private Button payButton;
    private Bundle bundle;
    TextView payAmountView , CreditAmount;
    private  String UUIDs;
    userDatabaseModel model;
    userDatabaseHelper db ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout);
        bundle = getIntent().getExtras();
        payAmount = bundle.getInt(STATIC.Amount);
        // Hook up the view
        payButton = findViewById(R.id.pay_button);
        payAmountView = findViewById(R.id.payAmount);
        CreditAmount = findViewById(R.id.creditAmount);
        payButton.setOnClickListener(this::onPayClicked);
        payButton.setEnabled(false);
        UUIDs = UUID.randomUUID().toString();
        //local dp
        db = new userDatabaseHelper(getApplicationContext());
        model = db.getNote(1);
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        PaymentConfiguration.init(
                this,
                "pk_live_51JxaLNSBlkdvTJctb6Ke8yLG2u38p7Uy8kdhc9zk9ZukfWevAKXTSTdjhWOeelZTmj9L9d5cV1FeN3G8bIUQGPOo00Z7TSfXVz");
        alertDialog = new Dialog(this );
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setContentView(R.layout.dialog);
        alertDialog.setCancelable(true);
//        alertDialog.show();
        updatePaymentinfo(payAmount);
        fetchPaymentIntent();
    }

    private void updatePaymentinfo(int payAmount) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                payAmountView.setText(String.valueOf(payAmount));
                CreditableMoney =  5 * (payAmount / (double) 100);
                Log.d(TAG, "runUi: "+CreditableMoney);
                CreditAmount.setText(String.valueOf(payAmount - CreditableMoney));
            }
        });

    }

    private void onPayClicked(View view) {
        onPayClicked();
    }

    private void showAlert(String title, @Nullable String message) {
        runOnUiThread(() -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Ok", null)
                    .create();
            dialog.show();
        });
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }

    private void fetchPaymentIntent() {
        final String shoppingCartContent = "{\"items\": [ {\"id\":\"500000\"}]}";
        Map<String , String> map = new HashMap<>();
        map.put(STATIC.TransactionID , UUIDs);
        map.put(STATIC.Amount , String.valueOf(payAmount * 100));
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
        final RequestBody requestBody = RequestBody.create(jsonString, MediaType.get(STATIC.mediaType));

        Request request = new Request.Builder()
                .url(STATIC.baseBackend + "create-payment-intent")
                .post(requestBody)
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        showAlert("Failed to load data", "Error: " + e.toString());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response
                    ) throws IOException {
                        if (!response.isSuccessful()) {
                            showAlert("Failed to load page", "Error: " + response.toString()
                            );
                        } else {
                            final JSONObject responseJson = parseResponse(response.body());
                            paymentIntentClientSecret = responseJson.optString("clientSecret");
                            runOnUiThread(() -> payButton.setEnabled(true));
                            alertDialog.dismiss();
//                            onPayClicked();
                        }
                    }
                });
    }

    private JSONObject parseResponse(ResponseBody responseBody) {
        if (responseBody != null) {
            try {
                return new JSONObject(responseBody.string());
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error parsing response", e);
            }
        }

        return new JSONObject();
    }

    private void onPayClicked() {
        PaymentSheet.Address address = new PaymentSheet.Address(null, "in", null, null, null, null);
        PaymentSheet.Configuration configuration = new PaymentSheet.Configuration(
                "null",
                null, null,
                null,
                new PaymentSheet.BillingDetails(address, "null", "Gopal Meena", "+919399846909"),
                false);

        // Present Payment Sheet
        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration);
        Log.d(TAG, "onPayClicked: " + configuration.getDefaultBillingDetails().getAddress().toString());
    }

    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            showToast("Payment complete!");
            addTransactionstoDb("success" , UUIDs);
            if (bundle!=null && bundle.getBoolean("renType", false)){
                addTransactioForTransferBank(UUIDs);
            }
            Log.d(TAG, "onPaymentSheetResult: " + paymentSheetResult.toString());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            addTransactionstoDb("canceled" , UUIDs);
//            Log.i(TAG, "Payment canceled!");
//            finish();
//            overridePendingTransition(0,0);
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {

          //   addTransactionstoDb("failed" , UUIDs);
            Throwable error = ((PaymentSheetResult.Failed) paymentSheetResult).getError();
            showAlert("Payment failed", error.getLocalizedMessage());
        }
    }

    private void addTransactioForTransferBank(String uuiDs) {
        try{
            Map<String , String> map = new HashMap<>();
            map.put(STATIC.DESC , "DESC");
            map.put("transactionAmount" , String.valueOf(bundle.getInt("amount")));
            map.put("transactionDesc" , bundle.getString("uDecription"));
            map.put("transactionDate" , bundle.getString(""));
            map.put("transactionAC", bundle.getString("uAccount"));
            map.put("transactionIfsc", bundle.getString("uIfsc"));
            map.put("transactionName", bundle.getString("uName"));
//        TODO: to implement this function
            Gson gson = new Gson();
            String jsonString = gson.toJson(map);
            final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
            Request request = new Request.Builder().url(STATIC.baseBackend +"payments").addHeader("authorization" , "Bearer "+model.getAuth()).post(requestBody).build();
            new OkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                }
            });
        }catch (Exception ignored){}
    }

    private void addTransactionstoDb(String tStatus, String Tid) {
        try{
            Map<String , String> map = new HashMap<>();
            map.put(STATIC.TransactionID , Tid);
            map.put(STATIC.Amount , String.valueOf(payAmount));
            map.put(STATIC.TransactionType , bundle.getString(STATIC.TransactionType , "Transfer"));
            map.put(STATIC.TransactionStatus , tStatus);
            map.put(STATIC.AuthKey , model.getMobile());
            Gson gson = new Gson();
            String jsonString = gson.toJson(map);

            final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
            Request request = new Request.Builder().url(STATIC.baseBackend +"payments").addHeader("authorization" , "Bearer "+model.getAuth()).post(requestBody).build();
      new OkHttpClient()
          .newCall(request)
          .enqueue(
              new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d(TAG, "onResponse: payment failed for some reason");
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                 Log.d(TAG, "onResponse: payment successfull");
                }
              });
        }catch (Exception ignored){ }
    }
}