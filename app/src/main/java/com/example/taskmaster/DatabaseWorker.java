package com.example.taskmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseWorker extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="accounts.db";
    public static final String TABLE_NAME="register_user";
    public static final String COL_1 = "id";
    public static final String COL_2 = "email";
    public static final String COL_3 = "password";

    public DatabaseWorker(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);

        long res = db.insert("register_user", null, contentValues);

        db.close();

        return res;
    }

    public boolean checkUser(String email, String password){
        String [] columns = { COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String [] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        int count = ((Cursor) cursor).getCount();

        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }
}
