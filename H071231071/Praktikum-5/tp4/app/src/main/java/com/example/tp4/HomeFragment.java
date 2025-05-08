package com.example.tp4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {
    private RecyclerView rvAllBooks;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private TextView tvNoResult;
    private ProgressBar progressBar;
    private ExecutorService executor;
    private Handler handler;

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

        DataDummy dataDummy = new DataDummy();
        if (bookList.isEmpty()) {
            bookList = dataDummy.getBooks();
        }

        // Set up the adapters for the RecyclerViews
        bookAdapter = new BookAdapter(getContext(), bookList, false);
        rvAllBooks.setAdapter(bookAdapter);


        tvNoResult = view.findViewById(R.id.text_no_result);
        progressBar = view.findViewById(R.id.progress_bar);

        bookAdapter.setFilterResultListener(hasResult -> {
            if (hasResult) {
                rvAllBooks.setVisibility(View.VISIBLE);
                tvNoResult.setVisibility(View.GONE);
            } else {
                rvAllBooks.setVisibility(View.GONE);
                tvNoResult.setVisibility(View.VISIBLE);
            }
        });

        // Set up SearchView functionality
        SearchView searchView = view.findViewById(R.id.search_view);
        ChipGroup chipGroup = view.findViewById(R.id.genre_chip_group);

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                rvAllBooks.setVisibility(View.GONE);
                executor.execute(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(() -> {
                        currentQuery = query;
                        bookAdapter.filter(currentQuery, currentGenre);
                        progressBar.setVisibility(View.GONE);
                    });
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.VISIBLE);
                rvAllBooks.setVisibility(View.GONE);
                executor.execute(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(() -> {
                        currentQuery = newText;
                        bookAdapter.filter(currentQuery, currentGenre);
                        progressBar.setVisibility(View.GONE);
                    });
                });
                return true;
            }

        });

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            View child = chipGroup.getChildAt(i);
            if (child instanceof Chip) {
                Chip chip = (Chip) child;
                chip.setOnClickListener(v -> {
                    progressBar.setVisibility(View.VISIBLE);
                    rvAllBooks.setVisibility(View.GONE);
                    executor.execute(() -> {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.post(() -> {
                            currentGenre = chip.getText().toString();
                            bookAdapter.filter(currentQuery, currentGenre);
                            progressBar.setVisibility(View.GONE);
                        });
                    });
                });
            }
        }




    }
}
