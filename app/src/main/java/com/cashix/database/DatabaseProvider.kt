package com.cashix.database

import android.content.Context
import com.cashix.database.bank.BankDatabase
import com.cashix.database.user.UserDatabase
import javax.inject.Inject

class DatabaseProvider @Inject constructor(val context: Context) {
    fun getUserDB(): UserDatabase {
        return UserDatabase(context)
    }

    fun getBankDB(): BankDatabase {
        return BankDatabase(context)
    }
}