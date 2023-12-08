package com.cashix.kotlin.UI.onBoarding.shared;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddBankResponse {
    String status ;
    accounts accounts;

    public AddBankResponse() {
    }

    public String getStatus() {
        return status;
    }

    public com.cashix.kotlin.UI.onBoarding.shared.accounts getAccounts() {
        return accounts;
    }
}
