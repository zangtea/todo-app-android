package com.example.todo_app_android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ChiTieuDB";
    private static final int DB_VERSION = 1;

    public static final String TABLE_EXPENSE = "expenses";
    public static final String COL_ID = "id";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_CATEGORY = "category";
    public static final String COL_DATE = "date";
    public static final String COL_NOTE = "note";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_EXPENSE + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_AMOUNT + " REAL NOT NULL, " +
                    COL_CATEGORY + " TEXT NOT NULL, " +
                    COL_DATE + " TEXT NOT NULL, " +
                    COL_NOTE + " TEXT" + ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        onCreate(db);
    }
}