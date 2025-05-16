package com.example.tp4;

import static com.example.tp4.databook.BookDataManager.bookList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tp4.bookadapter.BookAdapter;
import com.example.tp4.databook.Book;
import com.example.tp4.databook.BookDataManager;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookDataManager bookDataManager;
    private TextView emptyView;
    private ActivityResultLauncher<Intent> detailLauncher;

    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        progressBar = view.findViewById(R.id.progressBarFavorites);

        bookDataManager = new BookDataManager();

        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        emptyView = view.findViewById(R.id.emptyFavoritesText);

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

        // Load favorite books
        loadFavoriteBooks();

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
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);

        new Thread(() -> {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Book> favoriteBooks = bookDataManager.getLikedBooks();

            requireActivity().runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);

                if (favoriteBooks.isEmpty()) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    if (bookAdapter == null) {
                        bookAdapter = new BookAdapter(favoriteBooks, book -> {
                            Intent intent = new Intent(getContext(), DetailBookActivity.class);
                            intent.putExtra("BOOK", book);
                            detailLauncher.launch(intent);
                        });
                        recyclerView.setAdapter(bookAdapter);
                    } else {
                        bookAdapter.updateBooks(favoriteBooks);
                    }

                    // Efek animasi fade-in dikit biar uwuw
                    recyclerView.setAlpha(0f);
                    recyclerView.animate().alpha(1f).setDuration(300).start();
                }
            });
        }).start();
    }


}