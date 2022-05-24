package com.cashix.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cashix.UI.BankDetails;

public class BankDataHelper extends SQLiteOpenHelper {

    public BankDataHelper(@Nullable Context context) {
        super(context, "BANKDATA" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + "BankDetails" + "("
                        + "COLUMN_ID" + " INTEGER,"
                        + "ACCOUNT" + " TEXT,"
                        + "NAME" + " TEXT,"
                        + "IFSC" + " TEXT"
                        + ")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "BankDetails");
    }
    public long insertNewAccount(String number , String ifsc , String ac){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("IFSC", ifsc);
            values.put("NAME", ac);
            values.put("ACCOUNT", number);
            values.put("COLUMN_ID", 1);
            long id = db.insert("BankDetails", null, values);
            // close db connection
            db.close();
            // return newly inserted row id
            return id;

        }catch (Exception e){return -1;}
    }
    public bankDataModel getBank(){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(
                  "BankDetails",
                    new String[]{
                        "COLUMN_ID" , "IFSC"  , "NAME" , "ACCOUNT" , "COLUMN_ID" } ,
                    "COLUMN_ID"  +"=?" ,
                    new String[]{"1"} , null , null ,null , null);
            if (cursor!=null){
                cursor.moveToFirst();
            }
            bankDataModel model = new bankDataModel(
               cursor.getInt(cursor.getColumnIndex("COLUMN_ID")),
               cursor.getString(cursor.getColumnIndex("ACCOUNT") ),
               cursor.getString(cursor.getColumnIndex("NAME") ),
               cursor.getString(cursor.getColumnIndex("IFSC")) );

                            cursor.close();
                            return model;
        }catch (Exception e){
            return null;
        }
    }
}
