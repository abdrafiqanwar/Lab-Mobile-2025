package com.example.kilogram;

import java.util.ArrayList;
import java.util.List;

public class StoryDataProvider {

    /**
     * Creates a list of dummy story data for testing/demo purposes
     * @return List of Story objects with dummy data
     */
    public static List<Story> getDummyStories() {
        List<Story> stories = new ArrayList<>();

        // Add dummy stories (10 items)
        stories.add(new Story("1", "Your Story", "https://i.pinimg.com/736x/4f/24/d8/4f24d8197dd5822ac01184e706b33b1d.jpg", true));
        stories.add(new Story("2", "Robert_Brown", "https://randomuser.me/api/portraits/men/2.jpg", true));
        stories.add(new Story("3", "Alice_Johnson", "https://randomuser.me/api/portraits/women/3.jpg", false));
        stories.add(new Story("4", "David_Miller", "https://randomuser.me/api/portraits/men/4.jpg", true));
        stories.add(new Story("5", "Sophia_Clark", "https://randomuser.me/api/portraits/women/5.jpg", false));
        stories.add(new Story("6", "Liam_Walker", "https://randomuser.me/api/portraits/men/6.jpg", true));
        stories.add(new Story("7", "Isabella_Hall", "https://randomuser.me/api/portraits/women/7.jpg", false));
        stories.add(new Story("8", "Ethan_Allen", "https://randomuser.me/api/portraits/men/8.jpg", true));
        stories.add(new Story("9", "Mia_Young", "https://randomuser.me/api/portraits/women/9.jpg", false));
        stories.add(new Story("10", "Aiden_Pearce", "https://randomuser.me/api/portraits/men/10.jpg", true));

        return stories;
    }

    /**
     * Get a specific story by ID
     * @param id The ID of the story to retrieve
     * @return The Story object if found, null otherwise
     */
    public static Story getStoryById(String id) {
        for (Story story : getDummyStories()) {
            if (story.getId().equals(id)) {
                return story;
            }
        }
        return null;
    }
}