package com.example.tp8;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "note";

    public static final class NoteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DESC = "description";
        public static String DATE = "date";
    }
}