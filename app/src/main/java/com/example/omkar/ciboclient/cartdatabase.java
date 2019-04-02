package com.example.omkar.ciboclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class cartdatabase extends SQLiteOpenHelper {

    public static final String DTABASE_NAME = "order.db";
    public static final String TABLE_NAME = "orders";
    public static final String COLUMN0 = "_id";
    public static final String COLUMN1 = "ITEM_NAME";
    public static final String COLUMN2 = "ITEM_QTY";
    public static final String COLUMN3 = "ITEM_PRICE";
    public static final int VERSION = 1;




    public cartdatabase(@Nullable Context context) {
        super(context,DTABASE_NAME , null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query=String.format("CREATE TABLE IF NOT EXISTS %s (%s STRING, %s STRING,%s INTEGER,%s STRING)",TABLE_NAME,COLUMN0,COLUMN1,COLUMN2,COLUMN3);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor showdata()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return c;
    }

    public void updateme(String name,int qty)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN2,qty);
        String[] args = {name};
        db.update(TABLE_NAME,cv,COLUMN1+" = ? ",args);
    }
    public void deleteitem(String name)
    {
        String[] args = {name};
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN1 +"=?",args);
    }
    public void additem(String id,String name,int qty,String price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN0,id);
        cv.put(COLUMN1,name);
        cv.put(COLUMN2,qty);
        cv.put(COLUMN3,price);
        db.insert(TABLE_NAME,null,cv);
    }
    public void deleteall()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }

    public Cursor showselected(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME + " WHERE "+COLUMN0+" = '"+id+"'",null);
        return c;
    }
    public Cursor showselectedonly()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME + " WHERE "+COLUMN2+" > 0",null);
        return c;
    }




}
