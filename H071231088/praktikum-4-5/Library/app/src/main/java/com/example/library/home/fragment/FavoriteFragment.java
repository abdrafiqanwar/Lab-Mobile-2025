package com.example.library.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.library.data.adapter.FavBookAdapter;
import com.example.library.data.repository.FavBookRepository;
import com.example.library.databinding.FragmentFavoriteBinding;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavBookAdapter adapter;

    // Handler untuk main thread (UI thread)
    private Handler mainHandler;

    // HandlerThread untuk background thread
    private HandlerThread backgroundThread;
    private Handler backgroundHandler;

    public FavoriteFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);

        // Inisialisasi adapter dan RecyclerView
        adapter = new FavBookAdapter(requireContext(), new ArrayList<>());
        binding.rvFavoriteBooks.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvFavoriteBooks.setAdapter(adapter);

        // Menampilkan progressBar
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.rvFavoriteBooks.setVisibility(View.GONE);
        binding.text1.setVisibility(View.GONE);

        mainHandler = new Handler(Looper.getMainLooper());

        backgroundThread = new HandlerThread("BackgroundThread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());

        FavBookRepository.getAll().observe(getViewLifecycleOwner(), favBooks -> {
            // Hapus task lama jika ada
            backgroundHandler.removeCallbacksAndMessages(null);

            backgroundHandler.post(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                if (isAdded() && binding != null) {
                    // Update UI di main thread
                    mainHandler.post(() -> {
                        binding.progressBar.setVisibility(View.GONE);
                        if (favBooks.isEmpty()) {
                            binding.text1.setVisibility(View.VISIBLE);
                        } else {
                            binding.rvFavoriteBooks.setVisibility(View.VISIBLE);
                        }
                        adapter.updateBooks(favBooks);
                    });
                }
            });
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        backgroundHandler.removeCallbacksAndMessages(null);
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop background thread secara permanen
        backgroundThread.quitSafely();
    }
}
