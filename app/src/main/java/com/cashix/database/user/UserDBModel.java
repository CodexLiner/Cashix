package com.cashix.database.user;

public class UserDBModel {
    public static final String TABLE_NAME = "USER";
    public static final String COLUMN_ID = "id";
    public static final String AUTHKEY = "token";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    public static final String ZIP = "pincode";
    private int id = 1;
    private String name, email, mobile, pincode , authKey;
    public static String DbName = "DATABASE_LOCAL@CASHIX";

    public static final String
            CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER,"
                    + NAME + " TEXT,"
                    + ZIP + " TEXT,"
                    + MOBILE + " TEXT,"
                    + EMAIL + " TEXT DEFAULT " + "'guest@cashix.in',"
                    + AUTHKEY + " TEXT DEFAULT guest"
                    + ")";

    public UserDBModel(String name, String email, String mobile, String pincode, String authKey) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.pincode = pincode;
        this.authKey = authKey;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPincode() {
        return pincode;
    }

    public String getAuthKey() {
        return authKey;
    }

    public static String getDbName() {
        return DbName;
    }

    @Override
    public String toString() {
        return "UserDBModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", pincode='" + pincode + '\'' +
                ", authKey='" + authKey + '\'' +
                '}';
    }
}
