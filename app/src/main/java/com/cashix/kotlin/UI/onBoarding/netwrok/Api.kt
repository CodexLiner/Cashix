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

    @GET("/getUser")
    suspend fun getUser(): UserResponse

    @POST("/")
    suspend fun addBankAccount(@Body body: AddBankRequest): AddBankResponse

    @POST("/")
    suspend fun addNewCard(@Body body: AddNewCardRequest): AddCardResponse

    @POST("/addUser")
    suspend fun addNewUser(@Body body: AddUserRequest): UserResponse
}