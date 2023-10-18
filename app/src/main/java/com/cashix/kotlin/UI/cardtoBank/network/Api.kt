package com.cashix.kotlin.UI.cardtoBank.network

import retrofit2.http.GET

interface Api {
    @GET("/")
    suspend fun getStripeIntent() : String
}