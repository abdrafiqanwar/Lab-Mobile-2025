package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    private static final int DATABASE_VERSION = 2;

    // Query pembuatan tabel student
    private static final String SQL_CREATE_TABLE_STUDENT =
            "CREATE TABLE " + DatabaseContract.TABLE_NAME + " (" +
                    DatabaseContract.StudentColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseContract.StudentColumns.NAME + " TEXT NOT NULL," +
                    DatabaseContract.StudentColumns.NIM + " TEXT NOT NULL," +
                    DatabaseContract.StudentColumns.CREATED_AT + " TEXT," +
                    DatabaseContract.StudentColumns.UPDATED_AT + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


     //Dipanggil saat database pertama kali dibuat

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}
