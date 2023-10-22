package com.cashix.kotlin.UI.onBoarding.shared;

import java.util.List;

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
