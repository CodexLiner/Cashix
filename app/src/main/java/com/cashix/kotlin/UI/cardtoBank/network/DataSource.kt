package com.cashix.kotlin.UI.cardtoBank.network

interface  DataSource {
    suspend fun getStripeIntentReady() : String
}