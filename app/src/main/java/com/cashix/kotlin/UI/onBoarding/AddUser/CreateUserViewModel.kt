package com.cashix.kotlin.UI.onBoarding.AddUser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.database.DatabaseProvider
import com.cashix.database.user.UserDBModel
import com.cashix.kotlin.UI.onBoarding.data.BoardRepository
import com.cashix.kotlin.UI.onBoarding.shared.AddUserRequest
import com.cashix.kotlin.UI.onBoarding.shared.UserResponse
import com.cashix.kotlin.UI.shared.AuthInterceptor
import com.cashix.kotlin.UI.shared.SafeApiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    val repo: BoardRepository,
    private val authInterceptor: AuthInterceptor,
    private val databaseProvider: DatabaseProvider,
) : ViewModel() {
    val responseAddingUser: MutableLiveData<UserResponse> = MutableLiveData()
    private val responseDB: MutableLiveData<Long> = MutableLiveData()
    fun updateToken(token: String) {
        authInterceptor.updateToken(token)
    }

    fun saveUserProfile(name: String, email: String, pin: String = "110000") {
        SafeApiRequest.safe {
            repo.addNewUser(AddUserRequest(name, email, pin)).let {
                responseAddingUser.postValue(it)
            }
        }
    }

    fun saveUserInLocalDatabase(user: UserResponse.user, token: String) {
        databaseProvider.getUser()
            .setUser(UserDBModel(user.name, user.email, user.mobile, user.pincode, token)).let {
                responseDB.postValue(it)
            }

    }
}