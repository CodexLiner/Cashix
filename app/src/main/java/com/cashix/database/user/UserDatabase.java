package com.cashix.database.user;

import static com.cashix.database.user.UserDBModel.AUTHKEY;
import static com.cashix.database.user.UserDBModel.COLUMN_ID;
import static com.cashix.database.user.UserDBModel.EMAIL;
import static com.cashix.database.user.UserDBModel.MOBILE;
import static com.cashix.database.user.UserDBModel.NAME;
import static com.cashix.database.user.UserDBModel.ZIP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {
    public UserDatabase(Context context) {
        super(context, UserDBModel.DbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDBModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDBModel.TABLE_NAME);
    }

    public long setUser(UserDBModel user) {

        UserDBModel c = getUser(user.getId());
        if (c != null && c.getId() == user.getId()) {
            delete(user.getId());
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMAIL, user.getEmail());
        values.put(NAME, user.getName());
        values.put(ZIP, user.getPincode());
        values.put(MOBILE, user.getMobile());
        values.put(COLUMN_ID, user.getId());
        values.put(AUTHKEY, user.getAuthKey());

        long id = db.insert(UserDBModel.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public void delete(int id) {
        String[] args = {String.valueOf(id)};
        int x = getWritableDatabase().delete(UserDBModel.TABLE_NAME, "id=?", args);
    }

    public UserDBModel getUser(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(UserDBModel.TABLE_NAME,
                    new String[]{COLUMN_ID, EMAIL, MOBILE, NAME, ZIP , AUTHKEY},
                    UserDBModel.COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();

            UserDBModel user = new UserDBModel(
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getString(cursor.getColumnIndex(EMAIL)),
                    cursor.getString(cursor.getColumnIndex(MOBILE)),
                    cursor.getString(cursor.getColumnIndex(ZIP)),
                    cursor.getString(cursor.getColumnIndex(AUTHKEY)));

            cursor.close();
            return user;
        } catch (Exception ignored) {}
        return null;
    }
}
