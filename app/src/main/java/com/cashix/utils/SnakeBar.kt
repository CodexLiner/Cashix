package com.cashix.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.cashix.R
import com.google.android.material.snackbar.Snackbar


class SnakeBar(private val activity: Activity?) {
    fun showSnackbar(message: String?) {
        if (activity != null) {
            val view = activity.findViewById<View>(android.R.id.content)
            val snackbar = Snackbar.make(view, "0", Snackbar.LENGTH_SHORT)
            val customSnackView =
                activity.layoutInflater.inflate(com.cashix.R.layout.snackbar_2, null)
            snackbar.view.setBackgroundColor(Color.TRANSPARENT)
            val messageTextView: TextView = customSnackView.findViewById(R.id.message_snake)
            messageTextView.text = message

            val snackLayout = snackbar.view as Snackbar.SnackbarLayout
            snackLayout.addView(customSnackView)
            snackbar.show()

            if (view != null) {
                snackbar.show()
            }
        }
    }
}
