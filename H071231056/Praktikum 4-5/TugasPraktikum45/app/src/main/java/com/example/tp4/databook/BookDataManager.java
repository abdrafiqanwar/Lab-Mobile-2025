package com.example.tp4.databook;

import android.net.Uri;

import com.example.tp4.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookDataManager {
    // Using CopyOnWriteArrayList for thread safety
    public static ArrayList<Book> books = createBooks();
    public static ArrayList<Book> bookList = books;

    private static int nextId = 15;

    private static ArrayList<Book> createBooks() {
        ArrayList<Book> books = new ArrayList<>();
        Uri bbqsauce = Uri.parse("android.resource://com.example.tp4/" + R.drawable.bbqsauce);

        books.add(new Book(1,"Saus BBQ", "Pangeran", 2023, "Cara Buat Saus BBQ.",
                bbqsauce, false, "Food", 4.5f));


        Uri beefbatty = Uri.parse("android.resource://com.example.tp4/" + R.drawable.beefbatty);

        books.add(new Book(2,"Beef Batty", "Pangeran", 2023, "Cara Membuat Beef Batty.",
                beefbatty, false, "Food", 4.5f));


        Uri cheese = Uri.parse("android.resource://com.example.tp4/" + R.drawable.cheese);

        books.add(new Book(3,"Cheese", "Pangeran", 2023, "Keju yang enak.",
                cheese, false, "Romantis", 4.5f));


        Uri egg = Uri.parse("android.resource://com.example.tp4/" + R.drawable.egg);

        books.add(new Book(4,"Telur Goreng", "Pangeran", 2023, "Buat telur goreng.",
                egg, false, "Science Fiction", 4.5f));


        Uri burgerbun = Uri.parse("android.resource://com.example.tp4/" + R.drawable.burgerbun);

        books.add(new Book(5,"Burger Bun", "Pangeran", 2023, "Cara Membuat Burger Bun.",
                burgerbun, false, "Classic", 4.5f));


        Uri goldenonion = Uri.parse("android.resource://com.example.tp4/" + R.drawable.goldenonion);

        books.add(new Book(6,"Golden Onion", "Pangeran", 2023, "Golden Onion yang enak dan lezat.",
                goldenonion, false, "Classic", 4.5f));


        Uri chickenpatty = Uri.parse("android.resource://com.example.tp4/" + R.drawable.chickenpatty);

        books.add(new Book(7,"Chicken Patty", "Pangeran", 2023, "Cara Membuat Chicken Patty.",
                chickenpatty, false, "Adventure", 4.5f));


        Uri greenchillies = Uri.parse("android.resource://com.example.tp4/" + R.drawable.greenchillies);

        books.add(new Book(8,"Panduan Cara Menanam Cabe Hijau", "Pangeran", 2023, "Bagaimana Cara Menanam Cabe Hijau.",
                greenchillies, false, "Adventure", 4.5f));


        Uri lemon = Uri.parse("android.resource://com.example.tp4/" + R.drawable.lemon);

        books.add(new Book(9,"Lemon", "Pangeran", 2023, "Lemon yang sangat segar.",
                lemon, false, "Food", 4.5f));


        Uri mayonnaise = Uri.parse("android.resource://com.example.tp4/" + R.drawable.mayonnaise);

        books.add(new Book(10,"Tutor Buat Mayonnaise", "Pangeran", 2023, "Mayonnaise yang enak.",
                mayonnaise, false, "Food", 4.5f));


        Uri tomato = Uri.parse("android.resource://com.example.tp4/" + R.drawable.tomatoslices);

        books.add(new Book(11,"Tomat", "Pangeran", 2023, "Cara Menanam Tomat.",
                tomato, false, "Food", 4.5f));


        Uri redchillies = Uri.parse("android.resource://com.example.tp4/" + R.drawable.redchillies);

        books.add(new Book(12,"Cabe Merah", "Pangeran", 2023, "Cara Menanam Cabe Merah.",
                redchillies, false, "Food", 4.5f));


        Uri redkecthup = Uri.parse("android.resource://com.example.tp4/" + R.drawable.redkecthup);

        books.add(new Book(13,"Kecap", "Pangeran", 2023, "Cara Membuat Kecap Di Rumah.",
                redkecthup, false, "Food", 4.5f));


        Uri redmeats = Uri.parse("android.resource://com.example.tp4/" + R.drawable.redmeats);

        books.add(new Book(14,"Gambar Dagin Sehat", "Pangeran", 2023, "Daging yang cocok untuk BBQ.",
                redmeats, false, "Food", 4.5f));


        Uri yougurt = Uri.parse("android.resource://com.example.tp4/" + R.drawable.yogurt);

        books.add(new Book(15,"Yogurt", "Pangeran", 2023, "Tutor Pakai Yogurt di BBQ.",
                yougurt, false, "Food", 4.5f));

        return books;
    }

    public static synchronized int getNextId() {
        return nextId++;
    }

    public static synchronized void addBook(Book book) {
        bookList.add(0, book);
    }

    public static synchronized ArrayList<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public ArrayList<Book> searchBooks(String query) {
        if (query == null || query.isEmpty()) {
            return getAllBooks();
        }

        ArrayList<Book> results = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerCaseQuery)) {
                results.add(book);
            }
        }
        return results;
    }

    public ArrayList<Book> filterBooksByGenre(String genre) {
        if (genre == null || genre.isEmpty() || genre.equals("All")) {
            return getAllBooks();
        }

        ArrayList<Book> filteredBooks = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getGenre().equals(genre)) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }

    public ArrayList<String> getAllGenres() {
        ArrayList<String> genres = new ArrayList<>();
        genres.add("All");

        for (Book book : bookList) {
            if (!genres.contains(book.getGenre())) {
                genres.add(book.getGenre());
            }
        }

        return genres;
    }

    public ArrayList<Book> getLikedBooks() {
        ArrayList<Book> likedBooks = new ArrayList<>();
        for (Book book : bookList) {
            if (book.isLiked()) {
                likedBooks.add(book);
            }
        }

        return likedBooks;
    }
}