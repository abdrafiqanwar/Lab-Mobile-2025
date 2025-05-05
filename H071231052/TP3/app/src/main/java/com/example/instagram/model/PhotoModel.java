package com.example.instagram.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PhotoModel implements Parcelable {
    private int imageRes;
    private String imageUrl;
    private Uri uri;

    public PhotoModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public PhotoModel(int imageRes) {
        this.imageRes = imageRes;
    }
    public PhotoModel(Uri uri) {
        this.uri = uri;
    }

    protected PhotoModel(Parcel in) {
        imageUrl = in.readString();
        imageRes = in.readInt();
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<PhotoModel> CREATOR = new Creator<PhotoModel>() {
        @Override
        public PhotoModel createFromParcel(Parcel in) {
            return new PhotoModel(in);
        }

        @Override
        public PhotoModel[] newArray(int size) {
            return new PhotoModel[size];
        }
    };

    public String getImageUrl(){
        return imageUrl;
    }

    public int getImageRes() {
        return imageRes;
    }

    public Uri getUri() {
        return uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(imageUrl);
        parcel.writeInt(imageRes);
        parcel.writeParcelable(uri, flags);
    }
}
