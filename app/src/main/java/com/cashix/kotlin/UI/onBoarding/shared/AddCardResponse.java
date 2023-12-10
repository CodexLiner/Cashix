package com.cashix.kotlin.UI.onBoarding.shared;

public class AddCardResponse {
    String cardholdername, cardnumber, expirationdate , cvv , bankname , cardType , status , message ;

    public AddCardResponse() {
    }

    public AddCardResponse(String cardholdername, String cardnumber, String expirationdate, String cvv, String bankname, String cardType, String status, String message) {
        this.cardholdername = cardholdername;
        this.cardnumber = cardnumber;
        this.expirationdate = expirationdate;
        this.cvv = cvv;
        this.bankname = bankname;
        this.cardType = cardType;
        this.status = status;
        this.message = message;
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

    public String getCvv() {
        return cvv;
    }

    public String getBankname() {
        return bankname;
    }

    public String getCardType() {
        return cardType;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
