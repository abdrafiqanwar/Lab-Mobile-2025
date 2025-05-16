package com.example.tp4;

import static com.example.tp4.databook.BookDataManager.bookList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tp4.bookadapter.BookAdapter;
import com.example.tp4.databook.Book;
import com.example.tp4.databook.BookDataManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookDataManager bookDataManager;
    private SearchView searchView;
    private Spinner genreSpinner;
    private String currentGenre = "All";
    private String currentSearch = "";


    private ActivityResultLauncher<Intent> detailLauncher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bookDataManager = new BookDataManager();

        // 1. Inisialisasi ActivityResultLauncher dulu
        detailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Book updatedBook = result.getData().getParcelableExtra("UPDATED_BOOK");
                        if (updatedBook != null) {
                            updateBookFromDetail(updatedBook);
                        }
                    }
                });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAlpha(0f);
        recyclerView.animate().alpha(1f).setDuration(300).start();

        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Handler handler = new Handler(Looper.getMainLooper());
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            // Simulasi delay (opsional aja buat nampilin loading)
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

            ArrayList<Book> books = BookDataManager.getAllBooks();

            handler.post(() -> {
                bookAdapter = new BookAdapter(books, book -> {
                    Intent intent = new Intent(getContext(), DetailBookActivity.class);
                    intent.putExtra("BOOK", book);
                    detailLauncher.launch(intent);
                });
                recyclerView.setAdapter(bookAdapter);
                progressBar.setVisibility(View.GONE);

                filterBooks();
            });

        });


        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        genreSpinner = view.findViewById(R.id.genreSpinner);
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                bookDataManager.getAllGenres());
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
        genreSpinner.setOnItemSelectedListener(this);

        return view;
    }

    public void updateBookFromDetail(Book updatedBook) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId() == (updatedBook.getId())) {
                bookList.set(i, updatedBook);
                bookAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        filterBooks();
    }

    private void filterBooks() {
        if (bookAdapter == null) return;

        ProgressBar progressBar = requireView().findViewById(R.id.progressBar);
        RecyclerView recyclerView = requireView().findViewById(R.id.recyclerView);
        TextView tvEmpty = requireView().findViewById(R.id.tvEmptyMessage);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);

        Handler handler = new Handler(Looper.getMainLooper());
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                Thread.sleep(500);

                ArrayList<Book> searchResults = bookDataManager.searchBooks(currentSearch);
                ArrayList<Book> genreFiltered = bookDataManager.filterBooksByGenre(currentGenre);
                searchResults.retainAll(genreFiltered);

                handler.post(() -> {
                    progressBar.setVisibility(View.GONE);

                    if (searchResults.isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        bookAdapter.updateBooks(searchResults);

                        // Fade-in animasi opsional
                        recyclerView.setAlpha(0f);
                        recyclerView.animate().alpha(1f).setDuration(300).start();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentGenre = parent.getItemAtPosition(position).toString();
        filterBooks();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        currentGenre = "All";
        filterBooks();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        currentSearch = query;
        filterBooks();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        currentSearch = newText;
        filterBooks();
        return true;
    }
}
