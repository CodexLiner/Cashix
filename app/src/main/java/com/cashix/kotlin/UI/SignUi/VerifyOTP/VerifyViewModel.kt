package com.cashix.kotlin.UI.SignUi.VerifyOTP

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.database.DatabaseProvider
import com.cashix.database.user.UserDBModel
import com.cashix.kotlin.UI.SignUi.data.AuthRepository
import com.cashix.kotlin.UI.SignUi.shared.ValidateOTPResponse
import com.cashix.kotlin.UI.onBoarding.data.BoardRepository
import com.cashix.kotlin.UI.onBoarding.shared.UserResponse
import com.cashix.kotlin.UI.shared.AuthInterceptor
import com.cashix.kotlin.UI.shared.SafeApiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val repoUser: BoardRepository,
    private val authInterceptor: AuthInterceptor,
    private val databaseProvider: DatabaseProvider,
) : ViewModel() {
    val sendVerifyOTPResponse: MutableLiveData<ValidateOTPResponse> = MutableLiveData()
    val sendUserAddedResponse: MutableLiveData<Boolean> = MutableLiveData()
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
    fun getUser(token: String) {
        SafeApiRequest.safe {
            repoUser.getUser().let {
                if (it.status.equals("success")) {
                    setUser(it.user, token = token)
                }
            }
        }
    }
    private fun setUser(user: UserResponse.user, token: String) {
        databaseProvider.getUserDB()
            .setUser(UserDBModel(user.name, user.email, user.mobile, user.pincode, token)).let {
                sendUserAddedResponse.postValue(true)
            }
    }
}