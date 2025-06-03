package com.example.tp7_h071231092.util;

import android.database.Cursor;

import com.example.tp7_h071231092.data.DatabaseContract;
import com.example.tp7_h071231092.data.Notes;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Notes> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Notes> notesList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.NotesColumn._ID));
            String judul = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NotesColumn.JUDUL));
            String deskripsi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NotesColumn.DESKRIPSI));
            String createdAt = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NotesColumn.CREATED_AT));
            Notes note = new Notes(id, judul, deskripsi);
            note.setCreatedAt(createdAt);
            notesList.add(note);
        }
        return notesList;
    }
}
