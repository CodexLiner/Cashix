package com.cashix.network;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.POST;

public interface Api {
    @POST("/loginSendOtp")
    Call<loginSendOtpResponse> loginSendOtp(@Body LoginVerifyRequestBody body);
    @POST("/loginVerify")
    Call<loginSendOtpResponse> loginVerifyOtp(@Body LoginVerifyRequestBody body);

}