package com.cashix.database

import android.content.Context
import com.cashix.database.bank.BankDataHelper
import com.cashix.database.user.userDatabaseHelper
import javax.inject.Inject

class DatabaseProvider @Inject constructor(val context: Context) {
    fun getUserDB(): userDatabaseHelper {
        return userDatabaseHelper(context)
    }

    fun getBankDB(): BankDataHelper {
        return BankDataHelper(context)
    }
}