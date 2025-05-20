package com.projeku.tuprak4.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeku.tuprak4.book.Book;
import com.projeku.tuprak4.book.BookAdapter;
import com.projeku.tuprak4.book.BookDetail;
import com.projeku.tuprak4.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView; // RecyclerView untuk menampilkan daftar buku
    private BookAdapter adapter; // Adapter untuk menghubungkan data buku dengan RecyclerView
    private ArrayList<Book> favoriteBooks; // List buku favorit
    private TextView noFav; // TextView untuk menampilkan pesan "No Favorites"
    private ProgressBar progressBar; // ProgressBar untuk menampilkan loading
    private ExecutorService executorService; // Executor untuk menjalankan tugas di background
    private Handler mainHandler; // Handler untuk melakukan update UI di thread utama

    public FavoritesFragment() {
        // Konstruktor kosong, wajib ada untuk Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Menginflate layout fragment_favorites.xml ke dalam view
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Menyambungkan elemen UI dengan variabel
        recyclerView = view.findViewById(R.id.recycler_view_books);
        noFav = view.findViewById(R.id.empty_view);
        progressBar = view.findViewById(R.id.progress_bar);

        // Inisialisasi executor service untuk tugas background
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        // Inisialisasi list buku favorit yang kosong
        favoriteBooks = new ArrayList<>();

        // Setup RecyclerView untuk menampilkan daftar buku
        setupRecyclerView();

        // Memuat data buku favorit di background
        loadFavoriteBooks();

        return view; // Mengembalikan view fragment
    }

    private void setupRecyclerView() {
        // Membuat adapter untuk RecyclerView dengan listener ketika item buku diklik
        adapter = new BookAdapter(getContext(), favoriteBooks, book -> {
            // Ketika item diklik, buka detail buku
            Intent intent = new Intent(getContext(), BookDetail.class);
            // Mengirim data buku ke activity detail
            intent.putExtra("title", book.title);
            intent.putExtra("author", book.author);
            intent.putExtra("genre", book.genre);
            intent.putExtra("year", book.year);
            intent.putExtra("blurb", book.blurb);
            intent.putExtra("liked", book.isLiked);
            intent.putExtra("rating", book.rating);
            intent.putExtra("imageUri", book.imageUri);
            intent.putExtra("review", book.review);
            startActivity(intent); // Memulai activity detail buku
        });

        // Mengatur layout grid dengan 2 kolom untuk menampilkan buku
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter); // Menetapkan adapter pada RecyclerView
    }

    private void loadFavoriteBooks() {
        // Menampilkan progress bar dan menyembunyikan tampilan lainnya
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noFav.setVisibility(View.GONE);

        // Menjalankan tugas di thread background menggunakan executor
        executorService.execute(() -> {
            // Membuat list untuk menyimpan buku favorit
            ArrayList<Book> results = new ArrayList<>();

            // Filter buku yang difavoritkan dari DataBook.bookList
            for (Book book : com.projeku.tuprak4.book.DataBook.bookList) {
                if (book.isLiked) {
                    results.add(book);
                }
            }

            // Menambahkan delay simulasi agar progress bar terlihat (untuk testing)
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Mengupdate UI di thread utama setelah data diproses di background
            mainHandler.post(() -> {
                // Clear dan update daftar buku favorit
                favoriteBooks.clear();
                favoriteBooks.addAll(results);
                adapter.notifyDataSetChanged(); // Memberitahu adapter untuk memperbarui tampilan

                // Menyembunyikan progress bar
                progressBar.setVisibility(View.GONE);

                // Menampilkan RecyclerView atau pesan "No Favorites" jika tidak ada buku favorit
                if (favoriteBooks.isEmpty()) {
                    noFav.setVisibility(View.VISIBLE); // Tampilkan pesan jika tidak ada buku favorit
                    recyclerView.setVisibility(View.GONE);
                } else {
                    noFav.setVisibility(View.GONE); // Sembunyikan pesan "No Favorites"
                    recyclerView.setVisibility(View.VISIBLE); // Tampilkan RecyclerView
                }
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Memuat ulang data buku favorit saat fragment kembali muncul
        loadFavoriteBooks();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Menutup executor service untuk membersihkan sumber daya
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
