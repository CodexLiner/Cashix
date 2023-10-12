package com.cashix.kotlin.UI.SignUi.data

import android.util.Log
import com.cashix.kotlin.UI.SignUi.network.DataSource
import com.cashix.kotlin.UI.SignUi.network.LoginVerifyRequestBody
import com.cashix.kotlin.UI.SignUi.shared.Api
import com.cashix.kotlin.UI.SignUi.shared.SendOTPResponse
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class AuthRepository @Inject constructor(retrofit: Retrofit) : DataSource {
    private val service = retrofit.create(Api::class.java)

    override suspend fun sendOTP(mobile: String): SendOTPResponse {
        return try {
            service.sendOTP(LoginVerifyRequestBody(mobile, mobile))
        } catch (e: HttpException) {
            SendOTPResponse("failed", "", mobile)
        }
    }

}