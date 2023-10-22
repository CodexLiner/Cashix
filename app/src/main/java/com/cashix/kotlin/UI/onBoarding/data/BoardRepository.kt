package com.cashix.kotlin.UI.onBoarding.data

import com.cashix.kotlin.UI.onBoarding.netwrok.Api
import com.cashix.kotlin.UI.onBoarding.netwrok.DataSource
import com.cashix.kotlin.UI.onBoarding.shared.*
import retrofit2.Retrofit
import javax.inject.Inject

class BoardRepository @Inject constructor(retrofit: Retrofit) : DataSource {
    private val service = retrofit.create(Api::class.java)
    override suspend fun getCardDetails(): List<CardDetailsResponse> {
        return try {
            service.getCard()
        } catch (_: Exception) {
            listOf()
        }
    }

    override suspend fun getTransaction(): TransactionResponse {
        return try {
            service.getTransaction()
        } catch (_: Exception) {
            TransactionResponse()
        }
    }

    override suspend fun addBankAccount(body: AddBankRequest): AddBankResponse {
        return try {
            service.addBankAccount(body)
        } catch (_: Exception) {
            AddBankResponse()
        }
    }

    override suspend fun addNewCard(body: AddNewCardRequest): AddCardResponse {
        return try {
            service.addNewCard(body)
        } catch (_: java.lang.Exception) {
            AddCardResponse()
        }
    }

    override suspend fun addNewUser(body: AddUserRequest): UserResponse {
        return try {
            service.addNewUser(body)
        } catch (_: java.lang.Exception) {
            UserResponse()
        }
    }

    override suspend fun getUser(): UserResponse {
        return try {
            service.getUser();
        } catch (_: Exception) {
            UserResponse()
        }
    }

    override suspend fun getBankDetails(): AddBankResponse {
        return try {
            service.getBank()
        }catch (e : Exception){
            AddBankResponse()
        }
    }

    override suspend fun updateProfile(body: AddUserRequest): UserResponse {
        return try {
            service.updateProfile(body)
        } catch (e: java.lang.Exception) {
            UserResponse()
        }
    }


}