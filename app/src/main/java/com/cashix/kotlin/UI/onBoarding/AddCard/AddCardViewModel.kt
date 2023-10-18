package com.cashix.kotlin.UI.onBoarding.AddCard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.kotlin.UI.onBoarding.data.BoardRepository
import com.cashix.kotlin.UI.onBoarding.shared.AddCardResponse
import com.cashix.kotlin.UI.onBoarding.shared.AddNewCardRequest
import com.cashix.kotlin.UI.shared.SafeApiRequest

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(val repo: BoardRepository) : ViewModel() {
    val cardAddedResponse: MutableLiveData<AddCardResponse> = MutableLiveData()


    fun addCard(body: AddNewCardRequest) {
        SafeApiRequest.safe {
            repo.addNewCard(body).let {
                cardAddedResponse.postValue(it)
            }
        }
    }

    fun validateBIN(bin: String) {

    }
}