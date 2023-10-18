package com.cashix.kotlin.UI.cardtoBank.data


import com.cashix.kotlin.UI.cardtoBank.network.Api
import com.cashix.kotlin.UI.cardtoBank.network.DataSource
import retrofit2.Retrofit
import javax.inject.Inject

class CardRepository @Inject constructor(retrofit: Retrofit) : DataSource {
    private val service = retrofit.create(Api::class.java)
    override suspend fun getStripeIntentReady(): String {
        return service.getStripeIntent()
    }

}