package com.cashix.network;

import androidx.annotation.NonNull;

import com.cashix.constants.STATIC;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient(String token) {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS);
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(STATIC.baseUrlbackend)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();
    }

    public static synchronized RetrofitClient getInstance(String token) {
        mInstance = new RetrofitClient(token);
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

}