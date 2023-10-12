package com.cashix.kotlin.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cashix.R
import com.cashix.kotlin.UI.SignUi.SendOTP.SendFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_v2)
        supportFragmentManager.beginTransaction().replace(R.id.mainLayout , SendFragment()).commit()
    }
}