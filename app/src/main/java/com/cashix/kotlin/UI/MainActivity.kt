package com.cashix.kotlin.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cashix.R
import com.cashix.UI.Home_Activity
import com.cashix.database.DatabaseProvider
import com.cashix.kotlin.UI.SignUi.SendOTP.SendFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var databaseProvider: DatabaseProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_v2)
        databaseProvider = DatabaseProvider(this)
        if (databaseProvider.getUser().getUser(1) != null) {
            startActivity(Intent(this , Home_Activity::class.java))
        } else supportFragmentManager.beginTransaction().replace(R.id.mainLayout, SendFragment())
            .commit()
    }
}