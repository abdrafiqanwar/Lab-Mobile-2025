package com.example.praktikum_6.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.praktikum_6.databinding.ActivityMainBinding;
import com.example.praktikum_6.data.network.ApiClient;
import com.example.praktikum_6.data.network.ApiService;
import com.example.praktikum_6.data.respon.characters.CharacterResponse;
import com.example.praktikum_6.ui.CharacterAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CharacterAdapter adapter;
    private int currentPage = 1;
    private boolean hasMoreData = true;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CharacterAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreClickListener(() -> {
            loadMoreCharacters();
        });

        // Set listener untuk tombol "Muat Ulang"
        binding.btnReload.setOnClickListener(v -> {
            currentPage = 1; // Reset halaman ke 1 saat muat ulang
            hasMoreData = true; // Anggap ada data lagi saat muat ulang
            fetchCharacters(currentPage);
        });

        fetchCharacters(currentPage);
    }

    // Fungsi untuk memeriksa koneksi jaringan
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private void fetchCharacters(int page) {
        if (isLoading) return; // Mencegah multiple request saat masih loading

        if (!isNetworkAvailable()) {
            showErrorLayout("Tidak ada koneksi internet. Silahkan muat ulang.");
            Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
            return;
        }

        isLoading = true;
        binding.progressBar.setVisibility(View.VISIBLE);
        hideErrorLayout(); // Sembunyikan pesan error saat mencoba memuat
        adapter.setShowLoadMore(false); // sembunyikan tombol sementara loading

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<CharacterResponse> call = apiService.getCharacters(page);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                isLoading = false;
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    CharacterResponse characterResponse = response.body();
                    if (characterResponse.getResults() != null && !characterResponse.getResults().isEmpty()) {
                        if (page == 1) {
                            adapter.setCharacterList(characterResponse.getResults());
                        } else {
                            adapter.addCharacters(characterResponse.getResults());
                        }
                        currentPage++;
                        hasMoreData = characterResponse.getInfo().getNext() != null;
                        adapter.setShowLoadMore(hasMoreData);

                        if (!hasMoreData) {
                            Toast.makeText(MainActivity.this, "Tidak ada data lagi", Toast.LENGTH_SHORT).show();
                        }
                        showContentLayout(); // Tampilkan RecyclerView jika berhasil
                    } else {
                        hasMoreData = false;
                        showErrorLayout("Tidak ada data ditemukan. Silahkan muat ulang.");
                        Toast.makeText(MainActivity.this, "Tidak ada data ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showErrorLayout("Gagal memuat data: " + response.message() + ". Silahkan muat ulang.");
                    Toast.makeText(MainActivity.this, "Gagal memuat data: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                isLoading = false;
                binding.progressBar.setVisibility(View.GONE);
                adapter.setShowLoadMore(hasMoreData); // Tetap tampilkan load more jika ada data sebelumnya
                showErrorLayout("Kesalahan jaringan: " + t.getMessage() + ". Silahkan muat ulang.");
                Toast.makeText(MainActivity.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadMoreCharacters() {
        fetchCharacters(currentPage);
    }

    // Metode untuk menampilkan tata letak error
    private void showErrorLayout(String message) {
        binding.recyclerView.setVisibility(View.GONE);
        binding.layoutError.setVisibility(View.VISIBLE);
        binding.tvErrorMessage.setText(message);
    }

    // Metode untuk menyembunyikan tata letak error
    private void hideErrorLayout() {
        binding.layoutError.setVisibility(View.GONE);
    }

    // Metode untuk menampilkan konten (RecyclerView)
    private void showContentLayout() {
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.layoutError.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}