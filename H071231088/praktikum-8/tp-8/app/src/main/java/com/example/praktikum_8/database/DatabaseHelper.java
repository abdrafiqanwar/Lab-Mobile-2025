package com.example.praktikum_8.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notedb.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_NOTE = "CREATE TABLE " + DatabaseContract.TABLE_NAME + " (" +
                DatabaseContract.NoteColumn._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.NoteColumn.JUDUL + " TEXT NOT NULL, " +
                DatabaseContract.NoteColumn.DESKRIPSI + " TEXT NOT NULL, " +
                DatabaseContract.NoteColumn.CREATED_AT + " TEXT, " +
                DatabaseContract.NoteColumn.UPDATED_AT + " TEXT" +
                ")";
        db.execSQL(CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}