package com.example.projekfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes_app.db";
    private static final int DATABASE_VERSION = 3; // Updated for category support

    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";
    private static final String KEY_IS_FAVORITE = "is_favorite";
    private static final String KEY_CATEGORY = "category"; // New column

    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_UPDATED_AT + " TEXT,"
                + KEY_IS_FAVORITE + " INTEGER DEFAULT 0,"
                + KEY_CATEGORY + " TEXT DEFAULT 'Uncategorized'" + ")";
        db.execSQL(CREATE_TABLE_QUERY);
        Log.d(TAG, "Database table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add the is_favorite column to existing database
            db.execSQL("ALTER TABLE " + TABLE_NOTES + " ADD COLUMN " + KEY_IS_FAVORITE + " INTEGER DEFAULT 0");
            Log.d(TAG, "Added is_favorite column to existing table");
        }
        if (oldVersion < 3) {
            // Add the category column to existing database
            db.execSQL("ALTER TABLE " + TABLE_NOTES + " ADD COLUMN " + KEY_CATEGORY + " TEXT DEFAULT 'Uncategorized'");
            Log.d(TAG, "Added category column to existing table");
        }
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_CONTENT, note.getContent());
        values.put(KEY_CREATED_AT, getCurrentTimestamp());
        values.put(KEY_UPDATED_AT, getCurrentTimestamp());
        values.put(KEY_IS_FAVORITE, note.isFavorite() ? 1 : 0);
        values.put(KEY_CATEGORY, note.getCategory());

        long id = db.insert(TABLE_NOTES, null, values);
        db.close();
        return id;
    }

    public Note getNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Note note = null;

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NOTES, null, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                note = new Note(
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATED_AT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATED_AT))
                );
                note.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_FAVORITE)) == 1);
                note.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORY)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return note;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " ORDER BY " + KEY_UPDATED_AT + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Note note = new Note(
                            cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATED_AT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATED_AT))
                    );
                    note.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_FAVORITE)) == 1);
                    note.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORY)));
                    notes.add(note);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return notes;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_CONTENT, note.getContent());
        values.put(KEY_UPDATED_AT, getCurrentTimestamp());
        values.put(KEY_IS_FAVORITE, note.isFavorite() ? 1 : 0);
        values.put(KEY_CATEGORY, note.getCategory());

        int rowsAffected = db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
        return rowsAffected;
    }

    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
    }

    public List<Note> searchNotesByTitle(String query) {
        List<Note> matchingNotes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String searchQuery = "SELECT * FROM " + TABLE_NOTES +
                " WHERE " + KEY_TITLE + " LIKE '%" + query + "%'" +
                " ORDER BY " + KEY_UPDATED_AT + " DESC";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(searchQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Note note = new Note(
                            cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATED_AT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATED_AT))
                    );
                    note.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_FAVORITE)) == 1);
                    note.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORY)));
                    matchingNotes.add(note);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return matchingNotes;
    }

    public void toggleFavorite(long noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NOTES, new String[]{KEY_IS_FAVORITE},
                    KEY_ID + "=?", new String[]{String.valueOf(noteId)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int currentValue = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_FAVORITE));
                ContentValues values = new ContentValues();
                values.put(KEY_IS_FAVORITE, currentValue == 0 ? 1 : 0);
                values.put(KEY_UPDATED_AT, getCurrentTimestamp());

                db.update(TABLE_NOTES, values, KEY_ID + "=?", new String[]{String.valueOf(noteId)});
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public List<Note> getFavoriteNotes() {
        List<Note> favoriteNotes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NOTES, null,
                    KEY_IS_FAVORITE + "=?", new String[]{"1"},
                    null, null, KEY_UPDATED_AT + " DESC");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Note note = new Note(
                            cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATED_AT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATED_AT))
                    );
                    note.setFavorite(true);
                    note.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORY)));
                    favoriteNotes.add(note);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        Log.d(TAG, "Found " + favoriteNotes.size() + " favorite notes");

        return favoriteNotes;
    }

    // Add a method to get notes by category
    public List<Note> getNotesByCategory(String category) {
        List<Note> notesInCategory = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NOTES, null,
                    KEY_CATEGORY + "=?", new String[]{category},
                    null, null, KEY_UPDATED_AT + " DESC");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Note note = new Note(
                            cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATED_AT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATED_AT))
                    );
                    note.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_FAVORITE)) == 1);
                    note.setCategory(category);
                    notesInCategory.add(note);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return notesInCategory;
    }

    // Get all available categories
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(true, TABLE_NOTES, new String[]{KEY_CATEGORY},
                    null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String category = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORY));
                    if (!category.isEmpty() && !categories.contains(category)) {
                        categories.add(category);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return categories;
    }
}