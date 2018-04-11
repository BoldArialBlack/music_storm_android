package com.example.asus.music_storm_android.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 2018/4/10.
 */

public class HistorySQLiteOpenHelper extends SQLiteOpenHelper {

    public final static String DB_TABLE = "records_tb";
    public final static String DB_KEY_NAME = "name";
    private final static String DB_NAME = "temp.db";
    private final static int DB_VERSION = 1;

    public HistorySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + DB_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DB_KEY_NAME + " TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
