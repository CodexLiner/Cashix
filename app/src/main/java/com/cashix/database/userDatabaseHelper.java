package com.cashix.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class userDatabaseHelper extends SQLiteOpenHelper {
    public userDatabaseHelper(Context context) {
        super(context, userDatabaseModel.DbName , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(userDatabaseModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + userDatabaseModel.TABLE_NAME);
    }
    public long insertNote(String mobile , String token , int ids) {
        // get writable database as we want to write data
        userDatabaseModel c = getNote(ids);
        if (c!= null && c.getId()==ids){
            delete(ids);
           Log.d("TAG", "insertNote:fg");
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userDatabaseModel.COLUMN_NOTE, mobile);
        values.put(userDatabaseModel.AUTHKEY, token);
        values.put(userDatabaseModel.COLUMN_ID, ids);
        // insert row
        long id = db.insert(userDatabaseModel.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }
    public void delete(int id)
    {
        String[] args={String.valueOf(id)};
        int x  =getWritableDatabase().delete(userDatabaseModel.TABLE_NAME, "id=?", args);
    }
    public userDatabaseModel getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

      try{
          Cursor cursor = db.query(userDatabaseModel.TABLE_NAME,
                  new String[]{userDatabaseModel.COLUMN_ID, userDatabaseModel.COLUMN_NOTE,userDatabaseModel.DATE , userDatabaseModel.EMAIL, userDatabaseModel.AUTHKEY},
                  userDatabaseModel.COLUMN_ID + "=?",
                  new String[]{String.valueOf(id)}, null, null, null, null);

          if (cursor != null)
              cursor.moveToFirst();

          // prepare note object
          userDatabaseModel note = new userDatabaseModel(
                  cursor.getInt(cursor.getColumnIndex(userDatabaseModel.COLUMN_ID)),
                  cursor.getString(cursor.getColumnIndex(userDatabaseModel.COLUMN_NOTE)),
                  cursor.getString(cursor.getColumnIndex(userDatabaseModel.EMAIL)),
                  cursor.getString(cursor.getColumnIndex(userDatabaseModel.DATE)),
                  cursor.getString(cursor.getColumnIndex(userDatabaseModel.AUTHKEY)));

          // close the db connection
          cursor.close();
          return note;
      }catch (Exception e){

      }

        return null;
    }
}
