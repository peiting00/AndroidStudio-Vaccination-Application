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

    //Constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table(s) for the database
        createTable(db);
        //insert default data(s) into the table(s)
        insertDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINE + ";");
        onCreate(db);
    }

    /**
     * Includes query to create table for the database
     *
     * @param db is the SQLiteDatabase sent from OnCreate
     */
    private void createTable(SQLiteDatabase db) {

        //COLUMN_IC is the primary key for TABLE_USER
        //constraint foreign key COLUMN_VACCINE_ID to the PRIMARY KEY OF TABLE_VACCINE
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

        //create SECOND table in the database
        db.execSQL("CREATE TABLE " + TABLE_VACCINE + " (" +
                COLUMN_VACCINE_ID + " TEXT PRIMARY KEY , " +
                COLUMN_VACCINE_NAME + " TEXT NOT NULL );");
    }

    /**
     * Includes query to insert default data into the database
     *
     * @param db is the SQLiteDatabase sent from OnCreate
     */
    private void insertDefaultData(SQLiteDatabase db) {

        //Default user data is inserted into database
        db.execSQL("INSERT INTO " + TABLE_USER + "(" + COLUMN_IC + "," + COLUMN_NAME + "," +
                COLUMN_PASSWORD + "," + COLUMN_AGE + "," + COLUMN_PHONE + "," +
                COLUMN_ADDRESS + "," + COLUMN_NOTES + "," + COLUMN_VACCINE_STATUS + "," +
                COLUMN_isADMIN + "," + COLUMN_VACCINE_ID + ") VALUES " +
                "('000427019999','CHEW PEI TING','000427019999','21','01294864903','5 Jln Pinggiran 1 Medan Damai Ukay Hulu Ampang,68000 Klang Selangor','N/A','PENDING','0','1')," +
                "('991105012980','LIM SIAO SIAO','991105012980','22','0198475735','Blok B 49 Rumah Panjang 1 Jln Batu 13  47100 Puchong Selangor','N/A','PENDING','0','1')," +
                "('670218145667','MOHD ALI BIN ABUDULLAH','670218145667','54','0164573656','No. 138 4Th Floor Jln Tun H S Lee, 50000 Kuala Lumpur Wilayah Persekutuan','N/A','FIRST DOSE:29TH AUGUST 2021','0','2')," +
                "('880911143556','JANICE HO YI XIAN','880911143556','33','01948758473',' Lorong Seri Teruntum 1, Medan Warisan 25100 Kuantan Pahang','N/A','2 DOSES OF VACCINATION COMPLETED','0','3')," +
                "('991023042345','PRICILLA TAN WEI XUAN','991023042345','22','0118347546','NO 52A-F BROOKE DRIVE SIBU 96000 Sibu Sarawak','N/A','PENDING','0','2')," +
                "('940217101011','TOH XIN YU','940217101011','27','0109948584','1 33 Jln 20/16 Seksyen 20 Petaling Jaya 46300 Petaling Jaya, Selangor','FIRST DOSE DONE','SECOND DOSE:30TH JULY 2021','0','2')," +
                "('881207100882','SAMUEL NG','881207100882','33','01298847684','62 Jalan 10/34A Kepong Enterpreneurs Park 52100 Kuala Lumpur, Wilayah Persekutuan','N/A','PENDING','0','1')," +
                "('770703019983','DWEE WEN XIN','770703019983','44','0127787342','Blok H 25 4 Jln Pju 1/37 Prima Square Petaling Jaya 46500 Petaling Jaya, Selangor','N/A','FIRST DOSE:27TH AUGUST 2021','0','3')," +
                "('780113012456','LONG LI SUAN','780113012456','43','0176475638','Jalan Dagang 1/1A, Taman Dagang, 68000 Ampang, Selangor','N/A','FIRST DOSE:29TH AUGUST 2021','0','1')," +
                "('admin01','TEOH BO WEI','admin01','21','0116835683','C-6-06 RITZE PERDANA JALAN PJU 8/2 47820 Petaling Jaya, Selangor','N/A','-','1','1')," +
                "('admin02','CHEW PEI TING','admin02','21','012576773','B5-6 Happy Mansion, Jalan 17/3, 46400 Petaling Jaya, Selangor','N/A','-','1','1')," +
                "('admin03','BOO TING','admin03','21','0109948573','2a, Jalan Damar Laut 2b Telok Gong 42000 Klang, Selangor','N/A','-','1','1')");

        //Default vaccine data is inserted into database
        db.execSQL("INSERT INTO " + TABLE_VACCINE + "(" +
                COLUMN_VACCINE_ID + "," + COLUMN_VACCINE_NAME + ")" +
                "VALUES('1','PFIZER'),('2','SINOVAC'),('3','AZ');");

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
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_isADMIN + "='0' AND " + COLUMN_IC + "=?", new String[]{ic});
        if (cursor.getCount() > 0)
            return true;//IC exist
        else
            return false;
    }

    // Registed user info
    public Cursor readInfo(String ic) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_IC + "=?", new String[]{ic});
    }

    public boolean isBlank() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        return (cursor.getCount() < 1);
    }

    public boolean deleteUser(String ic) {
        //delete user by using NRIC (primary key)
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_USER, COLUMN_IC + "=?", new String[]{ic}) == 1;
    }

    public Cursor getAllUser() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_isADMIN + " ='0'", null);
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
        return db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_isADMIN + " ='0' AND " + COLUMN_IC + " LIKE '%" + clause + "%'", null);
    }

//    public Cursor filterUserBy(String IC){
//        SQLiteDatabase db=getReadableDatabase();
//        return db.rawQuery("SELECT * FROM "+TABLE_USER+" ORDER BY "+COLUMN_IC
//    }

}
