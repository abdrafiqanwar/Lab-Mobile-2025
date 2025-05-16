package com.example.praktikum5.jclass;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    private String title;
    private String author;
    private int year;
    private String synopsis;
    private String coverImageUri;
    private boolean isFavorite;
    private float rating;
    private String genre;

    public Book(String title, String author, int year, String synopsis, String coverImageUri, boolean isFavorite, float rating, String genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.synopsis = synopsis;
        this.coverImageUri = coverImageUri;
        this.isFavorite = isFavorite;
        this.rating = rating;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCoverImageUri() {
        return coverImageUri;
    }

    public void setCoverImageUri(String coverImageUri) {
        this.coverImageUri = coverImageUri;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}