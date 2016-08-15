package com.viator42.erikanote.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/2.
 */
public class EDbHelper extends SQLiteOpenHelper {
    public EDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public EDbHelper(Context context, String name)
    {
        this(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        db.execSQL("create table if not exists user("
                + "id integer primary key autoincrement,"
                + "username varchar(64),"
                + "password varchar(128),"
                + "imie varchar(128),"
                + "balance double,"
                + "total_income double,"
                + "total_spend double,"
                + "register_time integer,"
                + "last_login_time integer)");
            */
        db.execSQL("create table if not exists income_spend("
                + "id integer primary key autoincrement,"
                + "create_time integer,"
                + "name varchar(45),"
                + "comment varchar(128),"
                + "money double,"
                + "income_spend integer,"
                + "type integer)");

        db.execSQL("create table if not exists schedule("
                + "id integer primary key autoincrement,"
                + "type integer,"
                + "money double,"
                + "name varchar(45),"
                + "comment varchar(128),"
                + "create_time integer,"
                + "income_spend integer,"
                + "feq integer,"
                + "feq_value integer,"
                + "alarm_time integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
