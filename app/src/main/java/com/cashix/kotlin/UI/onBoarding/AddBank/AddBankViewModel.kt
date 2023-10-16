package com.cashix.kotlin.UI.onBoarding.AddBank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.kotlin.UI.onBoarding.data.BoardRepository
import com.cashix.kotlin.UI.onBoarding.shared.AddBankRequest
import com.cashix.kotlin.UI.onBoarding.shared.AddBankResponse
import com.cashix.kotlin.UI.shared.SafeApiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddBankViewModel @Inject constructor(repo: BoardRepository) : ViewModel() {
    val addBankResponse: MutableLiveData<AddBankResponse> = MutableLiveData()
    fun addBankAccount(body: AddBankRequest) {
        SafeApiRequest.safe {

        }
    }
}