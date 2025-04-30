package com.example.tp3.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Story implements Parcelable {
    private User user;
    private String title;
    private int storyImage;

    public Story(User user, String title, int storyImage) {
        this.user = user;
        this.title = title;
        this.storyImage = storyImage;
    }

    protected Story(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        title = in.readString();
        storyImage = in.readInt();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeString(title);
        dest.writeInt(storyImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public int getStoryImage() {
        return storyImage;
    }
}
