package com.example.tp04fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp04fragment.Adapter.BookAdapter;
import com.example.tp04fragment.Data.DataBook;
import com.example.tp04fragment.Models.Book;
import com.example.tp04fragment.Repository.BookRepository;
import com.example.tp04fragment.databinding.FragmentDetailBookBinding;
import com.example.tp04fragment.databinding.FragmentFavoritesBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private BookAdapter bookAdapter;
    private ArrayList<Book> favoriteBooks = new ArrayList<>();
    private Handler handler = new Handler();
    private Runnable runnable, onOpenRunnable;

    private ExecutorService onOpenExecutor;
    private Handler mainHandler;
    private static final int SEARCH_DELAY_MS = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((MainActivity) requireActivity()).showBottomNav();

        favoriteBooks = new ArrayList<>(BookRepository.getFavoriteBooks());

        bookAdapter = new BookAdapter(new ArrayList<>(), this::onBookClicked);
        binding.bookRecycler.setAdapter(bookAdapter);

        onOpenExecutor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        showLoading(true);

        onOpenExecutor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainHandler.post(() -> {
                showLoading(false);
                if (favoriteBooks.isEmpty()) {
                    binding.bookRecycler.setVisibility(View.GONE);
                    binding.emptyFavorites.setVisibility(View.VISIBLE);
                } else {
                    binding.bookRecycler.setVisibility(View.VISIBLE);
                    binding.emptyFavorites.setVisibility(View.GONE);
                    bookAdapter.updateBooks(new ArrayList<>(favoriteBooks));
                }
            });
        });
        

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        setupGenreButtons();

        return view;
    }

    private void onBookClicked(Book book) {
        DetailBookFragment fragment = new DetailBookFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("book", book);
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void filter(String text) {
        ArrayList<Book> filteredList = new ArrayList<>();
        for (Book book : favoriteBooks) {
            if (book.getJudul().toLowerCase().contains(text.toLowerCase()) ||
                    book.getPenulis().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(book);
            }
        }

        bookAdapter.filterList(filteredList);
        if (filteredList.isEmpty()) {
            binding.emptyFavorites.setVisibility(View.VISIBLE);
        } else {
            binding.emptyFavorites.setVisibility(View.GONE);
        }
    }

    private void setupGenreButtons() {
//        binding.allGenres.setOnClickListener(v -> {
//            changeButtonStyle(binding.allGenres, true);
//            changeButtonStyle(binding.romance, false);
//            changeButtonStyle(binding.fantasy, false);
//            changeButtonStyle(binding.scifi, false);
//            changeButtonStyle(binding.thriller, false);
//            changeButtonStyle(binding.horror, false);
//            changeButtonStyle(binding.drama, false);
//            changeButtonStyle(binding.history, false);
//            changeButtonStyle(binding.comedy, false);
//            changeButtonStyle(binding.sliceOfLife, false);
//            filterBooksByGenre("All Genres");
//        });
//
//        binding.romance.setOnClickListener(v -> {
//            changeButtonStyle(binding.allGenres, false);
//            changeButtonStyle(binding.romance, true);
//            changeButtonStyle(binding.fantasy, false);
//            changeButtonStyle(binding.scifi, false);
//            changeButtonStyle(binding.thriller, false);
//            changeButtonStyle(binding.horror, false);
//            changeButtonStyle(binding.drama, false);
//            changeButtonStyle(binding.history, false);
//            changeButtonStyle(binding.comedy, false);
//            changeButtonStyle(binding.sliceOfLife, false);
//            filterBooksByGenre("Romance");
//        });
//
//        binding.fantasy.setOnClickListener(v -> {
//            changeButtonStyle(binding.allGenres, false);
//            changeButtonStyle(binding.romance, false);
//            changeButtonStyle(binding.fantasy, true);
//            changeButtonStyle(binding.scifi, false);
//            changeButtonStyle(binding.thriller, false);
//            changeButtonStyle(binding.horror, false);
//            changeButtonStyle(binding.drama, false);
//            changeButtonStyle(binding.history, false);
//            changeButtonStyle(binding.comedy, false);
//            changeButtonStyle(binding.sliceOfLife, false);
//            filterBooksByGenre("Fantasy");
//        });
//
//        binding.scifi.setOnClickListener(v -> {
//            changeButtonStyle(binding.allGenres, false);
//            changeButtonStyle(binding.romance, false);
//            changeButtonStyle(binding.fantasy, false);
//            changeButtonStyle(binding.scifi, true);
//            changeButtonStyle(binding.thriller, false);
//            changeButtonStyle(binding.horror, false);
//            changeButtonStyle(binding.drama, false);
//            changeButtonStyle(binding.history, false);
//            changeButtonStyle(binding.comedy, false);
//            changeButtonStyle(binding.sliceOfLife, false);
//            filterBooksByGenre("Sci-Fi");
//        });
//
//        binding.thriller.setOnClickListener(v -> {
//            changeButtonStyle(binding.allGenres, false);
//            changeButtonStyle(binding.romance, false);
//            changeButtonStyle(binding.fantasy, false);
//            changeButtonStyle(binding.scifi, false);
//            changeButtonStyle(binding.thriller, true);
//            changeButtonStyle(binding.horror, false);
//            changeButtonStyle(binding.drama, false);
//            changeButtonStyle(binding.history, false);
//            changeButtonStyle(binding.comedy, false);
//            changeButtonStyle(binding.sliceOfLife, false);
//            filterBooksByGenre("Thriller");
//        });
//
//        binding.horror.setOnClickListener(v -> {
//            changeButtonStyle(binding.allGenres, false);
//            changeButtonStyle(binding.romance, false);
//            changeButtonStyle(binding.fantasy, false);
//            changeButtonStyle(binding.scifi, false);
//            changeButtonStyle(binding.thriller, false);
//            changeButtonStyle(binding.horror, true);
//            changeButtonStyle(binding.drama, false);
//            changeButtonStyle(binding.history, false);
//            changeButtonStyle(binding.comedy, false);
//            changeButtonStyle(binding.sliceOfLife, false);
//            filterBooksByGenre("Horror");
//        });
//
//        binding.drama.setOnClickListener(v -> {
//            changeButtonStyle(binding.allGenres, false);
//            changeButtonStyle(binding.romance, false);
//            changeButtonStyle(binding.fantasy, false);
//            changeButtonStyle(binding.scifi, false);
//            changeButtonStyle(binding.thriller, false);
//            changeButtonStyle(binding.horror, false);
//            changeButtonStyle(binding.drama, true);
//            changeButtonStyle(binding.history, false);
//            changeButtonStyle(binding.comedy, false);
//            changeButtonStyle(binding.sliceOfLife, false);
//            filterBooksByGenre("Drama");
//        });
//
//        binding.history.setOnClickListener(v -> {
//            changeButtonStyle(binding.allGenres, false);
//            changeButtonStyle(binding.romance, false);
//            changeButtonStyle(binding.fantasy, false);
//            changeButtonStyle(binding.scifi, false);
//            changeButtonStyle(binding.thriller, false);
//            changeButtonStyle(binding.horror, false);
//            changeButtonStyle(binding.drama, false);
//            changeButtonStyle(binding.history, true);
//            changeButtonStyle(binding.comedy, false);
//            changeButtonStyle(binding.sliceOfLife, false);
//            filterBooksByGenre("History");
//        });
//
//        binding.comedy.setOnClickListener(v -> {
//            changeButtonStyle(binding.allGenres, false);
//            changeButtonStyle(binding.romance, false);
//            changeButtonStyle(binding.fantasy, false);
//            changeButtonStyle(binding.scifi, false);
//            changeButtonStyle(binding.thriller, false);
//            changeButtonStyle(binding.horror, false);
//            changeButtonStyle(binding.drama, false);
//            changeButtonStyle(binding.history, false);
//            changeButtonStyle(binding.comedy, true);
//            changeButtonStyle(binding.sliceOfLife, false);
//            filterBooksByGenre("Comedy");
//        });
//
//        binding.sliceOfLife.setOnClickListener(v -> {
//            changeButtonStyle(binding.allGenres, false);
//            changeButtonStyle(binding.romance, false);
//            changeButtonStyle(binding.fantasy, false);
//            changeButtonStyle(binding.scifi, false);
//            changeButtonStyle(binding.thriller, false);
//            changeButtonStyle(binding.horror, false);
//            changeButtonStyle(binding.drama, false);
//            changeButtonStyle(binding.history, false);
//            changeButtonStyle(binding.comedy, false);
//            changeButtonStyle(binding.sliceOfLife, true);
//            filterBooksByGenre("Slice of Life");
//        });

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
        ArrayList<Book> filteredBooks;
        if (genre.equals("All Genres")) {
            filteredBooks = new ArrayList<>(favoriteBooks);
        } else {
            filteredBooks = new ArrayList<>();
            for (Book book : favoriteBooks) {
                if (book.getGenre().equals(genre)) {
                    filteredBooks.add(book);
                }
            }
        }

        bookAdapter.updateBooks(filteredBooks);
        if (filteredBooks.isEmpty()) {
            binding.emptyFavorites.setVisibility(View.VISIBLE);
        } else {
            binding.emptyFavorites.setVisibility(View.GONE);
        }
    }

    private void loadFavoriteBooks() {
        favoriteBooks.addAll(DataBook.books);
        bookAdapter.notifyDataSetChanged();

        if (favoriteBooks.isEmpty()) {
            binding.emptyFavorites.setVisibility(View.VISIBLE);
        } else {
            binding.emptyFavorites.setVisibility(View.GONE);
        }
    }

    private void showLoading(boolean isLoading) {
        binding.loadingProgress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.bookRecycler.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        binding.emptyFavorites.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }
}