package com.example.tp3.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class StoryModel {
    private int type;
    private String id;
    private String storyId;
    private String username;
    private int profileImageRes;

    // Constructor for Add Story
    public StoryModel(int type, String id, int profileImageRes) {
        this.type = type;
        this.id = id;
        this.profileImageRes = profileImageRes;
    }

    // Constructor for All Stories
    public StoryModel(int type, String id, String storyId, String username, int profileImageRes) {
        this.type = type;
        this.id = id;
        this.storyId = storyId;
        this.username = username;
        this.profileImageRes = profileImageRes;
    }

    public int getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getStoryId() {
        return storyId;
    }

    public String getUsername() {
        return username;
    }

    public int getProfileImageRes() {
        return profileImageRes;
    }

}