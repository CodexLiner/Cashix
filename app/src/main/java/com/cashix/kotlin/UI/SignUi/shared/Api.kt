package com.cashix.kotlin.UI.SignUi.shared

import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("/loginSendOtp")
    suspend fun sendOTP(@Body body: RequestBody): SendOTPResponse

    @POST("/loginVerify")
    suspend fun validateOTP(@Body body: RequestBody): ValidateOTPResponse
}