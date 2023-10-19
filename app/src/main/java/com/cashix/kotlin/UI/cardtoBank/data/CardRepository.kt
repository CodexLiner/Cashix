package com.cashix.kotlin.UI.cardtoBank.data


import android.util.Log
import com.cashix.kotlin.UI.cardtoBank.network.Api
import com.cashix.kotlin.UI.cardtoBank.network.DataSource
import com.cashix.kotlin.UI.cardtoBank.shared.StripeIntent
import retrofit2.Retrofit
import javax.inject.Inject

class CardRepository @Inject constructor(retrofit: Retrofit) : DataSource {
    private val service = retrofit.create(Api::class.java)
    override suspend fun getStripeIntentReady(body: String, transactionid: String): StripeIntent {
        return try {
            service.getStripeIntent(StripeIntent(body, transactionid))
        } catch (e: Exception) {
            StripeIntent("", "")
        }
    }

}