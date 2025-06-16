package com.example.library.home.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.library.data.classjava.Book;
import com.example.library.data.adapter.BookAdapter;
import com.example.library.home.ui.BookViewModel;
import com.example.library.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BookAdapter bookAdapter;
    private List<Book> filteredBookList;
    private Set<String> selectedGenres = new HashSet<>();
    private String currentSortOrder = "timestamp_desc"; // Default ke terbaru
    private BookViewModel viewModel;
    private final android.os.Handler handler = new android.os.Handler();
    private Runnable searchRunnable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);
        filteredBookList = new ArrayList<>();

        binding.rvBooks.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        bookAdapter = new BookAdapter(requireContext(), filteredBookList);
        binding.rvBooks.setAdapter(bookAdapter);

        viewModel.getBookList().observe(getViewLifecycleOwner(), books -> {
            filteredBookList.clear();
            filteredBookList.addAll(books);
            filterBooks(binding.etSearch.getText().toString(), selectedGenres);
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }
                binding.progressBar.setVisibility(View.VISIBLE);

                searchRunnable = () -> {
                    new Thread(() -> {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        requireActivity().runOnUiThread(() -> {
                            filterBooks(s.toString(), selectedGenres);
                            binding.progressBar.setVisibility(View.GONE);
                        });
                    }).start();
                };

                handler.postDelayed(searchRunnable, 0);
            }


            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.ivFilter.setOnClickListener(v -> showFilterMenu());
    }

    private void showFilterMenu() {
        PopupMenu popupMenu = new PopupMenu(requireContext(), binding.ivFilter);
        popupMenu.getMenu().add(0, 0, 0, "Genres");
        popupMenu.getMenu().add(0, 1, 1, "Urutkan Ascending (Judul)");
        popupMenu.getMenu().add(0, 2, 2, "Urutkan Descending (Judul)");
        popupMenu.getMenu().add(0, 3, 3, "Urutkan Terbaru");

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    showGenreSubMenu();
                    return true;
                case 1:
                    currentSortOrder = "title_asc";
                    filterBooks(binding.etSearch.getText().toString(), selectedGenres);
                    return true;
                case 2:
                    currentSortOrder = "title_desc";
                    filterBooks(binding.etSearch.getText().toString(), selectedGenres);
                    return true;
                case 3:
                    currentSortOrder = "timestamp_desc";
                    filterBooks(binding.etSearch.getText().toString(), selectedGenres);
                    return true;
                default:
                    return false;
            }
        });

        popupMenu.show();
    }

    private void showGenreSubMenu() {
        Set<String> allGenres = new HashSet<>();
        for (Book book : filteredBookList) {
            allGenres.addAll(book.getGenres());
        }

        PopupMenu subMenu = new PopupMenu(requireContext(), binding.ivFilter);
        int id = 0;
        for (String genre : allGenres) {
            subMenu.getMenu().add(0, id, id, genre).setCheckable(true).setChecked(selectedGenres.contains(genre));
            id++;
        }

        subMenu.setOnMenuItemClickListener(item -> {
            String genre = item.getTitle().toString();
            if (item.isChecked()) {
                selectedGenres.remove(genre);
                item.setChecked(false);
            } else {
                selectedGenres.add(genre);
                item.setChecked(true);
            }
            filterBooks(binding.etSearch.getText().toString(), selectedGenres);
            return true;
        });

        subMenu.show();
    }

    private void filterBooks(String query, Set<String> genres) {
        filteredBookList.clear();
        query = query.toLowerCase();

        List<Book> books = viewModel.getBookList().getValue();
        if (books != null) {
            for (Book book : books) {
                boolean matchesQuery = book.getTitle().toLowerCase().contains(query) || book.getAuthor().toLowerCase().contains(query);
                boolean matchesGenres = genres.isEmpty() || book.getGenres().stream().anyMatch(genres::contains);

                if (matchesQuery && matchesGenres) {
                    filteredBookList.add(book);
                }
            }
        }

        sortBooks(currentSortOrder);
    }

    private void sortBooks(String order) {
        switch (order) {
            case "title_asc":
                filteredBookList.sort((b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()));
                break;
            case "title_desc":
                filteredBookList.sort((b1, b2) -> b2.getTitle().compareToIgnoreCase(b1.getTitle()));
                break;
            case "timestamp_desc":
                filteredBookList.sort((b1, b2) -> Long.compare(b2.getAddedTimestamp(), b1.getAddedTimestamp()));
                break;
        }
        bookAdapter.updateBooks(filteredBookList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}