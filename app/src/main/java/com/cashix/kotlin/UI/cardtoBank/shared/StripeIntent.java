package com.cashix.kotlin.UI.cardtoBank.shared;

public class StripeIntent {
    String amount, transactionid , status , clientSecret;

    public StripeIntent(String amount, String transaction_id, String status, String clientSecret) {
        this.amount = amount;
        this.transactionid = transaction_id;
        this.status = status;
        this.clientSecret = clientSecret;
    }

    public StripeIntent(String amount, String transaction_id) {
        this.amount = amount;
        this.transactionid = transaction_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransaction_id() {
        return transactionid;
    }

    public String getStatus() {
        return status;
    }

    public String getClientSecret() {
        return clientSecret;
    }

}
