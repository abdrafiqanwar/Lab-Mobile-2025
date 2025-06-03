package com.example.praktikum8;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

 // model untuk menyimpan data mahasiswa
public class Student implements Parcelable {

    // ======= Properti (atribut) mahasiswa =======
    private int id;
    private String name;
    private String nim;
    private String createdAt;
    private String updatedAt;    //

    public Student() {
    }

    // Digunakan saat user input data baru
    public Student(String name, String nim) {
        this.name = name;
        this.nim = nim;
    }
    // Digunakan saat data dari database sudah memiliki ID
    public Student(int id, String name, String nim) {
        this.id = id;
        this.name = name;
        this.nim = nim;
    }

    // Digunakan saat ingin menyimpan/menampilkan data lengkap dari database
    public Student(int id, String name, String nim, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.nim = nim;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Digunakan saat mengambil data dari Parcel (untuk Parcelable)
    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
        nim = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    // Digunakan untuk mengirim dan menerima objek Student antar Activity
    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in); // Membuat objek dari Parcel
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size]; // Membuat array Student
        }
    };


    // Method untuk membaca dan mengatur nilai properti

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    // Tidak digunakan secara langsung, tapi wajib ditulis

    @Override
    public int describeContents() {
        return 0;
    }

    // Menulis data ke Parcel (untuk dikirim ke Activity lain)
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(nim);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }
}
