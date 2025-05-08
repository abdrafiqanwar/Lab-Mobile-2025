package com.example.tp4;

import com.example.tp4.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class DataDummy {

    public List<Book> getBooks() {
       List<Book> bookList = new ArrayList<>();

        if (bookList.isEmpty()) {
            bookList.add(new Book("The Alchemist", "Paulo Coelho", 1988, "A journey of self-discovery and destiny.", R.drawable.the_alchemist, "Fiction", 4.5f));
            bookList.add(new Book("Atomic Habits", "James Clear", 2018, "An easy & proven way to build good habits and break bad ones.", R.drawable.atomic, "Self-help", 4.8f));
            bookList.add(new Book("1984", "George Orwell", 1949, "A dystopian novel about totalitarian regime and surveillance.", R.drawable.nineteen, "Dystopian", 4.7f));
            bookList.add(new Book("Sapiens", "Yuval Noah Harari", 2011, "A brief history of humankind and the evolution of civilization.", R.drawable.sapiens, "History", 4.6f));
            bookList.add(new Book("Becoming", "Michelle Obama", 2018, "Memoir of the former First Lady of the United States.", R.drawable.becoming, "Biography", 4.9f));
            bookList.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997, "The first book of a magical adventure at Hogwarts.", R.drawable.harry, "Fantasy", 4.9f));
            bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960, "A novel about racial injustice in the Deep South.", R.drawable.mockingbird, "Fiction", 4.8f));
            bookList.add(new Book("Educated", "Tara Westover", 2018, "A memoir about growing up in a survivalist family.", R.drawable.educated, "Biography", 4.7f));
            bookList.add(new Book("Brief Answers to the Big Questions", "Stephen Hawking", 2018, "Scientific insights into life and the universe.", R.drawable.brief, "Science", 4.6f));
            bookList.add(new Book("The Silent Patient", "Alex Michaelides", 2019, "A psychological thriller about a woman's act of violence.", R.drawable.silent, "Horror", 4.3f));
            bookList.add(new Book("The Notebook", "Nicholas Sparks", 1996, "A heartwarming love story across decades.", R.drawable.notebook, "Romance", 4.5f));
            bookList.add(new Book("The Martian", "Andy Weir", 2011, "An astronaut's struggle to survive on Mars.", R.drawable.martian, "Science", 4.7f));
            bookList.add(new Book("Dracula", "Bram Stoker", 1897, "A horror classic that introduced Count Dracula.", R.drawable.dracula, "Horror", 4.2f));
            bookList.add(new Book("The Hobbit", "J.R.R. Tolkien", 1937, "A fantasy adventure before the Lord of the Rings.", R.drawable.hobbit, "Fantasy", 4.8f));
            bookList.add(new Book("Pride and Prejudice", "Jane Austen", 1813, "A romantic novel about manners and marriage.", R.drawable.pride, "Romance", 4.6f));
            bookList.add(new Book("The Diary of a Young Girl", "Anne Frank", 1947, "A real-life diary of a Jewish girl in hiding.", R.drawable.diary, "Non-fiction", 4.9f));
            bookList.add(new Book("The Subtle Art of Not Giving a F*ck", "Mark Manson", 2016, "A counterintuitive approach to living a good life.", R.drawable.art, "Non-fiction", 4.3f));
            bookList.add(new Book("Dune", "Frank Herbert", 1965, "An epic sci-fi story of politics and survival.", R.drawable.dune, "Science", 4.6f));
            bookList.add(new Book("Frankenstein", "Mary Shelley", 1818, "A tale of science gone wrong and its consequences.", R.drawable.mary, "Horror", 4.1f));
            bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "A novel about the American dream and lost love.", R.drawable.gatsby, "Fiction", 4.4f));
        }

        return bookList;
    }


}
