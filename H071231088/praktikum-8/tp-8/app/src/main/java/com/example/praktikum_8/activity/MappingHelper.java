package com.example.praktikum_8.activity;

import com.example.praktikum_8.note.Note;
import com.example.praktikum_8.database.DatabaseContract;

import android.database.Cursor;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Note> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Note> noteList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Note note = new Note();
                int idColumnIndex = cursor.getColumnIndex(DatabaseContract.NoteColumn._ID);
                int judulColumnIndex = cursor.getColumnIndex(DatabaseContract.NoteColumn.JUDUL);
                int deskripsiColumnIndex = cursor.getColumnIndex(DatabaseContract.NoteColumn.DESKRIPSI);
                int createdAtColumnIndex = cursor.getColumnIndex(DatabaseContract.NoteColumn.CREATED_AT);
                int updatedAtColumnIndex = cursor.getColumnIndex(DatabaseContract.NoteColumn.UPDATED_AT);

                if (idColumnIndex != -1) {
                    note.setId(cursor.getInt(idColumnIndex));
                }
                if (judulColumnIndex != -1) {
                    note.setJudul(cursor.getString(judulColumnIndex));
                }
                if (deskripsiColumnIndex != -1) {
                    note.setDeskripsi(cursor.getString(deskripsiColumnIndex));
                }
                if (createdAtColumnIndex != -1) {
                    note.setCreatedAt(cursor.getString(createdAtColumnIndex));
                }
                if (updatedAtColumnIndex != -1) {
                    note.setUpdatedAt(cursor.getString(updatedAtColumnIndex));
                }
                noteList.add(note);
            }
        }
        return noteList;
    }
}