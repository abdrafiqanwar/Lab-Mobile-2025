package com.example.tp3.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Highlight implements Parcelable {
    private String title;
    private int coverImage;

    private int detailStory;



    public Highlight(String title, int coverImage, int detailStory) {
        this.title = title;
        this.coverImage = coverImage;
        this.detailStory = detailStory;

    }

    protected Highlight(Parcel in) {
        title = in.readString();
        coverImage = in.readInt();
        detailStory = in.readInt();
    }

    public static final Creator<Highlight> CREATOR = new Creator<Highlight>() {
        @Override
        public Highlight createFromParcel(Parcel in) {
            return new Highlight(in);
        }

        @Override
        public Highlight[] newArray(int size) {
            return new Highlight[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public int getCoverImage() {
        return coverImage;
    }

    public int getDetailStory() {
        return detailStory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(coverImage);
        dest.writeInt(detailStory);
    }
}