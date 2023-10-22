package com.cashix.database.bank;

import static com.cashix.database.bank.bankDataModel.ACCOUNTNUMBER;
import static com.cashix.database.bank.bankDataModel.BANKNAME;
import static com.cashix.database.bank.bankDataModel.COLUMN_ID;
import static com.cashix.database.bank.bankDataModel.CREATE_TABLE;
import static com.cashix.database.bank.bankDataModel.HOLDERNAME;
import static com.cashix.database.bank.bankDataModel.IFSC;
import static com.cashix.database.bank.bankDataModel.TABLE_NAME;
import static com.cashix.database.bank.bankDataModel.USER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cashix.database.user.UserDBModel;

public class BankDataHelper extends SQLiteOpenHelper {

    public BankDataHelper(@Nullable Context context) {
        super(context, UserDBModel.DbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public long insertNewAccount(bankDataModel ac) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(HOLDERNAME, ac.holdername);
            values.put(ACCOUNTNUMBER, ac.accountnumber);
            values.put(IFSC, ac.bankifsc);
            values.put(BANKNAME, ac.bankname);
            values.put(USER, ac.user);
            values.put(COLUMN_ID, 1);
            long id = db.insert(TABLE_NAME, null, values);
            db.close();
            return id;
        } catch (Exception e) {
            return -1;
        }
    }

    public bankDataModel getBank(String user) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(
                    TABLE_NAME,
                    new String[]{
                            HOLDERNAME, ACCOUNTNUMBER, IFSC, BANKNAME, USER, COLUMN_ID},
                    USER + "=?",
                    new String[]{user}, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            bankDataModel model = new bankDataModel(
                    cursor.getString(cursor.getColumnIndex(HOLDERNAME)),
                    cursor.getString(cursor.getColumnIndex(BANKNAME)),
                    cursor.getString(cursor.getColumnIndex(IFSC)),
                    cursor.getString(cursor.getColumnIndex(ACCOUNTNUMBER)),
                    cursor.getString(cursor.getColumnIndex(USER))
            );

            cursor.close();
            return model;
        } catch (Exception e) {
            return null;
        }
    }
}
