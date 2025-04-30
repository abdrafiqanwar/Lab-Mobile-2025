package com.example.tugaspraktikum3;

import java.util.ArrayList;
import java.util.List;

public final class DataDummy {

    private DataDummy() {
        // Private constructor to prevent instantiation
    }

    public static ArrayList<User> getDummyUsers() {
        ArrayList<User> users = new ArrayList<>();

        User personalUser = new User(
                "Pangeran Juhrifar Jafar",
                "https://i.pravatar.cc/150?img=12",
                "Android Developer | Java & Kotlin | Open source contributor",
                getHighlightsForDev(),
                new ArrayList<>(),
                true
        );
        personalUser.setFeeds(getFoodBurger(personalUser));

        User user2 = new User(
                "Ratu_coding",
                "https://i.pravatar.cc/150?img=5",
                "Lifestyle blogger | Travel enthusiast | Coffee lover",
                getHighlightsForLifestyle(),
                new ArrayList<>(),
                false
        );
        user2.setFeeds(getSayuran(user2));

        User user3 = new User(
                "Caisar_coding",
                "https://i.pravatar.cc/150?img=8",
                "Food photographer | Home chef | Always hungry",
                getHighlightsForFood(),
                new ArrayList<>(),
                false
        );
        user3.setFeeds(getFeedsForFood(user3));

        users.add(personalUser);
        users.add(user2);
        users.add(user3);

        return users;
    }

    private static List<User.Story> getHighlightsForDev() {
        List<User.Story> stories = new ArrayList<>();
        stories.add(new User.Story(R.drawable.burger_bun, "Java Tips", "Kumpulan tips Java terbaru"));
        stories.add(new User.Story(R.drawable.cheese, "Android", "Tutorial Android development"));
        stories.add(new User.Story(R.drawable.chicken_patty, "Kotlin", "Belajar Kotlin bersama"));
        stories.add(new User.Story(R.drawable.chicken_patty, "Firebase", "Integrasi dengan Firebase"));
        stories.add(new User.Story(R.drawable.chicken_patty, "UI Design", "Design patterns populer"));
        stories.add(new User.Story(R.drawable.chicken_patty, "Git", "Mengatur versi dengan Git"));
        stories.add(new User.Story(R.drawable.chicken_patty, "PowerShell", "Command line scripting"));
        stories.add(new User.Story(R.drawable.chicken_patty, "CI/CD", "Continuous integration workflow"));
        return stories;
    }

    private static List<User.Story> getHighlightsForLifestyle() {
        List<User.Story> stories = new ArrayList<>();
        stories.add(new User.Story(R.drawable.red_meats, "Bali Trip", "Liburan di Bali minggu lalu"));
        stories.add(new User.Story(R.drawable.egg, "Workout", "Rutinitas workout harian"));
        stories.add(new User.Story(R.drawable.green_chillies, "Skincare", "Review produk skincare terbaru"));
        stories.add(new User.Story(R.drawable.mayonnaise, "Outfits", "OOTD inspirasi mingguan"));
        stories.add(new User.Story(R.drawable.mayonnaise, "Yoga", "Sesi yoga untuk pemula"));
        stories.add(new User.Story(R.drawable.mayonnaise, "Reading", "Rekomendasi buku bulan ini"));
        return stories;
    }

    private static List<User.Story> getHighlightsForFood() {
        List<User.Story> stories = new ArrayList<>();
        stories.add(new User.Story(R.drawable.green_chillies, "Breakfast", "Ide sarapan sehat"));
        stories.add(new User.Story(R.drawable.chicken_patty, "Coffee", "Review kedai kopi terbaik"));
        stories.add(new User.Story(R.drawable.makanan_laut_2, "Desserts", "Resep dessert mudah"));
        stories.add(new User.Story(R.drawable.ikan_bakar, "Street Food", "Jajanan street food favorit"));
        stories.add(new User.Story(R.drawable.green_chillies, "Cooking", "Tips masak cepat dan enak"));
        stories.add(new User.Story(R.drawable.lettuce, "Vegan", "Resep vegan untuk kamu"));
        return stories;
    }

    private static List<User.Feed> getFoodBurger(User user) {
        List<User.Feed> feeds = new ArrayList<>();
        feeds.add(new User.Feed(R.drawable.burger_bun, "Mulai hari dengan burger bun yang enak, bikin mood coding lebih semangat!", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.beef_patty, "Patty daging burger yang sempurna! Project Android selesai dengan hasil yang lezat.", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.bbq_sauce, "Sedikit saus BBQ untuk menambah rasa, seperti menambah semangat dalam coding!", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.cheese, "Keju meleleh di burger, seperti ide kreatif yang meleleh saat coding.", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.chicken_patty, "Patty ayam, pilihan segar untuk burger yang bikin hari jadi lebih nikmat.", user.getUsername()));
        return feeds;
    }

    private static List<User.Feed> getSayuran(User user) {
        List<User.Feed> feeds = new ArrayList<>();
        feeds.add(new User.Feed(R.drawable.green_chillies, "Rasakan kesegaran dan semangat pagi dengan rutin yoga dan journaling.", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.red_chillies, "Weekend getaway ke pantai, nikmati suasana santai dan pedasnya cabai!", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.tomato_slices, "Tomato slices untuk melengkapi salad sehat di pantai. Weekend penuh energi!", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.pickles, "Pickles segar untuk menambah kesegaran di weekend getaway ke pantai.", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.lettuce, "Lettuce fresh dan crunchy, menambah segar suasana weekend yang nyaman.", user.getUsername()));
        return feeds;
    }

    private static List<User.Feed> getFeedsForFood(User user) {
        List<User.Feed> feeds = new ArrayList<>();
        feeds.add(new User.Feed(R.drawable.makanan_laut_1, "Pasta carbonara autentik ala Italia, siapkan lidah untuk cita rasa Italia yang sebenarnya.", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.makanan_laut_2, "Food hunting di Bandung, temukan 5 cafe terbaik dengan hidangan seafood yang menggoda.", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.makanan_laut_3, "Cicipi berbagai hidangan seafood di Bandung, yang siap memanjakan lidahmu!", user.getUsername()));
        feeds.add(new User.Feed(R.drawable.ikan_bakar, "Ikan bakar segar yang menggugah selera, wajib dicoba saat food hunting di Bandung!", user.getUsername()));
        return feeds;
    }
}
