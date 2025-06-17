package com.example.praktikum_8.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_NAME = "note";

    public static final class NoteColumn implements BaseColumns {
        public static final String JUDUL = "judul";
        public static final String DESKRIPSI = "deskripsi";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";
    }
}