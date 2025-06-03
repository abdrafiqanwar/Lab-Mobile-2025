package com.example.tp7_h071231092.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tp7_h071231092.data.DatabaseContract;
import com.example.tp7_h071231092.data.DatabaseHelper;

public class NotesHelper {
    public static final String DATABASE_TABLE = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase database;
    private static volatile NotesHelper INSTANCE;

    public NotesHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static NotesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NotesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor searchByTitle(String query) {
        return database.query(
                DATABASE_TABLE,
                null,
                DatabaseContract.NotesColumn.JUDUL + " LIKE ?",
                new String[]{"%" + query + "%"},
                null,
                null,
                DatabaseContract.NotesColumn._ID + " ASC"
        );
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.NotesColumn._ID + " ASC"
        );
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                DatabaseContract.NotesColumn._ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, DatabaseContract.NotesColumn._ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, DatabaseContract.NotesColumn._ID + " = ?", new String[]{id});
    }
}
