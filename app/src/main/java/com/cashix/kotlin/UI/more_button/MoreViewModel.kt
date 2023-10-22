package com.cashix.kotlin.UI.more_button

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.database.DatabaseProvider
import com.cashix.database.user.UserDBModel
import com.cashix.kotlin.UI.onBoarding.data.BoardRepository
import com.cashix.kotlin.UI.onBoarding.shared.AddUserRequest
import com.cashix.kotlin.UI.onBoarding.shared.UserResponse
import com.cashix.kotlin.UI.shared.SafeApiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    val user: BoardRepository,
    val databaseProvider: DatabaseProvider,
) : ViewModel() {
    val userUpdateResult: MutableLiveData<UserResponse> = MutableLiveData()


    fun updateProfile(name: String, email: String, pincode: String) {
        SafeApiRequest.safe {
            user.updateProfile(AddUserRequest(name, email, pincode)).let {
                userUpdateResult.postValue(it)
            }
        }
    }

    fun getBankDetails(){
        SafeApiRequest.safe {

        }
    }

    fun saveUserInLocalDatabase(user: UserResponse.user, token: String) {
        databaseProvider.getUserDB()
            .setUser(
                UserDBModel(
                    user.name,
                    user.email,
                    user.mobile,
                    user.pincode,
                    databaseProvider.getUserDB().getUser(1).authKey
                )
            ).let {

            }

    }
}