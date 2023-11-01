package com.cashix.database.user;

import android.content.Context;

public class UserInfo {
    private static UserDatabase db;
    private static UserDBModel model;
    public UserInfo(Context context){
        db = new UserDatabase(context);
        model = db.getUser(1);
    }
    public UserDBModel getModel() {
        return model;
    }
    public UserDatabase getDB() {
        return db;
    }
}
