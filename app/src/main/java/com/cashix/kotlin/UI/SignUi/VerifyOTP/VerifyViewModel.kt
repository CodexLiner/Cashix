package com.cashix.kotlin.UI.SignUi.VerifyOTP

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.kotlin.UI.SignUi.data.AuthRepository
import com.cashix.kotlin.UI.SignUi.shared.ValidateOTPResponse
import com.cashix.kotlin.UI.shared.AuthInterceptor
import com.cashix.kotlin.UI.shared.SafeApiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val authInterceptor: AuthInterceptor,
) : ViewModel() {
    val sendVerifyOTPResponse: MutableLiveData<ValidateOTPResponse> = MutableLiveData()

    fun updateToken(token: String) {
        authInterceptor.updateToken(token)
    }
    fun validateOTP(otp: String, token: String) {
        SafeApiRequest.safe {
            repo.validateOTP(otp, token).let {
                sendVerifyOTPResponse.postValue(it)
            }
        }
    }

}