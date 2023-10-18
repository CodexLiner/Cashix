package com.cashix.kotlin.UI.onBoarding.shared;

import java.util.List;

class accounts{
    String holdername , bankname , bankifsc , accountnumber;

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
public class AddBankResponse {
    String status ;
    List<accounts> accounts;

    public AddBankResponse() {
    }

    public AddBankResponse(String status, List<com.cashix.kotlin.UI.onBoarding.shared.accounts> accounts) {
        this.status = status;
        this.accounts = accounts;
    }

    public String getStatus() {
        return status;
    }

    public List<com.cashix.kotlin.UI.onBoarding.shared.accounts> getAccounts() {
        return accounts;
    }
}
