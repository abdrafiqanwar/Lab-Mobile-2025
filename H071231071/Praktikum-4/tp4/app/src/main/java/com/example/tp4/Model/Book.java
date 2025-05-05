package com.example.tp4.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Book implements Parcelable {
    private String title, author, blurb, coverUp, genre;

    private int release_year;

    private int cover;
    private float rating;



    public Book(String title, String author, int release_year, String blurb, int cover, String genre, float rating) {
        this.title = title;
        this.author = author;
        this.release_year = release_year;
        this.blurb = blurb;
        this.cover = cover;
        this.genre = genre;
        this.rating = rating;
    }

    public Book(String title, String author, int release_year, String blurb, Uri coverUp, String genre,float rating) {
        this.title = title;
        this.author = author;
        this.release_year = release_year;
        this.blurb = blurb;
        this.coverUp = coverUp != null ? coverUp.toString() : null;
        this.genre = genre;
        this.rating = rating;
    }


    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        release_year = in.readInt();
        blurb = in.readString();
        cover = in.readInt();
        rating = in.readFloat();
        genre = in.readString();
        coverUp = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(release_year);
        dest.writeString(blurb);
        dest.writeInt(cover);
        dest.writeFloat(rating);
        dest.writeString(genre);
        dest.writeString(coverUp);
    }


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getRelease_year() {
        return release_year;
    }

    public String getBlurb() {
        return blurb;
    }

    public int getCover() {
        return cover;
    }



    public Uri getCoverUp() {
        return coverUp != null ? Uri.parse(coverUp) : null;
    }

    public String getGenre() {
        return genre;
    }

    public float getRating() {
        return rating;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book book = (Book) obj;

        return title.equals(book.title) &&
                author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return title.hashCode() + author.hashCode();
    }

}
