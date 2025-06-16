package com.example.praktikum_6.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.praktikum_6.R;
import com.example.praktikum_6.data.network.ApiClient;
import com.example.praktikum_6.data.network.ApiService;
import com.example.praktikum_6.data.respon.characters.Character; // Penting: import kelas Character Anda
import com.example.praktikum_6.databinding.ActivityDetailBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private int characterId = -1; // Untuk menyimpan ID karakter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ambil ID karakter dari Intent
        characterId = getIntent().getIntExtra("character_id", -1);

        // Jika ID tidak valid, tampilkan error dan kembali
        if (characterId == -1) {
            showDetailErrorLayout("ID karakter tidak valid.");
            Toast.makeText(this, "ID karakter tidak ditemukan.", Toast.LENGTH_SHORT).show();
            binding.btnDetailReload.setOnClickListener(v -> finish()); // Kembali saja jika ID tidak valid
            return;
        }

        // Set listener untuk tombol "Coba Lagi" di DetailActivity
        binding.btnDetailReload.setOnClickListener(v -> {
            fetchCharacterDetails(characterId); // Panggil ulang untuk memuat detail
        });

        // Panggil fungsi untuk memuat detail karakter
        fetchCharacterDetails(characterId);

        binding.btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
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

    private void fetchCharacterDetails(int id) {
        if (!isNetworkAvailable()) {
            showDetailErrorLayout("Tidak ada koneksi internet. Silahkan coba lagi.");
            Toast.makeText(this, "Tidak ada koneksi internet.", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progressBarDetail.setVisibility(View.VISIBLE); // Tampilkan progress bar
        hideDetailErrorLayout(); // Sembunyikan pesan error
        hideDetailContentLayout(); // Sembunyikan konten sebelumnya

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // Pastikan menggunakan Character dari package Anda
        Call<com.example.praktikum_6.data.respon.characters.Character> call = apiService.getCharacterById(id);

        call.enqueue(new Callback<com.example.praktikum_6.data.respon.characters.Character>() {
            @Override
            public void onResponse(Call<com.example.praktikum_6.data.respon.characters.Character> call, Response<com.example.praktikum_6.data.respon.characters.Character> response) {
                binding.progressBarDetail.setVisibility(View.GONE); // Sembunyikan progress bar

                if (response.isSuccessful() && response.body() != null) {
                    Character character = response.body();
                    displayCharacterDetails(character);
                    showDetailContentLayout(); // Tampilkan konten detail
                } else {
                    showDetailErrorLayout("Gagal memuat detail: " + response.message() + ". Silahkan coba lagi.");
                    Toast.makeText(DetailActivity.this, "Gagal memuat detail karakter: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.praktikum_6.data.respon.characters.Character> call, Throwable t) {
                binding.progressBarDetail.setVisibility(View.GONE); // Sembunyikan progress bar
                showDetailErrorLayout("Kesalahan jaringan: " + t.getMessage() + ". Silahkan coba lagi.");
                Toast.makeText(DetailActivity.this, "Kesalahan jaringan saat memuat detail: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayCharacterDetails(Character character) {
        // Set data ke view dari objek Character yang baru dimuat
        binding.tvName.setText(character.name != null ? character.name : "Tidak Diketahui");
        binding.tvStatus.setText(character.status != null ? character.status : "Tidak Diketahui");
        binding.tvSpesies.setText(character.species != null ? character.species : "Tidak Diketahui");
        binding.tvOrigine.setText(character.origin != null && character.origin.name != null ? character.origin.name : "Tidak Diketahui");
        binding.tvLocation.setText(character.location != null && character.location.name != null ? character.location.name : "Tidak Diketahui");
        binding.tvType.setText(character.type != null && !character.type.isEmpty() ? character.type : "Tidak Diketahui");
        binding.tvGender.setText(character.gender != null ? character.gender : "Tidak Diketahui");
        // Periksa apakah episode null sebelum mengakses length
        binding.tvEpisodeCount.setText(character.episode != null ? String.valueOf(character.episode.length) : "0");
        binding.tvCreated.setText(character.created != null ? character.created : "Tidak Diketahui");

        Glide.with(this)
                .load(character.image)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(binding.imgCharacter);
    }

    // Metode untuk menampilkan tata letak error di detail
    private void showDetailErrorLayout(String message) {
        binding.detailContentLayout.setVisibility(View.GONE);
        binding.btnBack.setVisibility(View.GONE); // Sembunyikan tombol kembali saat error
        binding.layoutDetailError.setVisibility(View.VISIBLE);
        binding.tvDetailErrorMessage.setText(message);
    }

    // Metode untuk menyembunyikan tata letak error di detail
    private void hideDetailErrorLayout() {
        binding.layoutDetailError.setVisibility(View.GONE);
    }

    // Metode untuk menampilkan konten detail
    private void showDetailContentLayout() {
        binding.detailContentLayout.setVisibility(View.VISIBLE);
        binding.btnBack.setVisibility(View.VISIBLE); // Tampilkan tombol kembali
    }

    // Metode untuk menyembunyikan konten detail
    private void hideDetailContentLayout() {
        binding.detailContentLayout.setVisibility(View.GONE);
    }
}