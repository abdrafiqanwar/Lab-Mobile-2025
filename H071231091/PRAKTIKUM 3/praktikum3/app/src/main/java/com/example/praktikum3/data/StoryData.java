package com.example.praktikum3.data;

import com.example.praktikum3.R;
import com.example.praktikum3.jclass.Story;

import java.util.ArrayList;

public class StoryData {
    public static ArrayList<Story> getStoryList() {
        ArrayList<Story> stories = new ArrayList<>();

        stories.add(new Story(R.drawable.story01, "GEEK FAM"));
        stories.add(new Story(R.drawable.story02, "TLID"));
        stories.add(new Story(R.drawable.story03, "ONIC"));
        stories.add(new Story(R.drawable.story04, "ALTER EGO"));
        stories.add(new Story(R.drawable.story05, "EVOS"));
        stories.add(new Story(R.drawable.story06, "NAVI"));

        return stories;
    }
}

