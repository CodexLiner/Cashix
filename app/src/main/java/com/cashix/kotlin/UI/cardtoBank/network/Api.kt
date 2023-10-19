package com.cashix.kotlin.UI.cardtoBank.network

import com.cashix.kotlin.UI.cardtoBank.shared.StripeIntent
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @POST("/createStripeIntent")
    suspend fun getStripeIntent(@Body body: StripeIntent) : StripeIntent
}