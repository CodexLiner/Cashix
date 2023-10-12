package com.cashix.kotlin.UI.SignUi.data

import android.util.Log
import com.cashix.kotlin.UI.SignUi.network.DataSource
import com.cashix.kotlin.UI.SignUi.VerifyOTP.RequestBody
import com.cashix.kotlin.UI.SignUi.shared.Api
import com.cashix.kotlin.UI.SignUi.shared.SendOTPResponse
import com.cashix.kotlin.UI.SignUi.shared.ValidateOTPResponse
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class AuthRepository @Inject constructor(retrofit: Retrofit) : DataSource {
    private val service = retrofit.create(Api::class.java)

    override suspend fun sendOTP(mobile: String): SendOTPResponse {
        return try {
            service.sendOTP(
                RequestBody(
                    mobile,
                    mobile
                )
            )
        } catch (e: HttpException) {
            SendOTPResponse("failed", "", mobile)
        }
    }

    override suspend fun validateOTP(otp: String, token: String): ValidateOTPResponse {
        return try {
            service.validateOTP(
                RequestBody(
                    otp,
                    token
                )
            );
        } catch (e: Exception) {
            Log.d("TAG", "DaggerTest error on valodate: ${e.localizedMessage}")
            ValidateOTPResponse("", "failed", false)
        }
    }

}