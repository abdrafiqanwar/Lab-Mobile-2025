package com.example.praktikum4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum4.R;
import com.example.praktikum4.adapter.BookAdapter;
import com.example.praktikum4.data.BookData;
import com.example.praktikum4.jclass.Book;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private BookAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView textNoFavorite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar_favorite_book);
        toolbar.setTitle("Favorite Book"); // Set judul toolbar

        progressBar = view.findViewById(R.id.progress_bar_favorite);
        recyclerView = view.findViewById(R.id.recycler_view_favorite_books);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        textNoFavorite = view.findViewById(R.id.text_no_favorite);

        // Memuat daftar favorit

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadFavoritesInBackground();

    }

    // Method untuk memuat buku favorit di background thread
    private void loadFavoritesInBackground() {
        progressBar.setVisibility(View.VISIBLE); // Tampilkan progress bar

        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulasi loading (bisa dihapus di production)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Dapatkan semua buku  yang favorit
            List<Book> allBooks = BookData.getBooks();
            List<Book> favoriteBooks = new ArrayList<>();

            for (Book book : allBooks) {
                if (book.isFavorite()) {
                    favoriteBooks.add(book);
                }
            }

            requireActivity().runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);

                if (favoriteBooks.isEmpty()) {
                    textNoFavorite.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    textNoFavorite.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    adapter = new BookAdapter(getContext(), favoriteBooks, book -> {
                        // Ketika buku diklik, buka DetailBookFragment
                        DetailBookFragment detailFragment = new DetailBookFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("selectedBook", book);
                        detailFragment.setArguments(bundle);

                        FragmentTransaction ft = requireActivity()
                                .getSupportFragmentManager()
                                .beginTransaction();
                        ft.replace(R.id.fragment_container, detailFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    });

                    recyclerView.setAdapter(adapter); // Set adapter ke RecyclerView
                }
            });
        }).start(); // Jalankan thread
    }
}