package com.example.DataBaseForReviews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelperLahore extends SQLiteOpenHelper {
    public DBhelperLahore(Context context) {
        super(context,"CustomerData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table CustomerDetails(name Text primary key,email Text, phone Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists CustomerDetails");
    }

    public boolean InsertCustomerDetails(String Name,String Email, String Phone)
    {
        SQLiteDatabase Db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",Name);
        contentValues.put("email",Email);
        contentValues.put("phone",Phone);

        long result=Db.insert("CustomerDetails",null, contentValues);
        if(result==-1)
        {
            return false;
        }
        else{
            return true;
        }
    }
    public boolean UpdateCustomerDetails(String Name,String Email, String Phone)
    {
        SQLiteDatabase Db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email",Email);
        contentValues.put("phone",Phone);
        Cursor cursor = Db.rawQuery("Select * from CustomerDetails where name=?", new String[]{Name});

        if (cursor.getCount()>0)
        {

            long result=Db.update("CustomerDetails",contentValues, "name=?",new String[]{Name});
            if(result==-1)
            {
                return false;
            }
            else{
                return true;
            }
        }else{ return false; }
    }

    public boolean DeleteCustomerDetails(String Name)
    {
        SQLiteDatabase Db = this.getWritableDatabase();
        Cursor cursor = Db.rawQuery("Select * from CustomerDetails where name=?", new String[]{Name});

        if (cursor.getCount()>0)
        {

            long result=Db.delete("CustomerDetails", "name=?",new String[]{Name});
            if(result==-1)
            {
                return false;
            }
            else{
                return true;
            }
        }else{ return false; }
    }
    //
    public Cursor GetCustomerDetails(String city)
    {

        //SELECT * FROM customers WHERE favorite_website = 'techonthenet.com'
        SQLiteDatabase Db = this.getWritableDatabase();
        Cursor cursor = Db.rawQuery("Select * from CustomerDetails where phone=?", new String[]{city});
        return cursor;

    }
    //


}