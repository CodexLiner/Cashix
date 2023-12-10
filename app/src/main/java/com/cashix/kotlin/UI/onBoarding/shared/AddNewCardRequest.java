package com.cashix.kotlin.UI.onBoarding.shared;

public class AddNewCardRequest {
    String cardholdername, cardnumber, cardExpMonth , cardExpYear , cardcvv;

    public AddNewCardRequest(String cardholdername, String cardnumber, String cardExpMonth, String cardExpYear, String cardcvv) {
        this.cardholdername = cardholdername;
        this.cardnumber = cardnumber;
        this.cardExpMonth = cardExpMonth;
        this.cardExpYear = cardExpYear;
        this.cardcvv = cardcvv;
    }
}