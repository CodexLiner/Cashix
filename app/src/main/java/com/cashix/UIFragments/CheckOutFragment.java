package com.cashix.UIFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cashix.constants.STATIC;
import com.cashix.database.user.userDatabaseHelper;
import com.cashix.database.user.userDatabaseModel;
import com.cashix.databinding.FragmentCheckOutBinding;
import com.cashix.utils.Binder.dialog;
import com.cashix.utils.bar;
import com.cashix.utils.common;
import com.google.gson.Gson;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CheckOutFragment extends Fragment {
    private static final String TAG = "TAG";
    private com.cashix.utils.bar bar;
    private Bundle bundle;
    private dialog dialog;
    private FragmentCheckOutBinding binding;
    private String paymentIntentClientSecret;

    private PaymentSheet paymentSheet;
    TextView payAmountView , CreditAmount;
    private  String UUIDs;
    private int payAmount ;
    private double CreditableMoney;
    private Button payButton;
    userDatabaseModel model;
    userDatabaseHelper db ;
    public CheckOutFragment(Bundle bundle) {
        this.bundle = bundle;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckOutFragment() {
        // Required empty public constructor
    }
    public static CheckOutFragment newInstance(String param1, String param2) {
        CheckOutFragment fragment = new CheckOutFragment();
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
        binding = FragmentCheckOutBinding.inflate(inflater);
        db = new userDatabaseHelper(requireContext());
        model = db.getUser(1);
        binding.backButton.setOnClickListener(v -> { common.back(requireActivity());});
        payAmountView = binding.payAmount;
        payButton = binding.payButton;
        payButton.setEnabled(false);
        payAmount = bundle.getInt(STATIC.Amount);
        UUIDs = UUID.randomUUID().toString();
        CreditAmount = binding.creditAmount;
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        PaymentConfiguration.init(
                requireContext(),
                "pk_live_51JxaLNSBlkdvTJctb6Ke8yLG2u38p7Uy8kdhc9zk9ZukfWevAKXTSTdjhWOeelZTmj9L9d5cV1FeN3G8bIUQGPOo00Z7TSfXVz");
        updatePayments(payAmount);
        fetchPaymentIntent();
        binding.payButton.setOnClickListener((View button)->{
            onPayClicked();
        });
        return binding.getRoot();
    }
    private void updatePayments(int payAmount) {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.payAmount.setText(String.valueOf(payAmount));
                CreditableMoney =  5 * (payAmount / (double) 100);
                CreditAmount.setText(String.valueOf(payAmount - CreditableMoney));
            }
        });

    }
    private void showToast(String message) {
        requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show());
    }

    private void fetchPaymentIntent() {
        dialog = new dialog(requireContext());
        bar = new bar(requireContext());
        bar.setCancelable(false);
        bar.show();
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
                bar.hide();
                requireActivity().runOnUiThread(()->{
                    dialog.setMessage("Failed to load data Check Your Internet");
                    dialog.show();});
                }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response
            ) throws IOException {
                if (!response.isSuccessful()) {
                    requireActivity().runOnUiThread(()->{
                        dialog.setMessage("Failed to load page Error: " + response.toString());
                    });
                } else {
                    final JSONObject responseJson = parseResponse(response.body());
                    paymentIntentClientSecret = responseJson.optString("clientSecret");
                    requireActivity().runOnUiThread(() -> binding.payButton.setEnabled(true));
                    bar.hide();
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
                true);

        // Present Payment Sheet
        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration);
        Log.d(TAG, "onPayClicked: " + configuration.getDefaultBillingDetails().getAddress().toString());
    }

    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            showToast("Payment complete!");
            addTransactionsDb("success" , UUIDs);
            if (bundle!=null && bundle.getBoolean("renType", false)){
                addTransactionForTransferBank(UUIDs);
            }
            Log.d(TAG, "onPaymentSheetResult: " + paymentSheetResult.toString());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            addTransactionsDb("canceled" , UUIDs);
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Throwable error = ((PaymentSheetResult.Failed) paymentSheetResult).getError();
            requireActivity().runOnUiThread(()->{
                dialog.setMessage("Payment failed "+error.getLocalizedMessage());
                dialog.show();
            });
        }
    }

    private void addTransactionForTransferBank(String uuiDs) {
        try{
            Map<String , String> map = new HashMap<>();
            map.put(STATIC.DESC , "DESC");
            map.put("transactionAmount" , String.valueOf(bundle.getInt("amount")));
            map.put("transactionDesc" , bundle.getString("uDescription"));
            map.put("transactionDate" , bundle.getString(""));
            map.put("transactionAC", bundle.getString("uAccount"));
            map.put("transactionIfsc", bundle.getString("uIfsc"));
            map.put("transactionName", bundle.getString("uName"));
//        TODO: to implement this function
            Gson gson = new Gson();
            String jsonString = gson.toJson(map);
            final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
            Request request = new Request.Builder().url(STATIC.baseBackend +"payments").addHeader("authorization" , "Bearer "+model.getAuth()).post(requestBody).build();
            new OkHttpClient().newCall(request).execute();
        }catch (Exception ignored){}
    }

    private void addTransactionsDb(String tStatus, String Tid) {
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
            new OkHttpClient().newCall(request).execute();
        }catch (Exception ignored){ }
    }
}