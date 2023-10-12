package com.cashix.kotlin.UI.SignUi.shared

data class ValidateOTPResponse(val token: String, val status: String, val oldUser: Boolean)