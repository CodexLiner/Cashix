package com.cashix.network;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    private String authToken;

    public AuthInterceptor(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Add the Authorization header with the bearer token
        Request newRequest = originalRequest.newBuilder()
                .header("authorization", "Bearer " + authToken)
                .build();

        return chain.proceed(newRequest);
    }
}
