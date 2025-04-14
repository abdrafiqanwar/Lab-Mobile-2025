package com.example.memuemulator;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name, username, email, avatarUri;

    public User(String name, String username, String email, String avatarUri) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.avatarUri = avatarUri;
    }

    protected User(Parcel in) {
        name = in.readString();
        username = in.readString();
        email = in.readString();
        avatarUri = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public  String getName() {return name;}
    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public String getAvatarUri() {return avatarUri;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(avatarUri);

    }
}
