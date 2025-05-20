package com.example.praktikum_4;

import android.graphics.Bitmap;

public class Book {
    public String title;
    public String author;
    public String year;
    public String blurb;
    public int coverResId;
    public boolean isLiked;
    public Bitmap coverBitmap;

    public Book(String title, String author, String year, String blurb, int coverResId, boolean isLiked) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.coverResId = coverResId;
        this.isLiked = isLiked;
    }

    // Tambahkan constructor baru bitmap
    public Book(String title, String author, String year, String blurb, Bitmap coverBitmap, boolean isLiked) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.coverBitmap = coverBitmap;
        this.isLiked = isLiked;
        this.coverResId = -1; // Tidak pakai resource drawable
    }
}
