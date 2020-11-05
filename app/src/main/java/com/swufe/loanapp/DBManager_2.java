package com.swufe.loanapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBManager_2 {
    private DBHelper_2 dbHelper_2;
    private String TBNAME_2;

    public DBManager_2(Context context) {
        dbHelper_2 = new DBHelper_2(context);
        TBNAME_2 = DBHelper_2.TB_NAME_2;//DBHelper_2中已经定义的表名
    }

    public void add(String name,String password) {
        SQLiteDatabase db = dbHelper_2.getWritableDatabase();

        ContentValues values = new ContentValues();//用于操纵数据库
        values.put("username", name);
        values.put("purchase", password);

        db.insert(TBNAME_2, null, values);
        db.close();
    }

    public void delete(String name) {
        SQLiteDatabase db = dbHelper_2.getWritableDatabase();
        db.delete(TBNAME_2, "username=?", new String[]{name});
        db.close();
    }

    //    查询所有数据
    public ArrayList<Purchase> getAllData_2() {
        ArrayList<Purchase> list = null;
        SQLiteDatabase db = dbHelper_2.getReadableDatabase();
        Cursor cursor = db.query(TBNAME_2, null, null, null, null, null, "username DESC");
        if (cursor != null) {
            list = new ArrayList<Purchase>();
            while (cursor.moveToNext()) {
                Purchase purchase = new Purchase();
                purchase.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                purchase.setPurchase(cursor.getString(cursor.getColumnIndex("purchase")));

                list.add(purchase);
            }
            cursor.close();
        }
        db.close();
        return list;
    }
}
