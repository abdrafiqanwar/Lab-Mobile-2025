package com.example.tp7_h071231092.data;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "notes";

    public static final class NotesColumn implements BaseColumns {
        public static String JUDUL = "judul";
        public static String DESKRIPSI = "deskripsi";
        public static String CREATED_AT = "created_at";
    }
}
