package com.cashix.kotlin.UI.cardtoBank

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cashix.kotlin.UI.cardtoBank.data.CardRepository
import com.cashix.kotlin.UI.cardtoBank.shared.StripeIntent
import com.cashix.kotlin.UI.shared.SafeApiRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardToBankViewModel @Inject constructor(val repo: CardRepository) : ViewModel() {
    val stripeIntentResult: MutableLiveData<StripeIntent> = MutableLiveData()
    val onSheetDismiss: MutableLiveData<String> = MutableLiveData()
    var isNotLoading = false

    fun getStripeIntent(amount: String, transactionid: String) {
        SafeApiRequest.safe {
            repo.getStripeIntentReady(amount, transactionid).let {
                stripeIntentResult.postValue(it)
                isNotLoading = !isNotLoading
            }
        }
    }
}