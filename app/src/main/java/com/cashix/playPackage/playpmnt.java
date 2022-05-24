package com.cashix.playPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cashix.R;
import com.google.firebase.auth.FirebaseAuth;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Objects;

public class playpmnt extends Activity implements PaymentResultListener {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playpmnt);
        startPayment();

    }
    public void startPayment() {
        int s = getIntent().getIntExtra("rate" , 1);
        s = s * 100;
        String wr = getIntent().getStringExtra("work");
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setImage(R.drawable.splash);


        try {
            JSONObject options = new JSONObject();
            options.put("name", "Cashix WebPortal");
            options.put("description", wr);
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(s));
            String ss = Objects.requireNonNull(auth.getCurrentUser()).getEmail();

            JSONObject preFill = new JSONObject();
            preFill.put("email", ss);
//            preFill.put("contact", "+91"+ss);
//            preFill.put("method", "wallet");

            JSONObject theme = new JSONObject();
            theme.put("hide_topbar" ,true);
            theme.put("color" ,"#6C63FF");
            theme.put("backdrop_color" ,"#2ebf91");

            JSONObject modal = new JSONObject();
            modal.put("animation" , false);
            modal.put("confirm_close" , false);


//            options.put("prefill", preFill);
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
            Toast.makeText(this, "Payment failed :", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentError", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}