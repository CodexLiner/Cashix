package com.cashix.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.cashix.R;
import com.cashix.UIFragments.LoginFragment;

import com.cashix.constants.STATIC;
import com.cashix.receivers.connection;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    private FrameLayout MainFrame;
    FragmentTransaction fragmentTransaction;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // checking internet
        checkConnection();
        setTheme(R.style.Theme_Crepaid);
        setContentView(R.layout.activity_main);
        MainFrame = findViewById(R.id.MainFrame);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .add(R.id.MainFrame, LoginFragment.class, null)
                .commit();
    }

    public void sendOtpViaServer(String trim) {
        Map<String , String> map = new HashMap<>();
        map.put("mobile" , trim);
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
        final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
        Request request = new Request.Builder().url(STATIC.baseUrlbackend +"crepaid_login/verify").post(requestBody).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        STATIC.makeToast(getApplicationContext() , "Failed To Send OTP"+e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject;
                    jsonObject = new JSONObject(response.body().string());
                    if (!jsonObject.optString("lastToken").equals(" ") || !jsonObject.optString("lastToken").isEmpty()){
                        Bundle bundle = new Bundle();
                        bundle.putString("token" , jsonObject.optString("lastToken") );
                        Log.d("TAG", "onResponse: "+ jsonObject.optString("lastToken"));
                        bundle.putString("mobile" , trim);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void checkConnection() {
        connection conn = new connection();
        conn.onReceive(getApplicationContext() , null);
    }


}