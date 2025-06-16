package com.projeku.pertemuan06;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView characterImageView;
    private TextView nameTextView, statusTextView, speciesTextView, genderTextView;
    private ProgressBar detailProgressBar;
    private ConstraintLayout errorLayout;
    private View infoCardView;
    private int characterId;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize views
        characterImageView = findViewById(R.id.characterImageView);
        nameTextView = findViewById(R.id.nameTextView);
        statusTextView = findViewById(R.id.statusTextView);
        speciesTextView = findViewById(R.id.speciesTextView);
        genderTextView = findViewById(R.id.genderTextView);
        detailProgressBar = findViewById(R.id.detailProgressBar);
        errorLayout = findViewById(R.id.errorLayout);
        infoCardView = findViewById(R.id.infoCardView);

        // Get API service
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Get character ID from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            characterId = extras.getInt("id", -1);
        }

        // Setup refresh button click listener
        findViewById(R.id.reloadIcon).setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                errorLayout.setVisibility(View.GONE);
                detailProgressBar.setVisibility(View.VISIBLE);
                loadCharacterFromApi();
            } else {
                Toast.makeText(DetailActivity.this, "No internet connection available", Toast.LENGTH_SHORT).show();
            }
        });

        // Check network and load data
        if (isNetworkAvailable()) {
            loadCharacterFromApi();
        } else {
            showOfflineUi();
        }
    }

    private void loadCharacterFromApi() {
        // Hide content views while loading
        hideContentViews();

        if (characterId != -1) {
            Call<Character> call = apiService.getCharacterById(characterId);
            call.enqueue(new retrofit2.Callback<Character>() {
                @Override
                public void onResponse(Call<Character> call, Response<Character> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Character character = response.body();
                        displayCharacterDetails(character);
                    } else {
                        showErrorUi();
                    }
                }

                @Override
                public void onFailure(Call<Character> call, Throwable t) {
                    showErrorUi();
                }
            });
        } else {
            showErrorUi();
        }
    }

    private void displayCharacterDetails(Character character) {
        nameTextView.setText(character.getName());
        statusTextView.setText("Status: " + character.getStatus());
        speciesTextView.setText("Species: " + character.getSpecies());
        genderTextView.setText("Gender: " + character.getGender());

        // Load image with Picasso
        Picasso.get().load(character.getImage()).into(characterImageView, new Callback() {
            @Override
            public void onSuccess() {
                showContentViews();
            }

            @Override
            public void onError(Exception e) {
                showContentViews();
                Toast.makeText(DetailActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showContentViews() {
        detailProgressBar.setVisibility(View.GONE);
        characterImageView.setVisibility(View.VISIBLE);
        nameTextView.setVisibility(View.VISIBLE);
        infoCardView.setVisibility(View.VISIBLE);
    }

    private void hideContentViews() {
        characterImageView.setVisibility(View.INVISIBLE);
        nameTextView.setVisibility(View.INVISIBLE);
        infoCardView.setVisibility(View.INVISIBLE);
    }

    private void showOfflineUi() {
        detailProgressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    private void showErrorUi() {
        detailProgressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}