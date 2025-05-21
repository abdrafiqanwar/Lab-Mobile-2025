package com.example.praktikum_4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> favoriteBooks;
    private ProgressBar progressBar;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.favorites_list);
        progressBar = view.findViewById(R.id.progress_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar.setVisibility(View.VISIBLE); // Tampilkan loading
        executor.execute(() -> {
            try {
                // Tambahkan delay agar loading terlihat
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> loadedFavorites = new ArrayList<>(BookData.favoriteBooks);

            mainHandler.post(() -> {
                favoriteBooks = loadedFavorites;
                adapter = new BookAdapter(favoriteBooks, book -> openBookDetail(book));
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE); // Sembunyikan loading
            });
        });

        return view;
    }

    private void openBookDetail(Book book) {
        Bundle bundle = new Bundle();
        bundle.putString("title", book.title);
        bundle.putString("author", book.author);
        bundle.putString("year", book.year);
        bundle.putString("blurb", book.blurb);
        bundle.putInt("coverResId", book.coverResId);
        bundle.putBoolean("isLiked", book.isLiked);

        BookDetailFragment detailFragment = new BookDetailFragment();
        detailFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
