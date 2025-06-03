package com.example.praktikum8;

import android.database.Cursor;

import com.example.database.DatabaseContract;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Student> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Student> students = new ArrayList<>(); // Menyimpan hasil konversi dalam bentuk list

        // Looping setiap baris data pada cursor
        while (cursor.moveToNext()) {
            // Mengambil data ID dari kolom _ID
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns.NAME));
            String nim = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns.NIM));
            String createdAt = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns.CREATED_AT));
            String updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.StudentColumns.UPDATED_AT));

            students.add(new Student(id, name, nim, createdAt, updatedAt));
        }

        // Mengembalikan seluruh data yang telah dikonversi
        return students;
    }
}
