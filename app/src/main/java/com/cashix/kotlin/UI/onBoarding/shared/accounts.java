package com.cashix.kotlin.UI.onBoarding.shared;

public class accounts {
    String holdername, bankname, bankifsc, accountnumber;

    public accounts(String holdername, String bankname, String bankifsc, String accountnumber) {
        this.holdername = holdername;
        this.bankname = bankname;
        this.bankifsc = bankifsc;
        this.accountnumber = accountnumber;
    }

    public String getHoldername() {
        return holdername;
    }

    public String getBankname() {
        return bankname;
    }

    public String getBankifsc() {
        return bankifsc;
    }

    public String getAccountnumber() {
        return accountnumber;
    }
}
