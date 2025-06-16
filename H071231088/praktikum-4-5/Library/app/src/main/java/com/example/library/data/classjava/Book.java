package com.example.library.data.classjava;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Book implements Parcelable {
    private int id;
    private String title;
    private String author;
    private int tahunRilis;
    private String coverBookUri;
    private int likeCount;
    private float rating;
    private List<String> genres;
    private long addedTimestamp;
    private String description;

    public Book(int id, String title, String author, int tahunRilis, String coverBookUri,
                int likeCount, float rating, List<String> genres, long addedTimestamp, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.tahunRilis = tahunRilis;
        this.coverBookUri = coverBookUri;
        this.likeCount = likeCount;
        this.rating = rating;
        this.genres = genres;
        this.addedTimestamp = addedTimestamp;
        this.description = description;
    }

    protected Book(Parcel in) {
        id = in.readInt();
        title = in.readString();
        author = in.readString();
        tahunRilis = in.readInt();
        coverBookUri = in.readString();
        likeCount = in.readInt();
        rating = in.readFloat();
        genres = new ArrayList<>();
        in.readStringList(genres);
        addedTimestamp = in.readLong();
        description = in.readString();
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(tahunRilis);
        dest.writeString(coverBookUri);
        dest.writeInt(likeCount);
        dest.writeFloat(rating);
        dest.writeStringList(genres);
        dest.writeLong(addedTimestamp);
        dest.writeString(description);
    }

    // Getter
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getTahunRilis() { return tahunRilis; }
    public String getCoverBookUri() { return coverBookUri; }
    public int getLikeCount() { return likeCount; }
    public float getRating() { return rating; }
    public List<String> getGenres() { return genres; }
    public long getAddedTimestamp() { return addedTimestamp; }
    public String getDescription() {return description; }

    // Equals dan hashCode untuk HashSet di BookViewModel
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}