package com.cashix.kotlin.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cashix.R
import com.cashix.utils.SnakeBar
import com.cashix.utils.common
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class SplashV2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            val deepLinkUri: Uri? = intent.data
            if (deepLinkUri?.path.equals("/src/html/about.html")) {
                common.Open(this@SplashV2, "")
            }
        } else {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                }
            }, 3000)
        }
    }
}