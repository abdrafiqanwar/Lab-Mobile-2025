package com.example.praktikum3.jclass;

import android.net.Uri;

public class Feed {
    private int imageRes = -1;  // Menyimpan ID resource gambar (misal: R.drawable.gambar)
    private String imageUriString;
    private String caption;


    public Feed(int imageRes, String caption) {
        this.imageRes = imageRes;  // Menyimpan resource ID gambar
        this.caption = caption;  // Menyimpan caption
    }


    public Feed(Uri imageUri, String caption) {
        this.imageRes = -1;  // Mengatur imageRes ke -1 karena gambar berasal dari URI
        this.imageUriString = imageUri.toString();  // Mengubah URI ke String untuk disimpan
        this.caption = caption;
    }

    // Memeriksa apakah gambar berasal dari resource (Drawable)
    public boolean isFromResource() {
        return imageRes != -1;  // Jika imageRes tidak -1, berarti gambar berasal dari resource
    }

    // Memeriksa apakah gambar berasal dari URI
    public boolean isFromUri() {
        return imageUriString != null;  // Jika imageUriString tidak null, berarti gambar berasal dari URI
    }

    // Mengambil resource ID gambar
    public int getImageRes() {
        return imageRes;  // Mengembalikan resource ID gambar
    }

    // Mengambil URI gambar
    public Uri getImageUri() {
        return Uri.parse(imageUriString);  // Mengubah imageUriString kembali ke Uri
    }

    // Mengambil caption gambar
    public String getCaption() {
        return caption;  // Mengembalikan caption gambar
    }
}
