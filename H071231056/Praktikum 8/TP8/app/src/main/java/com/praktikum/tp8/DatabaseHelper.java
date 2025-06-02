package com.praktikum.tp8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_UPDATED_AT = "updated_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT NOT NULL,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_CREATED_AT + " TEXT,"
                + COLUMN_UPDATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public long addNote(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String currentTime = getCurrentDateTime();

        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_CREATED_AT, currentTime);
        values.put(COLUMN_UPDATED_AT, currentTime);

        long id = db.insert(TABLE_NOTES, null, values);
        db.close();
        return id;
    }

    public int updateNote(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String currentTime = getCurrentDateTime();

        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_UPDATED_AT, currentTime);

        int result = db.update(TABLE_NOTES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Note> getAllNotes() {
        List<Note> notesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " ORDER BY " + COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT)));
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notesList;
    }

    public List<Note> searchNotes(String query) {
        List<Note> notesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES +
                " WHERE " + COLUMN_TITLE + " LIKE ? ORDER BY " + COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + query + "%"});

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));
                note.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT)));
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notesList;
    }

    public Note getNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, null, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Note note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
            note.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
            note.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));
            note.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT)));
            cursor.close();
            db.close();
            return note;
        }

        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
