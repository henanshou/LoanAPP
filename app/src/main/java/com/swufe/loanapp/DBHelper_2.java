package com.swufe.loanapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_2 extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_test2.db";
    public static final String TB_NAME_2 = "userpurchase";

    public DBHelper_2(Context context){
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TB_NAME_2 +"(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT, purchase TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+ TB_NAME);
//        onCreate(db);
    }
}
