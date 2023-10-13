package com.cashix.kotlin.UI.onBoarding.netwrok

import com.cashix.kotlin.UI.onBoarding.shared.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("/")
    suspend fun getCard(): CardDetailsResponse

    @GET("/")
    suspend fun getTransaction(): TransactionResponse

    @POST("/")
    suspend fun addBankAccount(@Body body: AddBankRequest): AddBankResponse

    @POST("/")
    suspend fun addNewCard(@Body body: AddNewCardRequest): AddCardResponse
}