package com.example.smartguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DataBaseHelper";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "name.db", null, 21);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists User (id integer primary key autoincrement, email text, password text)");
        db.execSQL("create table if not exists Reviews (id integer primary key autoincrement, email text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        onCreate(db);
    }


    public void registerUser(Data data){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("email",data.getEmail());
        contentValues.put("password", data.getPassword());
        long user = sqLiteDatabase.insert("User", null, contentValues);
        if (user != -1){
            Log.e(TAG ,"user Register Successful");
            
        }else{
            Log.e(TAG,"registerUser: error");
        }
    }

    public Boolean loginUser(Data data){
        Boolean check = false;
        SQLiteDatabase sqliteDatabase = this.getReadableDatabase();
        Cursor cursor = sqliteDatabase.rawQuery("select * from User", null, null);
        while (cursor.moveToNext()){
            String email = cursor.getString(1);
            String password = cursor.getString(2);

            if (email.equals(data.getEmail()) && password.equals(data.getPassword())){
                Log.e(TAG,"login successful");
                check = true;
            }
            else{
                Log.e(TAG,"login failed");
            }
        }
        return check;
    }


}