package com.projeku.tuprak4.book;

import java.util.ArrayList;

public class DataBook {
    public static ArrayList<Book> bookList = new ArrayList<>();
    static {
        if (bookList.isEmpty()) {
            bookList.addAll(getDummyBooks());
        }
    }
    public static ArrayList<Book> getDummyBooks() {
        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book("The Odyssey", "Homer", "An epic poem about Odysseus's journey home.", "Epic", "Timeless and heroic.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Homerus_-_Odissea%2C_1794_-_3939651_F.jpg/500px-Homerus_-_Odissea%2C_1794_-_3939651_F.jpg",
                1800, false, 4));

        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "A story of the Jazz Age in 1920s America.", "Fiction", "A timeless classic.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/The_Great_Gatsby_Cover_1925_Retouched.jpg/500px-The_Great_Gatsby_Cover_1925_Retouched.jpg",
                1925, false, 5));

        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "A novel about racial injustice in the Deep South.", "Fiction", "A powerful and moving story.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/To_Kill_a_Mockingbird_%28first_edition_cover%29.jpg/960px-To_Kill_a_Mockingbird_%28first_edition_cover%29.jpg",
                1960, false, 5));

        books.add(new Book("1984", "George Orwell", "A dystopian novel about totalitarianism.", "Dystopian", "Chilling and thought-provoking.",
                "https://upload.wikimedia.org/wikipedia/id/c/c7/1984_-_Bentang_Budaya.jpg",
                1949, false, 4));

        books.add(new Book("Pride and Prejudice", "Jane Austen", "A romantic novel about manners and marriage.", "Romance", "Witty and delightful.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/PrideAndPrejudiceTitlePage.jpg/960px-PrideAndPrejudiceTitlePage.jpg",
                1813, false, 5));

        books.add(new Book("Moby-Dick", "Herman Melville", "A tale of obsession and revenge on the high seas.", "Adventure", "Epic and symbolic.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Moby-Dick_FE_title_page.jpg/500px-Moby-Dick_FE_title_page.jpg",
                1851, false, 3));

        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", "A story about teenage rebellion and alienation.", "Fiction", "Iconic and relatable.",
                "https://upload.wikimedia.org/wikipedia/commons/8/89/The_Catcher_in_the_Rye_%281951%2C_first_edition_cover%29.jpg",
                1951, false, 4));

        books.add(new Book("The Hobbit", "J.R.R. Tolkien", "A fantasy adventure in Middle-earth.", "Fantasy", "Magical and adventurous.",
                "https://upload.wikimedia.org/wikipedia/en/4/4a/TheHobbit_FirstEdition.jpg",
                1937, false, 5));

        books.add(new Book("War and Peace", "Leo Tolstoy", "A historical novel set during the Napoleonic Wars.", "Historical", "Profound and expansive.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Tolstoy_-_War_and_Peace_-_first_edition%2C_1869.jpg/500px-Tolstoy_-_War_and_Peace_-_first_edition%2C_1869.jpg",
                1869, false, 4));

        books.add(new Book("The Alchemist", "Paulo Coelho", "A journey of self-discovery and following dreams.", "Philosophical", "Inspiring and uplifting.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/TheAlchemist.jpg/500px-TheAlchemist.jpg",
                1988, false, 5));

        books.add(new Book("Crime and Punishment", "Fyodor Dostoevsky", "A psychological exploration of guilt and redemption.", "Crime", "Dark and compelling.",
                "https://upload.wikimedia.org/wikipedia/en/4/4b/Crimeandpunishmentcover.png",
                1866, false, 4));

        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", "An epic fantasy trilogy.", "Fantasy", "Masterful and immersive.",
                "https://upload.wikimedia.org/wikipedia/en/e/e9/First_Single_Volume_Edition_of_The_Lord_of_the_Rings.gif",
                1954, false, 5));

        books.add(new Book("Brave New World", "Aldous Huxley", "A dystopian novel about a controlled society.", "Dystopian", "Provocative and visionary.",
                "https://upload.wikimedia.org/wikipedia/en/6/62/BraveNewWorld_FirstEdition.jpg",
                1932, false, 4));

        books.add(new Book("Jane Eyre", "Charlotte Brontë", "A novel about love, independence, and resilience.", "Romance", "Passionate and enduring.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9b/Jane_Eyre_title_page.jpg/500px-Jane_Eyre_title_page.jpg",
                1847, false, 5));

        books.add(new Book("The Divine Comedy", "Dante Alighieri", "A journey through Hell, Purgatory, and Paradise.", "Epic", "Profound and allegorical.",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/Philipp_Veit_004.jpg/500px-Philipp_Veit_004.jpg",
                1320, false, 5));

        return books;
    }


}

