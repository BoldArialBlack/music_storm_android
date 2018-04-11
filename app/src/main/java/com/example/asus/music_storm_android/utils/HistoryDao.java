package com.example.asus.music_storm_android.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/4/10.
 */

public class HistoryDao {
    HistorySQLiteOpenHelper helper;
    SQLiteDatabase db;

    public HistoryDao(Context context) {
        helper = new HistorySQLiteOpenHelper(context);
    }

    public boolean hasRecord(String str) {
        boolean hasRecord = false;
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(helper.DB_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (str.equals(cursor.getString(cursor.getColumnIndexOrThrow(helper.DB_KEY_NAME))))
                hasRecord = true;
        }
        db.close();
        return hasRecord;
    }

    public void add(String str) {
        if (!hasRecord(str)) {
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(helper.DB_KEY_NAME, str);
            db.insert(helper.DB_TABLE, null, values);
            db.close();
        }
    }

    public List<String> getHistory() {
        List<String> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(helper.DB_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(helper.DB_KEY_NAME));
            list.add(name);
        }
        db.close();
        return list;
    }

    public List<String> query(String str) {
        String querySql = "SELECT * FROM " + helper.DB_TABLE + " WHERE " + helper.DB_KEY_NAME
                + "LIKE '%" + str + "%' ORDER BY " + helper.DB_KEY_NAME;
        List<String> list = new ArrayList<>();
        Cursor cursor = helper.getReadableDatabase().rawQuery(querySql, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(helper.DB_KEY_NAME));
            list.add(name);
        }
        db.close();
        return list;
    }

    public void deleteAll() {
        db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM " + helper.DB_TABLE);
        db.close();
    }
}
