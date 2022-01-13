package com.crepaid.database;

public class userDatabaseModel {
    public static final String TABLE_NAME = "CREPAID_USERS";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "mobile";
    public static final String AUTHKEY = "token";

    private int id = 1;
    private String mobile;
    private String auth;

    public userDatabaseModel(int id, String mobile, String auth) {
        this.id = 1;
        this.mobile = mobile;
        this.auth = auth;
    }

    public userDatabaseModel(String mobile, String auth) {
        this.mobile = mobile;
        this.auth = auth;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public static String DbName = "CREPAID_USER_DATA";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER,"
                    + COLUMN_NOTE + " TEXT,"
                    + AUTHKEY + " TEXT"
                    + ")";

    @Override
    public String toString() {
        return "userDatabaseModel{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }
}
