package com.cashix.constants;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class STATIC {
    // user info
    public static final String AuthKey = "authkey";
    public static final String UserName = "UserName";
    public static final String UserNumber = "UserNumber";
    public static final String UserBank = "UserBank";
    public static final String UserKycId = "UserKycId";
    public static final String UserKycIdType = "UserKycIdType";
    public static final String UserAccount = "UserAccount";
    public static final String UserIfsc = "UserIfsc";
    public static final String Amount = "amount";
    public static final String TransactionID = "transactionId";
    public static final String TransactionType = "type";
    public static final String TransactionStatus = "status";
    public static final String DESC = "desc";

    public static final String STRIPEKEY = "";
    public static final String baseBackend = "http://192.168.29.144:5000/";
    public static final String baseUrlstripe= "";
    public static final String mediaType = "application/json; charset=utf-8";
    public static String TextLocalKey = "NDU3YTczMzk0YjU4NzczMTM5MzI0NTY4Nzc0YzYzNzA=";
    public static String sendTotpMessage = " is your CrePaid verification code. please DO NOT SHARE this with anyone - Thank You CrePaid";
    public static void makeToast(Context context , String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void makeSnack(Context context, ViewGroup viewGroup , String msg){
        Snackbar.make(context ,viewGroup, msg,  Snackbar.LENGTH_LONG).show();

    }

}
