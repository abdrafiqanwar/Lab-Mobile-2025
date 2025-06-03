package com.example.database;

import android.provider.BaseColumns;

//penamaan struktur database
public class DatabaseContract {
    public static final String TABLE_NAME = "student";

    public static final class StudentColumns implements BaseColumns {
        public static final String NAME = "name";
        public static final String NIM = "nim";

        // Kolom untuk tracking waktu
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";
    }
}
