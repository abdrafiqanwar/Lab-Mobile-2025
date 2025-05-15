package com.example.library.home.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.library.data.classjava.Book;
import com.example.library.data.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends ViewModel {
    private final MutableLiveData<List<Book>> bookList = new MutableLiveData<>();
    private final MutableLiveData<List<Book>> favoriteBookList = new MutableLiveData<>();
    private final BookRepository bookRepository;

    public BookViewModel() {
        bookRepository = BookRepository.getInstance();
        bookList.setValue(new ArrayList<>(bookRepository.getBooks()));
        favoriteBookList.setValue(new ArrayList<>());
    }

    public LiveData<List<Book>> getBookList() {
        return bookList;
    }

    public LiveData<List<Book>> getFavoriteBookList() {
        return favoriteBookList;
    }

    public void addBook(Book book) {
        bookRepository.addBook(book);
        bookList.setValue(new ArrayList<>(bookRepository.getBooks()));
    }

    public void toggleFavoriteBook(Book book) {
        List<Book> currentFavorites = favoriteBookList.getValue();
        if (currentFavorites == null) {
            currentFavorites = new ArrayList<>();
        }

        if (currentFavorites.contains(book)) {
            currentFavorites.remove(book);
        } else {
            currentFavorites.add(book);
        }

        favoriteBookList.setValue(currentFavorites);
    }

    public boolean isBookFavorite(Book book) {
        List<Book> currentFavorites = favoriteBookList.getValue();
        return currentFavorites != null && currentFavorites.contains(book);
    }
}