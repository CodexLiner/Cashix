package com.cashix.UIFragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cashix.R;
import com.cashix.constants.STATIC;
import com.cashix.databinding.FragmentWalletTransferBinding;
import com.cashix.utils.common;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.CheckoutActivity;
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

public class WalletTransfer extends Fragment implements PaymentResultListener {
    private FragmentWalletTransferBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WalletTransfer() {
        // Required empty public constructor
    }
    public static WalletTransfer newInstance(String param1, String param2) {
        WalletTransfer fragment = new WalletTransfer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWalletTransferBinding.inflate(inflater);
        binding.buttonPay.setOnClickListener((View pay)->{
          if (common.isEmpty(binding.walletAmount)){
            startPayment();
          }
        });
        return binding.getRoot();
    }
    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = requireActivity();

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
           // addTransactionstoDb("success" , "");
            Toast.makeText(requireContext(), "Payment Successful: ", Toast.LENGTH_SHORT).show();
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
            //addTransactionstoDb("failed" , "");
            Log.d("TAG", +code+" startPayment: "+response);
            //  Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentError", e);
        }
    }
//    private void addTransactionstoDb(String tStatus, String Tid) {
//        try{
//            Map<String , String> map = new HashMap<>();
//            map.put(STATIC.TransactionID ,String.valueOf( System.currentTimeMillis()));
//            map.put(STATIC.Amount , binding.walletAmount.getText().toString());
//            map.put(STATIC.DESC , "DESC");
//            map.put(STATIC.TransactionType ,  "wallet");
//            map.put(STATIC.TransactionStatus , tStatus);
//           //  map.put(STATIC.AuthKey , model.getMobile());
//            Gson gson = new Gson();
//            String jsonString = gson.toJson(map);
//
//            final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
//            Request request = new Request.Builder().url(STATIC.baseBackend +"payments").addHeader("authorization" , "Bearer "+model.getAuth()).post(requestBody).build();
//            new OkHttpClient()
//                    .newCall(request)
//                    .enqueue(
//                            new Callback() {
//                                @Override
//                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                                    Log.d("TAG", "onResponse: payment failed for some reason");
//                                }
//
//                                @Override
//                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                                    Log.d("TAG", "onResponse: payment successful");
//                                }
//                            });
//        }catch (Exception ignored){ }
//    }
}