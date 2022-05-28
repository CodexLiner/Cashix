package com.cashix.database.user;

import android.content.Context;

public class UserInfo {
    private static userDatabaseHelper db;
    private static userDatabaseModel model;
    public UserInfo(Context context){
        db = new userDatabaseHelper(context);
        model = db.getNote(1);
    }
    public userDatabaseModel getModel() {
        return model;
    }
    public userDatabaseHelper getDB() {
        return db;
    }
}
