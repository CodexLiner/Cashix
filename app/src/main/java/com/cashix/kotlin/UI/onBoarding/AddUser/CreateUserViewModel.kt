package com.cashix.kotlin.UI.onBoarding.AddUser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.kotlin.UI.onBoarding.data.BoardRepository
import com.cashix.kotlin.UI.onBoarding.shared.AddUserResponse
import com.cashix.kotlin.UI.shared.SafeApiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(val repo: BoardRepository) : ViewModel() {
    val responseAddingUser : MutableLiveData<AddUserResponse> = MutableLiveData()

    fun saveUserProfile(name: String, email: String, pin: String = "110000") {
        SafeApiRequest.safe {

        }
    }
}