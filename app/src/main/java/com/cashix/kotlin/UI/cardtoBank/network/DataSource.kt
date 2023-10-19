package com.cashix.kotlin.UI.cardtoBank.network

import com.cashix.kotlin.UI.cardtoBank.shared.StripeIntent

interface  DataSource {
    suspend fun getStripeIntentReady(body: String, transactionid: String) : StripeIntent
}