//package com.example.tp3.Model;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class User implements Parcelable {
//    private String username;
//    private String name;
//    private int profileImage;
//    private String bio;
//    private List<Feed> feedList;
//    private List<Story> storyList;
//    private List<Highlight> highlightList;  // Tambahkan list untuk highlight
//
//
//    private List<Highlight> detailHighlight;
//
//    public User(String username, String name, int profileImage, String bio) {
//        this.username = username;
//        this.name = name;
//        this.profileImage = profileImage;
//        this.bio = bio;
//        this.feedList = new ArrayList<>();
//        this.storyList = new ArrayList<>();
//        this.highlightList = new ArrayList<>();  // Inisialisasi list highlight
//    }
//
//    protected User(Parcel in) {
//        username = in.readString();
//        name = in.readString();
//        profileImage = in.readInt();
//        bio = in.readString();
//        feedList = in.createTypedArrayList(Feed.CREATOR);
//        storyList = in.createTypedArrayList(Story.CREATOR);
//        highlightList = in.createTypedArrayList(Highlight.CREATOR);  // Deserialisasi highlight
//    }
//
//    public static final Creator<User> CREATOR = new Creator<User>() {
//        @Override
//        public User createFromParcel(Parcel in) {
//            return new User(in);
//        }
//
//        @Override
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getProfileImage() {
//        return profileImage;
//    }
//
//    public String getBio() {
//        return bio;
//    }
//
//    public List<Feed> getFeedList() {
//        return feedList;
//    }
//    public int getFeedCount() {
//        return feedList.size();
//    }
//
//
//    public List<Story> getStoryList() {
//        return storyList;
//    }
//
//    public List<Highlight> getHighlightList() {
//        return highlightList;  // Mengembalikan list highlight
//    }
//
//    public List<Highlight> getDetailHighlight() {
//        return detailHighlight;
//    }
//
//    public void setFeedList(List<Feed> feedList) {
//        this.feedList = feedList;
//    }
//
//    public void addFeed(Feed feed) {
//        feedList.add(0, feed); // Ditambahkan di awal (biar seperti post terbaru)
//    }
//
//    public void addStory(Story story) {
//        storyList.add(story);
//    }
//
//    public void addHighlight(Highlight highlight) {
//        highlightList.add(highlight); // Tambahkan highlight ke list
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(username);
//        dest.writeString(name);
//        dest.writeInt(profileImage);
//        dest.writeString(bio);
//        dest.writeTypedList(feedList);
//        dest.writeTypedList(storyList);
//        dest.writeTypedList(highlightList);
//    }
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//}
//
//
//


package com.example.tp3.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    private String username;
    private String name;
    private int profileImage;
    private String bio;
    private List<Feed> feedList;
    private List<Story> storyList;
    private List<Highlight> highlightList;  // Tambahkan list untuk highlight

    public User(String username, String name, int profileImage, String bio) {
        this.username = username;
        this.name = name;
        this.profileImage = profileImage;
        this.bio = bio;
        this.feedList = new ArrayList<>();
        this.storyList = new ArrayList<>();
        this.highlightList = new ArrayList<>();  // Inisialisasi list highlight
    }

    protected User(Parcel in) {
        username = in.readString();
        name = in.readString();
        profileImage = in.readInt();
        bio = in.readString();
        feedList = in.createTypedArrayList(Feed.CREATOR);
        storyList = in.createTypedArrayList(Story.CREATOR);
        highlightList = in.createTypedArrayList(Highlight.CREATOR);  // Deserialisasi highlight
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public String getBio() {
        return bio;
    }

    public List<Feed> getFeedList() {
        return feedList;
    }


    public List<Story> getStoryList() {
        return storyList;
    }

    public List<Highlight> getHighlightList() {
        return highlightList;
    }

    public void setFeedList(List<Feed> feedList) {
        this.feedList = feedList;
    }

    public void addHighlight(Highlight highlight) {
        highlightList.add(highlight); // Tambahkan highlight ke list
    }
    public void addFeed(Feed feed) {
        feedList.add(0, feed); // Ditambahkan di awal (biar seperti post terbaru)
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(name);
        dest.writeInt(profileImage);
        dest.writeString(bio);
        dest.writeTypedList(feedList);
        dest.writeTypedList(storyList);
        dest.writeTypedList(highlightList);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
