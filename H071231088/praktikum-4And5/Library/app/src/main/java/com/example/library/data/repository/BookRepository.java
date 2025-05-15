package com.example.library.data.repository;

import com.example.library.data.classjava.DummyBookData;
import com.example.library.data.classjava.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static BookRepository instance;
    private List<Book> books;

    private BookRepository() {
        books = DummyBookData.getDummyBooks();
    }

    public static synchronized BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void addBook(Book book) {
        // Pastikan book memiliki addedTimestamp
        if (book.getAddedTimestamp() == 0) {
            try {
                java.lang.reflect.Field timestampField = Book.class.getDeclaredField("addedTimestamp");
                timestampField.setAccessible(true);
                timestampField.setLong(book, System.currentTimeMillis());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        books.add(book);
    }
}