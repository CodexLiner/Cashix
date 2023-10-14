package com.cashix.kotlin.UI.onBoarding.netwrok

import com.cashix.kotlin.UI.onBoarding.shared.*

interface DataSource {
    suspend fun getCardDetails(): CardDetailsResponse
    suspend fun getTransaction(): TransactionResponse
    suspend fun addBankAccount(body: AddBankRequest): AddBankResponse
    suspend fun addNewCard(body: AddNewCardRequest): AddCardResponse
    suspend fun addNewUser(body: AddUserRequest): UserResponse
    suspend fun getUser(): UserResponse

}