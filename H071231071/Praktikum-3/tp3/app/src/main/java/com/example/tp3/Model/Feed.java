////package com.example.tp3.Model;
////
////import android.os.Parcel;
////import android.os.Parcelable;
////
////public class Feed implements Parcelable {
////    private String username;
////    private int feedImage;
////    private String caption;
//////    private transient User user;
////
////    // Hapus transient dari variabel user di kelas Feed
////    private User user; // Hapus keyword transient
////
////
////    private long timestamp; // Tambahkan ini
////
////    public Feed(User user, int feedImage, String caption) {
////        this.user = user;
////        this.username = user.getUsername();
////        this.feedImage = feedImage;
////        this.caption = caption;
////        this.timestamp = System.currentTimeMillis(); // waktu sekarang
////    }
////
//////    protected Feed(Parcel in) {
//////        username = in.readString();
//////        feedImage = in.readInt();
//////        caption = in.readString();
//////        timestamp = in.readLong(); // baca timestamp
//////    }
////
////    protected Feed(Parcel in) {
////        username = in.readString();
////        feedImage = in.readInt();
////        caption = in.readString();
////        timestamp = in.readLong();
////        user = in.readParcelable(User.class.getClassLoader());
////    }
////
////
////    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
////        @Override
////        public Feed createFromParcel(Parcel in) {
////            return new Feed(in);
////        }
////
////        @Override
////        public Feed[] newArray(int size) {
////            return new Feed[size];
////        }
////    };
////
//////    @Override
//////    public void writeToParcel(Parcel dest, int flags) {
//////        dest.writeString(username);
//////        dest.writeInt(feedImage);
//////        dest.writeString(caption);
//////        dest.writeLong(timestamp); // tulis timestamp
//////    }
////
////    @Override
////    public void writeToParcel(Parcel dest, int flags) {
////        dest.writeString(username);
////        dest.writeInt(feedImage);
////        dest.writeString(caption);
////        dest.writeLong(timestamp);
////        dest.writeParcelable(user, flags);
////    }
////
////
////    // Getter tambahan
////    public long getTimestamp() {
////        return timestamp;
////    }
////
////    public User getUser() {
////        return user;
////    }
////
////    public void setUser(User user) {
////        this.user = user;
////    }
////
////    public String getUsername() {
////        return username;
////    }
////
////    public int getFeedImage() {
////        return feedImage;
////    }
////
////    public String getCaption() {
////        return caption;
////    }
////
////    @Override
////    public int describeContents() {
////        return 0;
////    }
////}
//
package com.example.tp3.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Feed implements Parcelable {
    private String username;
    private int feedImage;
    private String caption;
    private String imageUriString;

    private String userId;
    private int userProfileImage;
    private transient User user;

    public Feed(User user, int feedImage, String caption) {
        this.username = user.getUsername();
        this.userId = user.getUsername();
        this.userProfileImage = user.getProfileImage();
        this.user = user;
        this.feedImage = feedImage;
        this.caption = caption;
    }

    public Feed(User user, Uri imageUri, String caption) {
        this.username = user.getUsername();
        this.userId = user.getUsername();
        this.userProfileImage = user.getProfileImage();
        this.user = user;
        this.imageUriString = imageUri != null ? imageUri.toString() : null;
        this.caption = caption;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(feedImage);
        dest.writeString(caption);
        dest.writeString(userId);
        dest.writeInt(userProfileImage);
        dest.writeString(imageUriString);
    }

    protected Feed(Parcel in) {
        username = in.readString();
        feedImage = in.readInt();
        caption = in.readString();
        userId = in.readString();
        userProfileImage = in.readInt();
        imageUriString = in.readString();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public void reconstructUser(User user) {
        this.user = user;
    }

    public User getUser() { return user; }
    public String getUsername() { return username; }
    public int getFeedImage() { return feedImage; }
    public String getCaption() { return caption; }
    public int getUserProfileImage() { return userProfileImage; }

    public Uri getImageUri() {
        return imageUriString != null ? Uri.parse(imageUriString) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
