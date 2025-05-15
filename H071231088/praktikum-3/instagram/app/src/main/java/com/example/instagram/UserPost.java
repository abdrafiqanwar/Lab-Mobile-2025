package com.example.instagram;

public class UserPost {
    private int likes;
    private int comments;
    private String caption;
    private String time;
    private String feedUris;

    // Constructor
    public UserPost(int likes, int comments, String caption, String time, String feedUris) {
        this.likes = likes;
        this.comments = comments;
        this.caption = caption;
        this.time = time;
        this.feedUris = feedUris;
    }


    // Getter & Setter
    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public int getComments() { return comments; }
    public void setComments(int comments) { this.comments = comments; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getFeedUris() { return feedUris; }
    public void setFeedUris(String feedUris) { this.feedUris = feedUris; }
}
