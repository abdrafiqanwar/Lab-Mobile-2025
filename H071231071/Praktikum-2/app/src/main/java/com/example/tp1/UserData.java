package com.example.tp1;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {
    private String username;
    private String name;
    private String bio;
    private String website;
    private String imageUriString;

    public UserData(String username, String name, String bio, String website, Uri imageUri) {
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.website = website;
        // Simpan string URI, tambahkan pengecekan null
        this.imageUriString = imageUri != null ? imageUri.toString() : null;
    }

    protected UserData(Parcel in) {
        username = in.readString();
        name = in.readString();
        bio = in.readString();
        website = in.readString();
        imageUriString = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(bio);
        dest.writeString(website);
        dest.writeString(imageUriString);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getWebsite() {
        return website;
    }

    public Uri getImageUri() {
        return imageUriString != null ? Uri.parse(imageUriString) : null;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUriString = imageUri != null ? imageUri.toString() : null;
    }
}
