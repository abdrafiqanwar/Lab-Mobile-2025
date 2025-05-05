package com.example.tp4;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp4.Adapter.BookAdapter;
import com.example.tp4.Model.Book;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView rvAllBooks;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private String currentQuery = "";
    private String currentGenre = "Semua";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerViews
        rvAllBooks = view.findViewById(R.id.recycler_view_all_books);

        // Set LayoutManagers
        rvAllBooks.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Check if bookList is null, if it is, initialize it
        if (bookList == null) {
            bookList = new ArrayList<>();
        }

        // Retrieve book list from MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            bookList = mainActivity.getBookList();
        } else {
            Log.e("HomeFragment", "MainActivity is null");
        }

        // If bookList is empty, add sample data
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

        // Set up the adapters for the RecyclerViews
        bookAdapter = new BookAdapter(getContext(), bookList, false);
        rvAllBooks.setAdapter(bookAdapter);


        // Set up SearchView functionality
        SearchView searchView = view.findViewById(R.id.search_view);
        ChipGroup chipGroup = view.findViewById(R.id.genre_chip_group);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentQuery = query;
                bookAdapter.filter(currentQuery, currentGenre);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText;
                bookAdapter.filter(currentQuery, currentGenre);
                return true;
            }
        });

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            View child = chipGroup.getChildAt(i);
            if (child instanceof Chip) {
                Chip chip = (Chip) child;
                chip.setOnClickListener(v -> {
                    currentGenre = chip.getText().toString();
                    bookAdapter.filter(currentQuery, currentGenre);
                });
            }
        }



    }
}
