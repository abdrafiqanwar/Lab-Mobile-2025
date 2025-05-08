package com.example.tp4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tp4.Adapter.BookAdapter;
import com.example.tp4.Model.FavoriteStore;

import androidx.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavBookFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ProgressBar progressBar;
    private TextView emptyTextView;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_book, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_fav_books);
        adapter = new BookAdapter(getContext(), FavoriteStore.favoriteBooks, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progress_bar);
        emptyTextView = view.findViewById(R.id.emptyTextView);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        loadFavoritesData(); // Load data in background

        return view;
    }

    private void loadFavoritesData() {
        executor.execute(() -> {
            if (FavoriteStore.favoriteBooks.isEmpty()) {
                progressBar.setVisibility(View.GONE); // Hide progress bar
                recyclerView.setVisibility(View.GONE);
                emptyTextView.setVisibility(View.VISIBLE);
            } else {
                // Simulating a background data load (e.g., from a database or API)
                try {
                    Thread.sleep(1000);  // Simulating a delay in loading the data
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // After background task finishes, update the UI on the main thread
                handler.post(() -> {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged(); // Refresh the adapter
                    progressBar.setVisibility(View.GONE); // Hide progress bar
                });
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (FavoriteStore.favoriteBooks.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged(); // Refresh saat kembali
    }


}
