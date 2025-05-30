package com.example.praktikum6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum6.adapters.CharacterAdapter;
import com.example.praktikum6.models.CharacterResponse;
import com.example.praktikum6.models.Characters;
import com.example.praktikum6.networks.ApiConfig;
import com.example.praktikum6.networks.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    // Deklaerasikan komponen UI
    private RecyclerView rvCharacters;
    private CharacterAdapter adapter;           // Adapter untuk menghubungkan data ke RecyclerView
    private ProgressBar progressBar;
    private Button loadMoreButton;
    private LinearLayout noInternetLayout;
    private Button retryButton;


    // Variabel kontrol dan data
    private int currentPage = 1;
    private boolean isLoading = false;
    private List<Characters> allCharacters = new ArrayList<>();  // List menyimpan semua karakter yang diambil
    private boolean isLastPage = false;           // Penanda sudah sampai halaman terakhir atau belum

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hubungkan variabel dengan view XML
        rvCharacters = findViewById(R.id.rv_characters);
        progressBar = findViewById(R.id.progressBar);
        loadMoreButton = findViewById(R.id.loadMoreButton);
        noInternetLayout = findViewById(R.id.noInternetLayout);
        retryButton = findViewById(R.id.retryButton);

        // Inisialisasi adapter dan layout manager RecyclerView
        adapter = new CharacterAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCharacters.setLayoutManager(layoutManager);
        rvCharacters.setAdapter(adapter);

        // tombol Load More
        loadMoreButton.setOnClickListener(v -> {
            if (!isLoading && !isLastPage) {
                currentPage++;           // Naikkan nomor halaman
                getCharacters(currentPage); // Panggil API untuk halaman baru
                loadMoreButton.setVisibility(View.GONE);
            }
        });


        retryButton.setOnClickListener(v -> {
            hideErrorView();
            getCharacters(currentPage);
        });

        // scroll RecyclerView untuk menampilkan tombol Load More saat mencapai bawah list
        rvCharacters.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition(); // Posisi item  yang terlihat

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0 && totalItemCount > 0) {
                        loadMoreButton.setVisibility(View.VISIBLE); // Tampilkan tombol Load More
                    } else {
                        loadMoreButton.setVisibility(View.GONE);
                    }
                }
            }
        });

        // Ambil data karakter dari API
        getCharacters(currentPage);
    }

    // Fungsi menampilkan layout error
    private void showErrorView() {
        rvCharacters.setVisibility(View.GONE);
        loadMoreButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
    }

    // Fungsi menyembunyikan layout error dan menampilkan list karakter
    private void hideErrorView() {
        noInternetLayout.setVisibility(View.GONE);
        rvCharacters.setVisibility(View.VISIBLE);
    }

    // Fungsi utama untuk mengambil data karakter dari API berdasarkan halaman
    private void getCharacters(int page) {
        showLoading(true);
        isLoading = true; //loading
        loadMoreButton.setVisibility(View.GONE);

        ApiService apiService = ApiConfig.getApiService();
        Call<CharacterResponse> call = apiService.getCharacters(page); // Request ke endpoint karakter dengan halaman

        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                showLoading(false);
                isLoading = false;

                if (response.isSuccessful() && response.body() != null) {
                    hideErrorView();

                    List<Characters> newCharacters = response.body().getResults(); // Ambil list karakter baru

                    allCharacters.addAll(newCharacters);

                    adapter.setCharactersList(allCharacters);

                    if (response.body().getInfo() != null &&
                            response.body().getInfo().getNext() == null) {
                        isLastPage = true;
                        loadMoreButton.setVisibility(View.GONE); // Sembunyikan tombol load more jika sudah terakhir
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    showErrorView();   // Tampilkan error view jika gagal load data
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                showLoading(false);   // Sembunyikan progress bar
                isLoading = false;    // Tandai loading selesai
                showErrorView();      // Tampilkan layout error jika gagal koneksi/API error
            }
        });
    }

    // Fungsi untuk menampilkan dan menyembunyikan progress bar loading
    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
