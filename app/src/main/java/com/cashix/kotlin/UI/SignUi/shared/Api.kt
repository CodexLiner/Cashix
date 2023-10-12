package com.cashix.kotlin.UI.SignUi.shared

import com.cashix.kotlin.UI.SignUi.network.LoginVerifyRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @POST("/loginSendOtp")
    suspend fun sendOTP(@Body body : LoginVerifyRequestBody): SendOTPResponse

    @GET("/category")
    suspend fun ValidateOTP(): ValidateOTPResponse
}