package com.cashix.kotlin.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cashix.R
import com.cashix.utils.common

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            val deepLinkUri: Uri? = intent.data
            if (deepLinkUri?.path.equals("/src/html/about.html")){
                common.Open(this@SignInActivity, "")
            }
        }
    }
}