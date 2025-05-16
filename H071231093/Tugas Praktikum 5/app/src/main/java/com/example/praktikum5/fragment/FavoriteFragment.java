package com.example.praktikum5.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView; // <-- Tambahkan import ini
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum5.R;
import com.example.praktikum5.adapter.BookAdapter;
import com.example.praktikum5.data.BookData;
import com.example.praktikum5.jclass.Book;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private ProgressBar progressBar;
    private View loadingContainer;
    private boolean isFragmentDestroyed = false;
    private RecyclerView recyclerView;    // <-- Jadikan variabel instance
    private TextView emptyViewFavorite; // <-- Tambahkan variabel untuk empty view

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFragmentDestroyed = true;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_favorite_books);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        emptyViewFavorite = view.findViewById(R.id.empty_view_favorite); // <-- Inisialisasi empty view

        progressBar = view.findViewById(R.id.progress_bar);
        loadingContainer = view.findViewById(R.id.loading_container);

        // Sembunyikan RecyclerView dan EmptyView pada awalnya
        recyclerView.setVisibility(View.GONE);
        emptyViewFavorite.setVisibility(View.GONE);
        loadingContainer.setVisibility(View.VISIBLE); // Tampilkan loading
        long startTime = System.currentTimeMillis(); //

        new Thread(() -> {
            List<Book> favoriteBooks = new ArrayList<>();
            for (Book book : BookData.getBooks()) { //
                if (book.isFavorite()) { //
                    favoriteBooks.add(book); //
                }
            }

            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(10); //
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int progress = i;
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (!isFragmentDestroyed) { //
                        progressBar.setProgress(progress); //
                        animateProgressBarColor(progress); //
                    }
                });
            }

            long elapsedTime = System.currentTimeMillis() - startTime; //
            long delay = Math.max(0, 1000 - elapsedTime); //

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (!isFragmentDestroyed) { //
                    loadingContainer.setVisibility(View.GONE); // Sembunyikan loading container
                    if (favoriteBooks.isEmpty()) {
                        emptyViewFavorite.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        emptyViewFavorite.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        BookAdapter adapter = new BookAdapter(getContext(), favoriteBooks, book -> { //
                            DetailBookFragment detailFragment = new DetailBookFragment(); //
                            Bundle bundle = new Bundle(); //
                            bundle.putSerializable("selectedBook", book); //
                            detailFragment.setArguments(bundle); //

                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction(); //
                            transaction.replace(R.id.fragment_container, detailFragment); //
                            transaction.addToBackStack(null); //
                            transaction.commit(); //
                        });
                        recyclerView.setAdapter(adapter); //
                    }
                }
            }, delay); //
        }).start();

        return view;
    }

    private void animateProgressBarColor(int progress) {
        if (getContext() == null || isFragmentDestroyed) { // Tambahkan pengecekan context dan isFragmentDestroyed
            return;
        }
        int startColor = ContextCompat.getColor(requireContext(), android.R.color.black); //
        int endColor = ContextCompat.getColor(requireContext(), android.R.color.white); //

        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor); //
        colorAnimator.addUpdateListener(animation -> {
            if (!isFragmentDestroyed && progressBar != null && progressBar.getProgressDrawable() != null) { // Tambahkan pengecekan
                int color = (int) animation.getAnimatedValue(); //
                progressBar.getProgressDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN); //
            }
        });

        if (progress == 100) { //
            colorAnimator.start(); //
        }
    }
}