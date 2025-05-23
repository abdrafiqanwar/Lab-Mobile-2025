package com.example.praktikum6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.praktikum6.models.Characters;
import com.example.praktikum6.networks.ApiConfig;
import com.example.praktikum6.networks.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ImageView ivCharacter;
    private TextView tvName, tvStatus, tvSpecies, tvGender, tvType;
    private ProgressBar progressBar;
    private LinearLayout detailContent;
    private LinearLayout noInternetLayout;
    private Button retryButton;
    private int characterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivCharacter = findViewById(R.id.iv_character_detail);
        tvName = findViewById(R.id.tv_name_detail);
        tvStatus = findViewById(R.id.tv_status_detail);
        tvSpecies = findViewById(R.id.tv_species_detail);
        tvGender = findViewById(R.id.tv_gender_detail);
        tvType = findViewById(R.id.tv_type_detail);
        progressBar = findViewById(R.id.progressBar_detail);
        detailContent = findViewById(R.id.detail_content);
        noInternetLayout = findViewById(R.id.noInternetLayout_detail);
        retryButton = findViewById(R.id.retryButton_detail);

        Characters character = (Characters) getIntent().getSerializableExtra("CHARACTER");
        if (character != null) {
            characterId = character.getId();

            retryButton.setOnClickListener(v -> {
                hideErrorView();
                loadCharacterDetails(characterId);
            });

            loadCharacterDetails(characterId);
        } else {
            Toast.makeText(this, "Character information not available", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadCharacterDetails(int id) {
        showLoading(true);
        hideErrorView();

        ApiService apiService = ApiConfig.getApiService();
        Call<Characters> call = apiService.getCharacterById(id);

        call.enqueue(new Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {
                showLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    Characters character = response.body();
                    displayCharacterDetails(character);
                } else {
                    showErrorView();
                    Toast.makeText(DetailActivity.this, "Failed to load character details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                showLoading(false);
                showErrorView();
            }
        });
    }

    private void displayCharacterDetails(Characters character) {
        tvName.setText(character.getName());
        tvStatus.setText("Status: " + character.getStatus());
        tvSpecies.setText("Species: " + character.getSpecies());
        tvGender.setText("Gender: " + character.getGender());
        tvType.setText("Type: " + (character.getType().isEmpty() ? "Unknown" : character.getType()));

        Glide.with(this)
                .load(character.getImage())
                .into(ivCharacter);

        detailContent.setVisibility(View.VISIBLE);
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void showErrorView() {
        detailContent.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
    }

    private void hideErrorView() {
        noInternetLayout.setVisibility(View.GONE);
    }
}