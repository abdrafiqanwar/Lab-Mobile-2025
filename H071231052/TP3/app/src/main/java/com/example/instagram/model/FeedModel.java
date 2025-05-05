package com.example.instagram.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class FeedModel implements Parcelable {
    int profileImage;
    String profileImageUrl;
    String username;
    String usernameCaption;
    List<PhotoModel> photoList;
    String caption;
    boolean isLiked;
    int likes;
    int comments;

    boolean isSaved;

    String bio;

    String highlightName;
    int highlightProfileImage;
    String highlightProfileImageUrl;
    List<PhotoModel> highlightPhotos;
    int postCount;
    int followerCount;
    int followingCount;


    public FeedModel(String profileImageUrl, String username, List<PhotoModel> photoList, String caption, int likes, int comments, String bio,
                     String highlightName, String highlightProfileImageUrl, List<PhotoModel> highlightPhotos,
                     int postCount, int followerCount, int followingCount) {
        this.profileImageUrl = profileImageUrl;
        this.profileImage = 0; // tidak pakai drawable
        this.username = username;
        this.usernameCaption = username;
        this.photoList = photoList;
        this.caption = caption;
        this.likes = likes;
        this.comments = comments;
        this.bio = bio;
        this.isLiked = false;
        this.isSaved = false;
        this.highlightName = highlightName;
        this.highlightProfileImageUrl = highlightProfileImageUrl;
        this.highlightProfileImage = 0;
        this.highlightPhotos = highlightPhotos;
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }


    public FeedModel(int profileImage, String username, List<PhotoModel> photoList, String caption, int likes, int comments, String bio,
                     String highlightName,
                     int highlightProfileImage,
                     List<PhotoModel> highlightPhotos,
                     int postCount, int followerCount,
                     int followingCount) {
        this.profileImage = profileImage;
        this.username = username;
        this.usernameCaption = username;
        this.photoList = photoList;
        this.caption = caption;
        this.likes = likes;
        this.comments = comments;
        this.bio = bio;
        this.isLiked = false;
        this.isSaved = false;
        this.highlightName = highlightName;
        this.highlightProfileImage = highlightProfileImage;
        this.highlightPhotos = highlightPhotos;
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    protected FeedModel(Parcel in) {
        profileImage = in.readInt();
        profileImageUrl = in.readString();
        username = in.readString();
        usernameCaption = in.readString();
        photoList = in.createTypedArrayList(PhotoModel.CREATOR);
        caption = in.readString();
        bio = in.readString();
        isLiked = in.readByte() != 0;
        comments = in.readInt();
        isSaved = in.readByte() != 0;
        likes = in.readInt();
        highlightName = in.readString();
        highlightProfileImageUrl = in.readString();
        highlightProfileImage = in.readInt();
        highlightPhotos = in.createTypedArrayList(PhotoModel.CREATOR);
        postCount = in.readInt();
        followerCount = in.readInt();
        followingCount = in.readInt();
    }

    public static final Creator<FeedModel> CREATOR = new Creator<FeedModel>() {
        @Override
        public FeedModel createFromParcel(Parcel in) {
            return new FeedModel(in);
        }

        @Override
        public FeedModel[] newArray(int size) {
            return new FeedModel[size];
        }
    };

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getProfileImage() {
        return profileImage;
    }
    public int getHighlightProfileImage() {
        return highlightProfileImage;
    }

    public String getHighlightProfileImageUrl() {
        return highlightProfileImageUrl;
    }

    public String getHighlightName() {
        return highlightName;
    }
    public List<PhotoModel> getHighlightPhotos() {
        return highlightPhotos;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public List<PhotoModel> getPhotoList() {
        return photoList;
    }

    public String getCaption() {
        return caption;
    }
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public boolean isLiked(){
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(profileImage);
        dest.writeString(profileImageUrl);
        dest.writeString(username);
        dest.writeString(usernameCaption);
        dest.writeTypedList(photoList);
        dest.writeString(caption);
        dest.writeString(bio);
        dest.writeByte((byte) (isLiked ? 1 : 0));
        dest.writeInt(comments);
        dest.writeByte((byte) (isSaved ? 1 : 0));
        dest.writeInt(likes);
        dest.writeString(highlightName);
        dest.writeString(highlightProfileImageUrl);
        dest.writeInt(highlightProfileImage);
        dest.writeTypedList(highlightPhotos);
        dest.writeInt(postCount);
        dest.writeInt(followerCount);
        dest.writeInt(followingCount);
    }

    public int getHighlightImage() {
        return highlightProfileImage;
    }
}
