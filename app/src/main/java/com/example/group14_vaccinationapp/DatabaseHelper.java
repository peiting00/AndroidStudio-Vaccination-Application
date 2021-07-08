package com.example.group14_vaccinationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vaccination.db";
    //user table
    private static final String TABLE_USER = "user";
    private static final String TABLE_VACCINE = "vaccine";
    private static final String COLUMN_IC = "ic";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_NOTES = "notes";
    private static final String COLUMN_VACCINE_STATUS = "status";
    private static final String COLUMN_isADMIN = "isAdmin";
    //vaccine table
    private static final String COLUMN_VACCINE_ID = "vaccineID";
    private static final String COLUMN_VACCINE_NAME = "vaccineName";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_IC + " TEXT PRIMARY KEY , " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_AGE + " TEXT NOT NULL, " +
                COLUMN_PHONE + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL, " +
                COLUMN_NOTES + " TEXT NOT NULL, " +
                COLUMN_VACCINE_STATUS + " TEXT NOT NULL, " +
                COLUMN_isADMIN + " TEXT NOT NULL, " +
                COLUMN_VACCINE_ID + " TEXT NOT NULL," +
                "CONSTRAINT fk_vaccine FOREIGN KEY (" + COLUMN_VACCINE_ID + ") " +
                "REFERENCES " + TABLE_VACCINE + "(" + COLUMN_VACCINE_ID + "));");

        db.execSQL("CREATE TABLE " + TABLE_VACCINE + " (" +
                COLUMN_VACCINE_ID + " TEXT PRIMARY KEY , " +
                COLUMN_VACCINE_NAME + " TEXT NOT NULL );");

        db.execSQL("INSERT INTO " + TABLE_VACCINE + "(" +
                COLUMN_VACCINE_ID + "," + COLUMN_VACCINE_NAME + ")" +
                "VALUES('1','PFIZER'),('2','SINOVAC'),('3','AZ');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINE + ";");
        onCreate(db);
    }

    public Boolean addUser(String IC, String name, String password, String age, String phone, String address, String notes, String vaccineID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues conVal = new ContentValues();
        conVal.put(COLUMN_IC, IC); //0
        conVal.put(COLUMN_NAME, name);//1
        conVal.put(COLUMN_PASSWORD, password);//2
        conVal.put(COLUMN_AGE, age);//3
        conVal.put(COLUMN_PHONE, phone);//4
        conVal.put(COLUMN_ADDRESS, address);//5
        conVal.put(COLUMN_VACCINE_STATUS, "Pending");//default value is pending//6
        conVal.put(COLUMN_NOTES, notes);//7
        conVal.put(COLUMN_isADMIN, "0"); //default user as not admin//8
        conVal.put(COLUMN_VACCINE_ID, vaccineID); // before come to here need to speficy the id//9

        return db.insert(TABLE_USER, null, conVal) != -1;
    }

    public boolean isIC_Exist(String ic) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_IC + "=?", new String[]{ic});
        if (cursor.getCount() > 0)
            return true;//IC exist
        else
            return false;
    }

    // Registed user info
    public Cursor readInfo(String ic) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_USER +" WHERE "+COLUMN_IC+"=?",new String[]{ic} );
    }

    public boolean isBlank() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        return (cursor.getCount() <1);
    }

    public boolean deleteUser(String ic) {
        //delete user by using NRIC (primary key)
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_USER, COLUMN_IC + "=?", new String[]{ic}) == 1;
    }

    public Cursor getAllUser() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER, null);
    }

    public boolean updateUser(String IC, String name, String age, String phone, String address, String status, String notes, String vaccineID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues conVal = new ContentValues();
        conVal.put(COLUMN_IC, IC); //0
        conVal.put(COLUMN_NAME, name);//1
        conVal.put(COLUMN_AGE, age);//3
        conVal.put(COLUMN_PHONE, phone);//4
        conVal.put(COLUMN_ADDRESS, address);//5
        conVal.put(COLUMN_VACCINE_STATUS, status);//default value is pending//6
        conVal.put(COLUMN_NOTES, notes);//7
        conVal.put(COLUMN_VACCINE_ID, vaccineID); // before come to here need to speficy the id//9

        return db.update(TABLE_USER, conVal, COLUMN_IC + "=?", new String[]{String.valueOf(IC)}) == 1;
    }

    public Cursor searchUserBy(String clause) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_IC + " LIKE '%"+clause+"%'",null);
    }

//    public Cursor filterUserBy(String IC){
//        SQLiteDatabase db=getReadableDatabase();
//        return db.rawQuery("SELECT * FROM "+TABLE_USER+" ORDER BY "+COLUMN_IC
//    }

}
