package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StudentHelper {
    private static final String DATABASE_TABLE = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;
    private static volatile StudentHelper INSTANCE;


    private StudentHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static StudentHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (StudentHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StudentHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    // Membuka koneksi ke database
    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    // Menutup koneksi
    public void close() {
        databaseHelper.close();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    // Mengambil semua data
    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.StudentColumns._ID + " ASC"
        );
    }
    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                DatabaseContract.StudentColumns._ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public Cursor searchByTitle(String query) {
        return database.query(
                DATABASE_TABLE,
                null,
                DatabaseContract.StudentColumns.NAME + " LIKE ?",
                new String[]{"%" + query + "%"},
                null,
                null,
                DatabaseContract.StudentColumns._ID + " ASC"
        );
    }


    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values,
                DatabaseContract.StudentColumns._ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE,
                DatabaseContract.StudentColumns._ID + " = ?", new String[]{id});
    }
}
