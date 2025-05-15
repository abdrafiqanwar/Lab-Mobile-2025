package com.example.instagram;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private int totalPosts;
    private int followers;
    private int following;
    private String bio;
    private List<String> imageUris;
    private List<String> feedUris;
    private String profileImageUri;

    // Constructor default dengan nilai-nilai bawaan
    public User() {
        this.name = "Nama Default";
        this.totalPosts = 0;
        this.followers = 0;
        this.following = 0;
        this.bio = "Halo! Saya pengguna baru.";
        this.profileImageUri = ""; // misalnya URI default
        this.imageUris = new ArrayList<>(); // list kosong sebagai default
        this.feedUris = new ArrayList<>();
    }

    // Constructor lengkap dengan parameter
    public User(String name, int totalPosts, int followers, int following, String bio, String profileImageUri) {
        this.name = name;
        this.totalPosts = totalPosts;
        this.followers = followers;
        this.following = following;
        this.bio = bio;
        this.profileImageUri = profileImageUri;
        this.imageUris = new ArrayList<>();
        this.feedUris = new ArrayList<>();
    }

    // Getter dan Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public List<String> getImageUris() {
        return imageUris;
    }

    public void addImageUri(String uri) {
        this.imageUris.add(uri);
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }
    public List<String> getFeedUris() {
        return feedUris;
    }
    public void addFeedUri(String uri) {
        this.feedUris.add(uri);
    }

    // Menampilkan semua data user sebagai String
    public String displayUserInfo() {
        return "Name: " + name + "\n" +
                "Posts: " + totalPosts + "\n" +
                "Followers: " + followers + "\n" +
                "Following: " + following + "\n" +
                "Bio: " + bio + "\n" +
                "Profile Image URI: " + profileImageUri + "\n" +
                "Image URIs: " + imageUris.toString();
    }
}
