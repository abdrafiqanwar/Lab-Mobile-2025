package com.example.tp4.Model;

import com.example.tp4.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class FavoriteStore {
    public static List<Book> favoriteBooks = new ArrayList<>();

    public static boolean isFavorite(Book book) {
        return favoriteBooks.contains(book);
    }

    public static void toggleFavorite(Book book) {
        if (isFavorite(book)) {
            favoriteBooks.remove(book);
        } else {
            favoriteBooks.add(book);
        }
    }
}
