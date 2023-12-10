package com.cashix.database.bank;

public class bankDataModel {
    public static final String TABLE_NAME = "bankdetails";
    public static final String COLUMN_ID = "id";
    public static final String HOLDERNAME = "holdername";
    public static final String BANKNAME = "bankname";
    public static final String IFSC = "ifsc";
    public static final String ACCOUNTNUMBER = "accountnumber";
    public static final String USER = "user";

    public static String DbName = "DATABASE_LOCAL@CASHIX_1";



    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER,"
            + HOLDERNAME + " TEXT,"
            + BANKNAME + " TEXT,"
            + IFSC + " TEXT,"
            + ACCOUNTNUMBER + " TEXT DEFAULT '',"
            + USER + " TEXT DEFAULT guest"
            + ")";

    String holdername, bankname, bankifsc, accountnumber, user , id;

    public bankDataModel(String holdername, String bankname, String bankifsc, String accountnumber, String user, String id) {
        this.holdername = holdername;
        this.bankname = bankname;
        this.bankifsc = bankifsc;
        this.accountnumber = accountnumber;
        this.user = user;
        this.id = id;
    }

    public String getId() {
        return id;
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

    public String getUser() {
        return user;
    }
}
