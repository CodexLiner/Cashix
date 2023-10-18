package com.cashix.kotlin.UI.onBoarding.shared;

public class AddNewCardRequest {
    String cardholdername, cardnumber, expirationdate;

    public AddNewCardRequest(String cardholdername, String cardnumber, String expirationdate) {
        this.cardholdername = cardholdername;
        this.cardnumber = cardnumber;
        this.expirationdate = expirationdate;
    }

    public String getCardholdername() {
        return cardholdername;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public String getExpirationdate() {
        return expirationdate;
    }
}