package com.cashix.database.user;

public class userDatabaseModel {
    public static final String TABLE_NAME = "CREPAID_USERS";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "mobile";
    public static final String AUTHKEY = "token";
    public static final String DATE = "date";
    public static final String EMAIL = "email";

    private int id = 1;
    private String mobile;
    private String auth , email , date ;

    public userDatabaseModel(int id, String mobile, String auth, String email, String date) {
        this.id = id;
        this.mobile = mobile;
        this.auth = auth;
        this.email = email;
        this.date = date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static String DbName = "CREPAID_USER_DATA";
    public static final String
            CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER,"
                    + COLUMN_NOTE + " TEXT,"
                    + DATE + " TEXT DEFAULT "+"'01/01/1900',"
                    + EMAIL + " TEXT DEFAULT "+"'mail@cashix.in',"
                    + AUTHKEY + " TEXT DEFAULT Guest"
                    + ")";

    @Override
    public String toString() {
        return "userDatabaseModel{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", auth='" + auth + '\'' +
                ", email='" + email + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
