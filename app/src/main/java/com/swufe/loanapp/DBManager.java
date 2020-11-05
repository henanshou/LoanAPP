package com.swufe.loanapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;//DBHelper中已经定义的表名
    }

    public void add(String name,String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();//用于操纵数据库
        values.put("name", name);
        values.put("password", password);

        db.insert(TBNAME, null, values);
        db.close();
    }

    public void delete(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "name=?", new String[]{name});
        db.close();
    }

    //    查询所有数据
    public ArrayList<User> getAllData() {
        ArrayList<User> list = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, "name DESC");
        if (cursor != null) {
            list = new ArrayList<User>();
            while (cursor.moveToNext()) {
                User user = new User();
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));

                list.add(user);
            }
            cursor.close();
        }
        db.close();
        return list;
    }
}
