package com.cashix.kotlin.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cashix.R
import com.cashix.database.DatabaseProvider
import com.cashix.kotlin.UI.SignUi.SendOTP.SendFragment
import com.cashix.kotlin.UI.cardtoBank.CardToBankFragment
import com.cashix.kotlin.UI.shared.AuthInterceptor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var databaseProvider: DatabaseProvider

    @Inject
    lateinit var authInterceptor: AuthInterceptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_v2)
        databaseProvider = DatabaseProvider(this)

        if (databaseProvider.getUserDB().getUser(1) != null) {
            authInterceptor.updateToken(databaseProvider.getUserDB().getUser(1).authKey)
            go(CardToBankFragment()::class.java)
//            go(com.cashix.kotlin.UI.Home.HomePage.HomeFragment()::class.java)
        } else {
            go(SendFragment::class.java);
        }
//        try {
//            Q025010065@ybl
//            val uri: Uri =
//                Uri.parse("upi://pay?pa=paytmqr1iuefqme1i@paytm&pn=Cashix&tn=cashix payment&am=1&cu=inr")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            intent.setPackage("net.one97.paytm") // Replace with the actual package name of the UPI app you want to open
//            startActivityForResult(intent, 100)
//        } catch (e: ActivityNotFoundException) {
//            // Handle the case where the specified UPI app is not installed
//        }
    }

    private fun go(fragment: Class<out Fragment?>) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true).setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.mainLayout, fragment, null).commit()
    }

    var doubleBackToExitPressedOnce = false
//    override fun onBackPressed() {
//        val fn = supportFragmentManager
//        if (fn.backStackEntryCount == 1) {
//            if (doubleBackToExitPressedOnce) {
//                finishAffinity()
//                super.onBackPressed()
//                return
//            }
//            SnakeBar(this@MainActivity).showSnackbar("Press Back Again to Exit")
//            Handler(Looper.getMainLooper()).postDelayed(
//                { doubleBackToExitPressedOnce = false }, 2000
//            )
//        } else {
//            super.onBackPressed()
//        }
//    }
}