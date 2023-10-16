package com.cashix.kotlin.UI.onBoarding.shared;

public class AddBankRequest {
    String holdername , bankname , bankifsc , accountnumber;

    public AddBankRequest(String holdername, String bankname, String bankifsc, String accountnumber) {
        this.holdername = holdername;
        this.bankname = bankname;
        this.bankifsc = bankifsc;
        this.accountnumber = accountnumber;
    }
}
