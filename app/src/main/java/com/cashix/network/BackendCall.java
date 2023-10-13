package com.cashix.network;

import android.app.Activity;
import android.os.AsyncTask;

import com.cashix.constants.STATIC;
import com.cashix.utils.Bar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BackendCall extends AsyncTask<String , String , String> {
    private Bar bar;
    private final Activity activity;
    private Response response;
    OkHttpClient client;
    public AsyncResponse asyncResponse;
    private JSONObject jsonObject;
    private final String url;
    private String header = "Bearer ";
    private Map<? , ?> map;

    public BackendCall(String url, Map<?, ?> map,String header , Activity activity) {
        this.url = STATIC.baseBackend+url;
        this.map = map;
        this.header += header;
        this.activity = activity;
        // new okHttp Object
        client = new OkHttpClient();
    }

    public BackendCall(String url, Activity activity) {
        this.url = STATIC.baseBackend+url;
        this.activity = activity;
        // new okHttp Object
        client = new OkHttpClient();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            if (map!=null){
               response = sendResponse(url , map);
            }else {
              response = getResponse(url);
            }
            jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());

        } catch (Exception ignored) {}
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bar = new Bar(activity.getApplicationContext());
        bar.setCancelable(false);
        bar.show();
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        bar.hide();
        asyncResponse.Result(jsonObject);
    }
    private Response getResponse(String endPoint) throws IOException {
        Request request = new Request.Builder().url("https://api.publicapis.org/entries").addHeader("authorization" , "").get().build();
        return  client.newCall(request).execute();
    }
    private Response sendResponse(String endpoint , Map<?, ?> map) throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
        final RequestBody requestBody = RequestBody.create(jsonString , MediaType.get(STATIC.mediaType));
        Request request = new Request.Builder().url(endpoint).post(requestBody).addHeader("authorization" , header).build();
        return client.newCall(request).execute();
    }

}
