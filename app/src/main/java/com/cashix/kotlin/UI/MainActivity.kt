package com.cashix.kotlin.UI

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cashix.R
import com.cashix.UIFragments.HomeFragment
import com.cashix.database.DatabaseProvider
import com.cashix.kotlin.UI.SignUi.SendOTP.SendFragment
import com.cashix.kotlin.UI.onBoarding.AddUser.CreateUserFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var databaseProvider: DatabaseProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_v2)
        databaseProvider = DatabaseProvider(this)
        if (databaseProvider.getUser().getUser(1) != null) {
            supportFragmentManager.beginTransaction().replace(R.id.mainLayout, HomeFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.mainLayout, SendFragment())
                .commit()
        }
    }

    var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        val fn = supportFragmentManager
        if (fn.backStackEntryCount == 0) {
            if (doubleBackToExitPressedOnce) {
                finishAffinity()
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true
            val snackbar = Snackbar.make(findViewById(R.id.mainLayout), "0", Snackbar.LENGTH_SHORT)
            val customSnackView = layoutInflater.inflate(R.layout.snackbar, null)
            snackbar.view.setBackgroundColor(Color.TRANSPARENT)
            val snackLayout = snackbar.view as SnackbarLayout
            snackLayout.addView(customSnackView)
            snackbar.show()
            Handler(Looper.getMainLooper()).postDelayed(
                { doubleBackToExitPressedOnce = false },
                2000
            )
        } else {
            super.onBackPressed()
        }
    }
}