package com.example.instagram;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private int image, likes, comments, shares, totalPosts, followers, following;
    private String name, time, caption, bio;

    // Constructor untuk menginisialisasi semua atribut
    public Post(int image, String name, String time, String caption, int likes, int comments, int shares, int totalPosts, int followers, int following, String bio) {
        this.image = image;
        this.name = name;
        this.time = time;
        this.caption = caption;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
        this.totalPosts = totalPosts;
        this.followers = followers;
        this.following = following;
        this.bio = bio;
    }

    // Constructor untuk Parcelable
    protected Post(Parcel in) {
        image = in.readInt();
        name = in.readString();
        time = in.readString();
        caption = in.readString();
        likes = in.readInt();
        comments = in.readInt();
        shares = in.readInt();
        totalPosts = in.readInt();
        followers = in.readInt();
        following = in.readInt();
        bio = in.readString();
    }

    // CREATOR untuk Parcelable
    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    // Getter dan Setter untuk atribut baru
    public int getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(int totalPosts) {
        this.totalPosts = totalPosts;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // Getter dan Setter untuk atribut lama
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    // Implementasi Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(image);
        parcel.writeString(name);
        parcel.writeString(time);
        parcel.writeString(caption);
        parcel.writeInt(likes);
        parcel.writeInt(comments);
        parcel.writeInt(shares);
        parcel.writeInt(totalPosts);
        parcel.writeInt(followers);
        parcel.writeInt(following);
        parcel.writeString(bio);
    }
}
