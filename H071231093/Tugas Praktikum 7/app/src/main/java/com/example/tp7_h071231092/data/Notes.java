package com.example.tp7_h071231092.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Notes implements Parcelable {
    private int id;
    private String judul;
    private String deskripsi;
    private String createdAt;

    public Notes() {}

    public Notes(int id, String judul, String deskripsi) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
    }

    protected Notes(Parcel in) {
        id = in.readInt();
        judul = in.readString();
        deskripsi = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(judul);
        parcel.writeString(deskripsi);
    }
}
