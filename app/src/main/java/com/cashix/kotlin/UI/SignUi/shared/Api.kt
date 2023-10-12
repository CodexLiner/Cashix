package com.cashix.kotlin.UI.SignUi.shared

import com.cashix.kotlin.UI.SignUi.VerifyOTP.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @POST("/loginSendOtp")
    suspend fun sendOTP(@Body body: RequestBody): SendOTPResponse

    @POST("/loginVerify")
    suspend fun validateOTP(@Body body: RequestBody): ValidateOTPResponse
}