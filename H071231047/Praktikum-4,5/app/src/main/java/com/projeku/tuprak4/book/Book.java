package com.projeku.tuprak4.book;

public class Book {
    public String title, author, blurb, genre ,review , imageUri;
    public int year, rating;
    public boolean isLiked;

    public Book(String title, String author, String blurb, String genre, String review, String imageUri, int year, boolean isLiked, int rating) {
        this.title = title;
        this.author = author;
        this.blurb = blurb;
        this.genre = genre;
        this.review = review;
        this.imageUri = imageUri;
        this.year = year;
        this.isLiked = isLiked;
        this.rating = rating;
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

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
