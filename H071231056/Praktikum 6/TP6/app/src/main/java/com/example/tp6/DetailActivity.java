package com.example.tp6;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tp6.api.ApiClient;
import com.example.tp6.api.DataService;
import com.example.tp6.models.Character;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView avatarDetail;
    private TextView nameDetail, speciesDetail, statusDetail, genderDetail, idDetail;
    private ProgressBar progressBar;
    private Button backButton;
    private ExecutorService executor;
    private Handler handler;
    private DataService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        // Initialize views
        avatarDetail = findViewById(R.id.avatarDetail);
        nameDetail = findViewById(R.id.nameDetail);
        speciesDetail = findViewById(R.id.emailDetail); // Assuming emailDetail is used for species
        statusDetail = findViewById(R.id.statusDetail);
        genderDetail = findViewById(R.id.genderDetail);
        idDetail = findViewById(R.id.idDetail);
        progressBar = findViewById(R.id.progressBar);
        backButton = findViewById(R.id.backButton);

        // Initialize threading components
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        // Initialize API service
        apiService = ApiClient.getClient().create(DataService.class);

        // Setup back button click listener
        backButton.setOnClickListener(v -> onBackPressed());

        // Get character data from intent
        if (getIntent().hasExtra("character_id")) {
            String characterId = getIntent().getStringExtra("character_id");
            loadCharacterDetails(characterId);
        } else {
            Toast.makeText(this, "No character ID found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadCharacterDetails(String id) {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection. Cannot load details.", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        executor.execute(() -> {
            Call<Character> call = apiService.getCharacterById(id);
            call.enqueue(new Callback<Character>() {
                @Override
                public void onResponse(Call<Character> call, Response<Character> response) {
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null) {
                            Character character = response.body();
                            idDetail.setText("ID: " + character.getId());
                            nameDetail.setText(character.getName());
                            speciesDetail.setText("Species: " + character.getSpecies());
                            statusDetail.setText("Status: " + character.getStatus());
                            genderDetail.setText("Gender: " + character.getGender());
                            Glide.with(DetailActivity.this).load(character.getImage()).into(avatarDetail);
                        } else {
                            Toast.makeText(DetailActivity.this, "Failed to load character details", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }

                @Override
                public void onFailure(Call<Character> call, Throwable t) {
                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(DetailActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        finish();
                    });
                }
            });
        });
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown the executor service
        if (executor != null) {
            executor.shutdown();
        }
    }
}