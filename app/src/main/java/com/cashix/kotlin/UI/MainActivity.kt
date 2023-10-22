package com.cashix.kotlin.UI

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cashix.R
import com.cashix.database.DatabaseProvider
import com.cashix.kotlin.UI.SignUi.SendOTP.SendFragment
import com.cashix.utils.SnakeBar
import com.cashix.utils.change
import com.cashix.utils.changeHelper
import dagger.hilt.android.AndroidEntryPoint
import net.one97.paytm.nativesdk.PaytmSDK
import net.one97.paytm.nativesdk.dataSource.PaytmPaymentsUtilRepository


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
                .replace(R.id.mainLayout, com.cashix.kotlin.UI.Home.HomePage.HomeFragment())
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .commit()
        } else {
            change.go(SendFragment::class.java);
        }
//        try {
////            Q025010065@ybl
//            val uri: Uri =
//                Uri.parse("upi://pay?pa=paytmqr1iuefqme1i@paytm&pn=Cashix&tn=cashix payment&am=1&cu=inr")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            intent.setPackage("net.one97.paytm") // Replace with the actual package name of the UPI app you want to open
//            startActivityForResult(intent, 100)
//        } catch (e: ActivityNotFoundException) {
//            // Handle the case where the specified UPI app is not installed
//        }
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