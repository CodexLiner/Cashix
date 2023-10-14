package com.cashix.kotlin.UI.SignUi.SendOTP

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.kotlin.UI.SignUi.data.AuthRepository
import com.cashix.kotlin.UI.SignUi.shared.SendOTPResponse
import com.cashix.kotlin.UI.shared.SafeApiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendViewModel @Inject constructor(private val repo: AuthRepository) : ViewModel() {
    val otpResponseMutableLiveData : MutableLiveData<SendOTPResponse> = MutableLiveData()

    fun sendOtp(mobile: String) {
        SafeApiRequest.safe {
            repo.sendOTP(mobile).let {
                otpResponseMutableLiveData.postValue(it)
            }
        }
    }
}