package com.example.praktikum3.jclass;

public class Story {
    private int imageRes;
    private String title;

    public Story(int imageRes, String title) {
        this.imageRes = imageRes;
        this.title = title;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getTitle() {
        return title;
    }
}

