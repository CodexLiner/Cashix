package com.cashix.kotlin.UI.Home.HomePage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.kotlin.UI.Home.data.BrowseDataRepository
import com.cashix.kotlin.UI.onBoarding.data.BoardRepository
import com.cashix.kotlin.UI.onBoarding.shared.CardDetailsResponse
import com.cashix.kotlin.UI.shared.SafeApiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repo : BrowseDataRepository , val card : BoardRepository) : ViewModel() {
    val creditCardData: MutableLiveData<List<CardDetailsResponse>> = MutableLiveData()


    fun getCreditCardData() {
        SafeApiRequest.safe {
            card.getCardDetails().let {
                creditCardData.postValue(it)
            }
        }
    }
    fun getTransactionsData(){

    }
}