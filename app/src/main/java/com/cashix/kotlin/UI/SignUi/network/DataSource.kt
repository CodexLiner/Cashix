package com.cashix.kotlin.UI.SignUi.network

import com.cashix.kotlin.UI.SignUi.shared.SendOTPResponse

interface DataSource {
    suspend fun sendOTP(mobile: String): SendOTPResponse
}