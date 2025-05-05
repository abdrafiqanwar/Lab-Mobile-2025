package com.example.praktikum3.data;

import com.example.praktikum3.R;
import com.example.praktikum3.jclass.Feed;

import java.util.ArrayList;

public class FeedData {
    private static ArrayList<Feed> feedList = new ArrayList<>();
    static {
        feedList.add(new Feed(R.drawable.postingan1, "Postingan 1"));
        feedList.add(new Feed(R.drawable.postingan2, "Postingan 2"));
        feedList.add(new Feed(R.drawable.postingan3, "Postingan 3"));
        feedList.add(new Feed(R.drawable.postingan4, "Postingan 4"));
        feedList.add(new Feed(R.drawable.postingan5, "Postingan 5"));
        feedList.add(new Feed(R.drawable.postingan6, "Postingan 6"));
        feedList.add(new Feed(R.drawable.postingan7, "Postingan 7"));
        feedList.add(new Feed(R.drawable.postingan8, "Postingan 8"));
        feedList.add(new Feed(R.drawable.postingan9, "Postingan 9"));
        feedList.add(new Feed(R.drawable.postingan10, "Postingan 10"));
    }

    public static ArrayList<Feed> getFeedList() {
        return feedList;
    }

    public static void addFeed(Feed newFeed) {
        feedList.add(0, newFeed);
    }
}
