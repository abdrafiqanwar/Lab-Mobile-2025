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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.projeku.tuprak4.book.Book;
import com.projeku.tuprak4.book.BookAdapter;
import com.projeku.tuprak4.book.BookDetail;
import com.projeku.tuprak4.book.DataBook;
import com.projeku.tuprak4.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private SearchView searchView;
    private ChipGroup genreChipGroup;
    private ArrayList<Book> allBooks;
    private ArrayList<Book> filteredBooks = new ArrayList<>();
    private String currentSearchQuery = "";
    private String selectedGenre = "";
    private ProgressBar progressBar;
    private TextView emptyView;
    private ExecutorService executorService;
    private Handler mainHandler; // Handler untuk memperbarui UI di thread utama

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Menginflate layout fragment_home.xml ke dalam view
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = view.findViewById(R.id.search_view);
        genreChipGroup = view.findViewById(R.id.genre_chip_group);
        recyclerView = view.findViewById(R.id.recycler_view_books);
        progressBar = view.findViewById(R.id.progress_bar);
        emptyView = view.findViewById(R.id.empty_view);

        // Eksecutor service untuk tugas di background
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        allBooks = DataBook.bookList;
        filteredBooks.addAll(allBooks); // Menyalin seluruh buku ke dalam daftar filteredBooks

        // Setup genre chips dan listener
        setupGenreChips();

        // Setup listener untuk pencarian buku
        setupSearchView();

        // Setup RecyclerView untuk menampilkan buku
        setupRecyclerView();

        return view; // Mengembalikan view fragment
    }

    private void setupGenreChips() {
        // Membuat chip "All" sebagai opsi genre default
        Chip allChip = new Chip(requireContext());
        allChip.setText("All");
        allChip.setCheckable(true);
        allChip.setChecked(true);
        allChip.setClickable(true);
        genreChipGroup.addView(allChip);

        // Daftar genre dari daftar buku
        Set<String> genres = new HashSet<>();
        for (Book book : allBooks) {
            genres.add(book.genre); // Menambahkan genre unik ke dalam set
        }

        // Membuat chip untuk setiap genre yang unik
        for (String genre : genres) {
            Chip chip = new Chip(requireContext());
            chip.setText(genre);
            chip.setCheckable(true);
            chip.setClickable(true);
            genreChipGroup.addView(chip); // Menambahkan chip genre ke dalam ChipGroup
        }

        // Listener untuk perubahan status chip yang dipilih
        genreChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            ArrayList<String> selectedGenres = new ArrayList<>();
            for (int id : checkedIds) {
                Chip selectedChip = group.findViewById(id);
                if (selectedChip != null) {
                    String genre = selectedChip.getText().toString();
                    if (!genre.equals("All")) {
                        selectedGenres.add(genre); // Menambahkan genre yang dipilih
                    }
                }
            }

            // Jika tidak ada genre yang dipilih, pilih chip "All" secara otomatis
            if (selectedGenres.isEmpty()) {
                allChip.setChecked(true);
            } else {
                allChip.setChecked(false);
            }

            // Gabungkan genre yang dipilih sebagai string dan filter buku
            selectedGenre = String.join(",", selectedGenres);
            filterBooks();
        });
    }

    private void setupSearchView() {
        // Setup listener untuk perubahan teks pencarian
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Tidak ada aksi ketika tombol enter ditekan
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Menyimpan query pencarian dan memfilter buku
                currentSearchQuery = newText;
                filterBooks();
                return true;
            }
        });
    }

    private void setupRecyclerView() {
        // Membuat adapter dan menghubungkannya ke RecyclerView
        adapter = new BookAdapter(getContext(), filteredBooks, book -> {
            // Ketika item diklik, buka activity untuk melihat detail buku
            Intent intent = new Intent(getContext(), BookDetail.class);
            intent.putExtra("title", book.title);
            intent.putExtra("author", book.author);
            intent.putExtra("genre", book.genre);
            intent.putExtra("year", book.year);
            intent.putExtra("blurb", book.blurb);
            intent.putExtra("liked", book.isLiked);
            intent.putExtra("rating", book.rating);
            intent.putExtra("imageUri", book.imageUri);
            startActivity(intent); // Memulai activity detail buku
        });

        // Mengatur layout grid untuk RecyclerView dengan 2 kolom
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter); // Menetapkan adapter pada RecyclerView
    }

    private void filterBooks() {
        // Menampilkan progress bar sebelum memulai tugas filtering
        progressBar.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE); // Hide empty view during filtering

        // Menjalankan tugas filtering di background thread
        executorService.execute(() -> {
            // Membuat list kosong untuk menyimpan hasil filter
            ArrayList<Book> results = new ArrayList<>();

            // Simulasi delay untuk menunjukkan progress bar (untuk pengujian)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Melakukan filtering berdasarkan pencarian dan genre yang dipilih
            for (Book book : allBooks) {
                boolean matchesSearch = currentSearchQuery.isEmpty() ||
                        book.title.toLowerCase().contains(currentSearchQuery.toLowerCase()) ||
                        book.author.toLowerCase().contains(currentSearchQuery.toLowerCase());

                boolean matchesGenre = selectedGenre.isEmpty() ||
                        selectedGenre.contains(book.genre);

                if (matchesSearch && matchesGenre) {
                    results.add(book); // Menambahkan buku yang sesuai filter ke hasil
                }
            }

            // Update UI di thread utama
            mainHandler.post(() -> {
                filteredBooks.clear(); // Clear daftar filteredBooks sebelumnya
                filteredBooks.addAll(results); // Menambahkan hasil filter ke filteredBooks
                adapter.notifyDataSetChanged(); // Memberitahu adapter untuk memperbarui tampilan
                progressBar.setVisibility(View.GONE); // Menyembunyikan progress bar setelah selesai

                // Show empty view if no results found
                if (results.isEmpty()) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Memperbarui tampilan adapter ketika fragment kembali muncul
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
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