package com.cashix.kotlin

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import net.one97.paytm.nativesdk.PaytmSDK

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate()
        PaytmSDK.init(this)
    }
}