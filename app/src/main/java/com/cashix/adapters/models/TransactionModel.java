package com.cashix.adapters.models;

public class TransactionModel {
    String _id , transactionId , status , amount , type , authkey , __v;

    public TransactionModel() {
    }

    public TransactionModel(String _id, String transactionId, String status, String amount, String type, String authkey, String __v) {
        this._id = _id;
        this.transactionId = transactionId;
        this.status = status;
        this.amount = amount;
        this.type = type;
        this.authkey = authkey;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
