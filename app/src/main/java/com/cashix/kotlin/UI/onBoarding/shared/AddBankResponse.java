package com.cashix.kotlin.UI.onBoarding.shared;

import java.util.List;

public class AddBankResponse {
    String status ;
    List<accounts> accounts;

    public AddBankResponse() {
    }

    public AddBankResponse(String status, List<accounts> accounts) {
        this.status = status;
        this.accounts = accounts;
    }

    public String getStatus() {
        return status;
    }

    public List<accounts> getAccounts() {
        return accounts;
    }
}
