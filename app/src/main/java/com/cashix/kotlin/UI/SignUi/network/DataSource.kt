package com.cashix.kotlin.UI.SignUi.network

import com.cashix.kotlin.UI.SignUi.shared.SendOTPResponse
import com.cashix.kotlin.UI.SignUi.shared.ValidateOTPResponse

interface DataSource {
    suspend fun sendOTP(mobile: String): SendOTPResponse
    suspend fun validateOTP(otp : String , token : String) : ValidateOTPResponse
}