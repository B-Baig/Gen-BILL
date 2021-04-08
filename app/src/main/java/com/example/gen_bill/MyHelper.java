package com.example.gen_bill;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(@Nullable Context context) {
        super(context, "MyDatabase", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "create Table myTable(invoiceNo INTEGER PRIMARY KEY AUTOINCREMENT,customerName TEXT,"+"contactNo TEXT,date INTEGER,item1 TEXT,qty1 INTEGER,amount1 INTEGER,item2 TEXT,qty2 INTEGER,amount2 INTEGER);";
        sqLiteDatabase.execSQL(createTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, int i, int i1) {

    }

    public void insert(String customerName,String contactNo,Long date,String item1,int qty1,int amount1,String item2,int qty2,int amount2)
    {


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("CustomerName",customerName);
        contentValues.put("ContactNo",contactNo);
        contentValues.put("date",date);
        contentValues.put("item1",item1);
        contentValues.put("qty1",qty1);
        contentValues.put("amount1",amount1);
        contentValues.put("item2",item2);
        contentValues.put("qty2",qty2);
        contentValues.put("amount2",amount2);

        sqLiteDatabase.insert("myTable",null,contentValues);

    }
}
