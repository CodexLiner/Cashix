package com.cashix.database.user;

import android.content.Context;

public class UserInfo {
    private static userDatabaseHelper db;
    private static UserDBModel model;
    public UserInfo(Context context){
        db = new userDatabaseHelper(context);
        model = db.getUser(1);
    }
    public UserDBModel getModel() {
        return model;
    }
    public userDatabaseHelper getDB() {
        return db;
    }
}
