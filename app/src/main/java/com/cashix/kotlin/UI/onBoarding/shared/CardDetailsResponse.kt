package com.cashix.kotlin.UI.onBoarding.shared

data class CardDetailsResponse(
    val cardholder: String,
    val cardbank: String,
    val cardtype: String,
    val cardnumber: String,
    var isSelected: Boolean,
)
