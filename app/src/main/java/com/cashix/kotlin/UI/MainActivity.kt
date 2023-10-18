package com.cashix.kotlin.UI

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.cashix.R
import com.cashix.UIFragments.HomeFragment
import com.cashix.database.DatabaseProvider
import com.cashix.kotlin.UI.SignUi.SendOTP.SendFragment
import com.cashix.kotlin.UI.cardtoBank.CardToBankFragment
import com.cashix.kotlin.UI.onBoarding.AddBank.AddBankFragment
import com.cashix.kotlin.UI.onBoarding.AddCard.AddCardFragment
import com.cashix.utils.SnakeBar
import com.cashix.utils.change
import com.cashix.utils.changeHelper
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
        val change = change(changeHelper(supportFragmentManager, R.id.mainLayout))
        if (databaseProvider.getUser().getUser(1) != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainLayout, CardToBankFragment())
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .commit()
        } else {
            change.go(SendFragment::class.java);
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
            SnakeBar(this@MainActivity).showSnackbar("Press Back Again to Exit")
            Handler(Looper.getMainLooper()).postDelayed(
                { doubleBackToExitPressedOnce = false },
                2000
            )
        } else {
            super.onBackPressed()
        }
    }
}