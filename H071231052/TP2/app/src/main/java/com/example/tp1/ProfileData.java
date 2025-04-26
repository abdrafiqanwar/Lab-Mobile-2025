package com.example.tp1;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProfileData implements Parcelable {
    protected ProfileData(Parcel in) {
        name = in.readString();
        bio = in.readString();
        status = in.readString();
        university = in.readString();
        location = in.readString();
        profileImageUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<ProfileData> CREATOR = new Creator<ProfileData>() {
        @Override
        public ProfileData createFromParcel(Parcel in) {
            return new ProfileData(in);
        }

        @Override
        public ProfileData[] newArray(int size) {
            return new ProfileData[size];
        }
    };

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Uri getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(Uri profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    public ProfileData(String name, String bio, String status, String university, String location, Uri profileImageUri) {
        this.name = name;
        this.bio = bio;
        this.status = status;
        this.university = university;
        this.location = location;
        this.profileImageUri = profileImageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String bio;
    private String status;
    private String university;
    private String location;
    private Uri profileImageUri;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(bio);
        parcel.writeString(status);
        parcel.writeString(university);
        parcel.writeString(location);
        parcel.writeParcelable(profileImageUri, i);
    }
}
