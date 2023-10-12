package com.cashix.kotlin.UI.shared

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClientV2 {
    companion object {
        operator fun invoke(): Retrofit {
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
            okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build()
        }
    }
}