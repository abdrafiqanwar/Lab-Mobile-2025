package com.example.praktikum3.jclass;

import java.util.ArrayList;

public class Home {
    private int profileImage;
    private int postImage;
    private String caption;
    private String username;
    private ArrayList<Integer> postList; // List of post images

    public Home(int profileImage, int postImage, String caption, String username, ArrayList<Integer> postList) {
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.caption = caption;
        this.username = username;
        this.postList = postList;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public int getPostImage() {
        return postImage;
    }

    public String getCaption() {
        return caption;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Integer> getPostList() {
        return postList;
    }
}