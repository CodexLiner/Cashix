package com.cashix.kotlin.UI.Home.HomePage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val creditCardData: MutableLiveData<Any> = MutableLiveData()


    fun getCreditCardData() {

    }
    fun getTransactionsData(){

    }
}