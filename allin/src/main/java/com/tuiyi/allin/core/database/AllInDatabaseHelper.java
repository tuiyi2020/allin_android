package com.tuiyi.allin.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 本地数据缓存
 */
public class AllInDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "allin.db";

    //数据库升级，放弃老数据。
    public static final int DB_VERSION = 1;

    public AllInDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AllInDatabase.AD.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AllInDatabase.AD.DROP_SQL);
        onCreate(db);
    }
}