package com.cashix.database

import android.content.Context
import com.cashix.database.user.userDatabaseHelper
import javax.inject.Inject

class DatabaseProvider @Inject constructor(val context: Context) {
    fun getUser(): userDatabaseHelper {
        return userDatabaseHelper(context)
    }
}