package com.projeku.tuprak8;

// Kelas POJO untuk merepresentasikan sebuah catatan
public class Note {
    private int id; // ID unik untuk setiap catatan
    private String title; // Judul catatan
    private String content; // Isi atau deskripsi catatan
    private String createdAt; // Waktu pembuatan catatan (format: yyyy/MM/dd HH:mm:ss)
    private String updatedAt; // Waktu pembaruan terakhir catatan (format: yyyy/MM/dd HH:mm:ss)

    // Konstruktor untuk membuat objek Note baru (tanpa ID, karena ID akan di-generate oleh database)
    public Note(String title, String content, String createdAt, String updatedAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Konstruktor untuk membuat objek Note dari data database (dengan ID)
    public Note(int id, String title, String content, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
