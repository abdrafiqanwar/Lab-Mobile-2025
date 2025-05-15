package com.example.library.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.library.data.classjava.FavBook;

import java.util.ArrayList;
import java.util.List;

public class FavBookRepository {
    private static final List<FavBook> favBooks = new ArrayList<>();
    private static final MutableLiveData<List<FavBook>> favBooksLiveData = new MutableLiveData<>();

    static {
        favBooksLiveData.setValue(new ArrayList<>(favBooks));
    }

    public static void add(FavBook book) {
        if (!isFavorite(book.getId())) {
            favBooks.add(book);
            favBooksLiveData.setValue(new ArrayList<>(favBooks));
        }
    }

    public static void removeById(int id) {
        favBooks.removeIf(book -> book.getId() == id);
        favBooksLiveData.setValue(new ArrayList<>(favBooks));
    }

    public static boolean isFavorite(int id) {
        return favBooks.stream().anyMatch(book -> book.getId() == id);
    }

    public static LiveData<List<FavBook>> getAll() {
        return favBooksLiveData;
    }
}