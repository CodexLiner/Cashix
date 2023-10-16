package com.cashix.kotlin.UI

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.cashix.R
import com.cashix.database.DatabaseProvider
import com.cashix.kotlin.UI.SignUi.SendOTP.SendFragment
import com.cashix.kotlin.UI.onBoarding.AddBank.AddBankFragment
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
            change.go(AddBankFragment::class.java);
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