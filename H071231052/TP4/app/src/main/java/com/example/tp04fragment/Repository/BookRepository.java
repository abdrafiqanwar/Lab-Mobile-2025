package com.example.tp04fragment.Repository;

import com.example.tp04fragment.Models.Book;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private static final List<Book> allBooks = new ArrayList<>();
    private static final List<Book> favoriteBooks = new ArrayList<>();
    public static void addBook(Book book) {
        allBooks.add(0, book);
    }

    public static List<Book> getAllBooks() {
        return allBooks;
    }

    public static void addToFavorites(Book book) {
        if (!favoriteBooks.contains(book)) {
            favoriteBooks.add(book);
        }
    }

    public static void removeFromFavorites(Book book) {
        favoriteBooks.remove(book);
    }

    public static List<Book> getFavoriteBooks() {
        return favoriteBooks;
    }
}