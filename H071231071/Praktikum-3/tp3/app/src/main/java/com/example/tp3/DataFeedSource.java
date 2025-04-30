package com.example.tp3;

import com.example.tp3.Model.Feed;
import com.example.tp3.Model.Highlight;
import com.example.tp3.Model.User;

import java.util.ArrayList;

public class DataFeedSource {

    public static ArrayList<User> users = generateDummyUsers();
//    public static ArrayList<Feed> feeds = generateDummyFeeds(users);
    private static User currentUser;

    public static ArrayList<User> getUsers() {
        return users;
    }

//    public static ArrayList<Feed> getFeeds() {
//        return feeds;
//    }

    private static ArrayList<User> generateDummyUsers() {
        ArrayList<User> users = new ArrayList<>();


        // Create users with highlights
        User jennie = new User("kimjennie", "Jennie Blackpink", R.drawable.jennie, "ini bio jennie");
        jennie.addFeed(new Feed(jennie, R.drawable.jennie2, "Play"));
        jennie.addHighlight(new Highlight("Concert Highlights", R.drawable.jennie2, R.drawable.story1));
        jennie.addHighlight(new Highlight("Travel Vlog", R.drawable.jennie2, R.drawable.story3));

        User sainz = new User("carlossainzzz", "Pembalap", R.drawable.sainzf1, "bio siainz");
        sainz.addFeed(new Feed(sainz, R.drawable.sainz2, "Play"));
        sainz.addFeed(new Feed(sainz, R.drawable.sainzf2, "OMAGAAA"));
        sainz.addFeed(new Feed(sainz, R.drawable.sainzf3, "SHEESHH"));
        sainz.addFeed(new Feed(sainz, R.drawable.sainzf4, "LEMME COOK"));
        sainz.addFeed(new Feed(sainz, R.drawable.sainzf5, "EAT, SLEEP, BASKETBALL"));
        sainz.addFeed(new Feed(sainz, R.drawable.sainzf6, "HAVE A NICE DAY YALL"));
        sainz.addHighlight(new Highlight("Race Day", R.drawable.sainz2, R.drawable.sainzf2));
        sainz.addHighlight(new Highlight("Behind the Scenes", R.drawable.sainz2, R.drawable.sainzf3));
        sainz.addHighlight(new Highlight("OMALE", R.drawable.sainz2, R.drawable.sainzf4));
        sainz.addHighlight(new Highlight("HOHO", R.drawable.sainz2, R.drawable.sainzf5));        sainz.addHighlight(new Highlight("Race Day", R.drawable.sainz2, R.drawable.sainzf2));
        sainz.addHighlight(new Highlight("H", R.drawable.sainz2, R.drawable.sainzf6));
        sainz.addHighlight(new Highlight("BISMILLAH", R.drawable.sainz2, R.drawable.sainzf2));

        User jordan = new User("jordan_poole", "Jordan Poole", R.drawable.jordanpp, "NBA Player");
        jordan.addFeed(new Feed(jordan, R.drawable.jordanf1, "Play"));
        jordan.addFeed(new Feed(jordan, R.drawable.jordanf2, "OMAGAAA"));
        jordan.addFeed(new Feed(jordan, R.drawable.jordanf3, "SHEESHH"));
        jordan.addFeed(new Feed(jordan, R.drawable.jordanf4, "LEMME COOK"));
        jordan.addFeed(new Feed(jordan, R.drawable.jordanf5, "EAT, SLEEP, BASKETBALL"));
        jordan.addFeed(new Feed(jordan, R.drawable.jordanf6, "HAVE A NICE DAY YALL"));
        jordan.addHighlight(new Highlight("NBL", R.drawable.jordanhighlight, R.drawable.jordans1));
        jordan.addHighlight(new Highlight("Fav", R.drawable.jordanhighlight, R.drawable.jordans2));
        jordan.addHighlight(new Highlight("Me", R.drawable.jordanhighlight, R.drawable.jordans3));
        jordan.addHighlight(new Highlight("Chillin", R.drawable.jordanhighlight, R.drawable.jordans4));
        jordan.addHighlight(new Highlight("J", R.drawable.jordanhighlight, R.drawable.jordans5));
        jordan.addHighlight(new Highlight("P", R.drawable.jordanhighlight, R.drawable.jordans6));
        jordan.addHighlight(new Highlight("...", R.drawable.jordanhighlight, R.drawable.jordans7));


        User jihyo = new User("_zyozyo", "JIHYO", R.drawable.jihyopp, "bio jihyo");
        jihyo.addFeed(new Feed(jihyo, R.drawable.jihyof1, "Play"));
        jihyo.addFeed(new Feed(jihyo, R.drawable.jihyof2, "OMAGAAA"));
        jihyo.addFeed(new Feed(jihyo, R.drawable.jihyof3, "SHEESHH"));
        jihyo.addFeed(new Feed(jihyo, R.drawable.jihyof4, "LEMME COOK"));
        jihyo.addFeed(new Feed(jihyo, R.drawable.jihyof5, "EAT, SLEEP, BASKETBALL"));
        jihyo.addFeed(new Feed(jihyo, R.drawable.jihyof6, "HAVE A NICE DAY YALL"));
        jihyo.addHighlight(new Highlight("NBL", R.drawable.jihyohighlight, R.drawable.jihyos1));
        jihyo.addHighlight(new Highlight("Fav", R.drawable.jihyohighlight, R.drawable.jihyos2));
        jihyo.addHighlight(new Highlight("Me", R.drawable.jihyohighlight, R.drawable.jihyos3));
        jihyo.addHighlight(new Highlight("Chillin", R.drawable.jihyohighlight, R.drawable.jihyos4));
        jihyo.addHighlight(new Highlight("J", R.drawable.jihyohighlight, R.drawable.jihyos5));
        jihyo.addHighlight(new Highlight("P", R.drawable.jihyohighlight, R.drawable.jihyos6));
        jihyo.addHighlight(new Highlight("...", R.drawable.jihyohighlight, R.drawable.jihyos7));


        // Add users to the list
        users.add(jennie);
        users.add(sainz);
        users.add(jordan);
        users.add(jihyo);
        return users;
    }



    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                currentUser = user;  // Set currentUser
                return user;
            }
        }
        return null;  // Return null if not found
    }


}