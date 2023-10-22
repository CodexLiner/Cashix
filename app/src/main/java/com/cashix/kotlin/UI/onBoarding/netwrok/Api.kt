package com.cashix.kotlin.UI.onBoarding.netwrok

import com.cashix.kotlin.UI.onBoarding.shared.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("/getCards")
    suspend fun getCard(): List<CardDetailsResponse>

    @GET("/")
    suspend fun getTransaction(): TransactionResponse

    @GET("/getUser")
    suspend fun getUser(): UserResponse

    @GET("/getBank")
    suspend fun getBank(): AddBankResponse

    @POST("/addBank")
    suspend fun addBankAccount(@Body body: AddBankRequest): AddBankResponse

    @POST("/addCard")
    suspend fun addNewCard(@Body body: AddNewCardRequest): AddCardResponse

    @POST("/addUser")
    suspend fun addNewUser(@Body body: AddUserRequest): UserResponse

    @POST("/updateUser")
    suspend fun updateProfile(@Body body: AddUserRequest): UserResponse

}