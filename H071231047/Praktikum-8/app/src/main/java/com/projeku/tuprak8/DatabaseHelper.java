package com.projeku.tuprak8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log; // Import Log untuk debugging

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Informasi database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NotesDatabase.db";
    private static final String TABLE_NOTES = "notes_table";

    // Kolom-kolom tabel notes
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_UPDATED_AT = "updated_at";

    private static final String TAG = "DatabaseHelper"; // Tag untuk logging

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Membuat tabel saat database pertama kali dibuat
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_UPDATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_QUERY);
        Log.d(TAG, "Database table created"); // Log pembuatan tabel
    }

    // Memperbarui database jika versi berubah
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
        Log.d(TAG, "Database upgraded from version " + oldVersion + " to " + newVersion); // Log upgrade database
    }

    // Fungsi untuk mendapatkan timestamp saat ini dalam format string
    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    // Menambahkan catatan baru ke database
    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_CONTENT, note.getContent());
        values.put(KEY_CREATED_AT, getCurrentTimestamp()); // Set waktu pembuatan
        values.put(KEY_UPDATED_AT, getCurrentTimestamp()); // Set waktu update sama dengan waktu pembuatan

        long id = db.insert(TABLE_NOTES, null, values);
        db.close();
        Log.d(TAG, "New note added with id: " + id); // Log penambahan catatan
        return id; // Mengembalikan ID baris yang baru dimasukkan, atau -1 jika terjadi kesalahan
    }

    // Mendapatkan satu catatan berdasarkan ID
    public Note getNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTES, new String[]{KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_CREATED_AT, KEY_UPDATED_AT},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        Note note = null;
        if (cursor != null && cursor.moveToFirst()) {
            note = new Note(
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATED_AT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATED_AT))
            );
            cursor.close();
            Log.d(TAG, "Fetched note with id: " + id); // Log pengambilan catatan by ID
        } else {
            Log.d(TAG, "No note found with id: " + id); // Log jika catatan tidak ditemukan
        }
        db.close();
        return note;
    }


    // Mendapatkan semua catatan dari database
    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " ORDER BY " + KEY_UPDATED_AT + " DESC"; // Urutkan berdasarkan waktu update terbaru
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
                    noteList.add(note);
                } while (cursor.moveToNext());
            }
            Log.d(TAG, "Fetched " + noteList.size() + " notes from database"); // Log jumlah catatan yang diambil
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to get notes from database", e); // Log error
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return noteList;
    }

    // Memperbarui catatan yang ada di database
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_CONTENT, note.getContent());
        values.put(KEY_UPDATED_AT, getCurrentTimestamp()); // Perbarui waktu update

        int rowsAffected = db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
        Log.d(TAG, "Note updated with id: " + note.getId() + ". Rows affected: " + rowsAffected); // Log pembaruan catatan
        return rowsAffected; // Mengembalikan jumlah baris yang terpengaruh
    }

    // Menghapus catatan dari database
    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[]{String.valueOf(noteId)});
        db.close();
        Log.d(TAG, "Note deleted with id: " + noteId + ". Rows deleted: " + rowsDeleted); // Log penghapusan catatan
    }

    // Mencari catatan berdasarkan judul (pencarian parsial/substring)
    public List<Note> searchNotesByTitle(String query) {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        // Menggunakan LIKE dengan wildcard '%' di kedua sisi query untuk pencarian substring
        String selection = KEY_TITLE + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + query + "%"};

        try {
            cursor = db.query(TABLE_NOTES, null, selection, selectionArgs, null, null, KEY_UPDATED_AT + " DESC");
            if (cursor.moveToFirst()) {
                do {
                    Note note = new Note(
                            cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATED_AT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(KEY_UPDATED_AT))
                    );
                    noteList.add(note);
                } while (cursor.moveToNext());
            }
            Log.d(TAG, "Search for '" + query + "' found " + noteList.size() + " notes."); // Log hasil pencarian
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to search notes from database", e); // Log error pencarian
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return noteList;
    }
}

