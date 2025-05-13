package com.example.tp04fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.tp04fragment.Data.DataBook;
import com.example.tp04fragment.Models.Book;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp04fragment.Adapter.BookAdapter;
import com.example.tp04fragment.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private Handler handler = new Handler();
    private Runnable runnable;
    private ArrayList<Book> allBooks = new ArrayList<>(DataBook.books);
    private BookAdapter adapter;
    
    // Threading components
    private ExecutorService searchExecutor;
    private Handler mainHandler;
    private static final int SEARCH_DELAY_MS = 1000;
    private Runnable searchRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize threading components
        searchExecutor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        ((MainActivity) requireActivity()).showBottomNav();
        allBooks = new ArrayList<>(DataBook.books);

        adapter = new BookAdapter(allBooks, book -> {
            DetailBookFragment fragment = new DetailBookFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("book", book);
            fragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        binding.bookRecycler.setAdapter(adapter);

        setupGenreButtons();
        setupSearchView();

        return view;
    }

    private void setupSearchView() {
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Cancel any pending search
                if (searchRunnable != null) {
                    mainHandler.removeCallbacks(searchRunnable);
                }

                showLoading(true);

                searchRunnable = () -> performSearch(newText);
                mainHandler.postDelayed(searchRunnable, SEARCH_DELAY_MS);
                return true;
            }
        });
    }

    private void performSearch(String text) {
        // Show loading indicator
        showLoading(true);
        
        searchExecutor.execute(() -> {
            ArrayList<Book> filteredList = new ArrayList<>();
            for (Book book : allBooks) {
                if (book.getJudul().toLowerCase().contains(text.toLowerCase()) ||
                        book.getPenulis().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(book);
                }
            }

            mainHandler.post(() -> {
                updateSearchResults(filteredList);
                showLoading(false);
            });
        });
    }
    
    private void showLoading(boolean isLoading) {
        binding.loadingProgress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
    
    private void updateSearchResults(ArrayList<Book> filteredList) {
        adapter.filterList(filteredList);
        binding.emptyGenre.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void setupGenreButtons() {
        Map<MaterialButton, String> genreButtons = new HashMap<>();
        genreButtons.put(binding.allGenres, "All Genres");
        genreButtons.put(binding.romance, "Romance");
        genreButtons.put(binding.fantasy, "Fantasy");
        genreButtons.put(binding.scifi, "Sci-Fi");
        genreButtons.put(binding.thriller, "Thriller");
        genreButtons.put(binding.horror, "Horror");
        genreButtons.put(binding.drama, "Drama");
        genreButtons.put(binding.history, "History");
        genreButtons.put(binding.comedy, "Comedy");
        genreButtons.put(binding.sliceOfLife, "Slice of Life");

        for (Map.Entry<MaterialButton, String> entry : genreButtons.entrySet()) {
            entry.getKey().setOnClickListener(v -> {
                for (MaterialButton button : genreButtons.keySet()) {
                    changeButtonStyle(button, button == entry.getKey());
                }
                filterBooksByGenre(entry.getValue());
            });
        }
    }

    private void changeButtonStyle(MaterialButton button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.abu));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.penulis));
        } else {
            button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.background));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.abu));
        }
    }

    private void filterBooksByGenre(String genre) {

        searchExecutor.execute(() -> {
            ArrayList<Book> filteredBooks;

            if (genre.equals("All Genres")) {
                filteredBooks = new ArrayList<>(allBooks);
            } else {
                filteredBooks = new ArrayList<>();
                for (Book book : allBooks) {
                    if (book.getGenre().equals(genre)) {
                        filteredBooks.add(book);
                    }
                }
            }
            
            mainHandler.post(() -> {
                adapter.updateBooks(filteredBooks);
                binding.emptyGenre.setVisibility(filteredBooks.isEmpty() ? View.VISIBLE : View.GONE);
                showLoading(false);
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up resources
        if (searchRunnable != null) {
            mainHandler.removeCallbacks(searchRunnable);
        }
        handler.removeCallbacks(runnable);
        searchExecutor.shutdown();
        binding = null;
    }
}
