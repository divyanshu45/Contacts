package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databasehelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myDB.db";
    public static final String TABLE_NAME = "contacts";
    public static final String ID = "ID";
    public static final String NAME = "Name";
    public static final String PHONE = "Phone";
    public static final String EMAIL = "Email";


    public Databasehelper(Context context) {
        super(context,DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PHONE TEXT, EMAIL TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String phone, String email){
        if(name.isEmpty() && phone.isEmpty() && email.isEmpty()){
            return false;
        }else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put(NAME, name);
            val.put(PHONE, phone);
            val.put(EMAIL, email);
            long res = db.insert(TABLE_NAME, null, val);
            if (res == -1) return false;
            else return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME , null);
        return res;
    }

    public boolean updateData(String id,String name, String phone, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NAME,name);
        val.put(PHONE,phone);
        val.put(EMAIL,email);
        int up = db.update(TABLE_NAME,val,"ID = ?",new String[]{ id });
        if(up != 0){
            return true;
        }else{
            return false;
        }
    }

    public int deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{ id });
    }
}
