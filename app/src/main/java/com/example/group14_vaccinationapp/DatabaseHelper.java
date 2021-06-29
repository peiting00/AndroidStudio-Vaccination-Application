package com.example.group14_vaccinationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "vaccination.db";

    public DatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE user (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, IC TEXT, age INTEGER, phone TEXT, address TEXT, notes TEXT, vaccinePrefer TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        //db.execSQL("DROP TABLE IF EXISTS user");
    }

    public Boolean insert (String name, String IC, int age, String phone, String address, String notes, String vaccinePrefer){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues conVal = new ContentValues();
        conVal.put("name", name);
        conVal.put("IC", IC);
        conVal.put("age", age);
        conVal.put("phone", phone);
        conVal.put("address", address);
        conVal.put("notes", notes);
        conVal.put("vaccinePrefer", vaccinePrefer);

        long result = db.insert("user", null, conVal);

        return (result != -1);
    }

    public Cursor readInfo(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        if(cursor.getCount() != 0)
            cursor.moveToFirst();
//query return as Cursor pointer
        return cursor;
    }

    public Boolean isBlank(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        return (cursor.getCount() < 1);
    }
}
