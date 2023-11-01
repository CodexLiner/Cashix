package com.cashix.kotlin.UI.shared

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    private var authToken: String? = null

    fun updateToken(token: String) {
        authToken = token
    }

    fun getToken(): String? {
        return authToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("authorization", "Bearer $authToken")
            .build()
        return chain.proceed(request)
    }
}
