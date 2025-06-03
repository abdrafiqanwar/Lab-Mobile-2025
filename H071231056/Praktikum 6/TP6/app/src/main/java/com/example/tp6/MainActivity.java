package com.example.tp6;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp6.adapter.CharacterAdapter;
import com.example.tp6.api.ApiClient;
import com.example.tp6.api.DataService;
import com.example.tp6.models.ApiResponse;
import com.example.tp6.models.Character;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CharacterAdapter.OnLoadMoreClickListener {
    private RecyclerView recyclerView;
    private CharacterAdapter adapter;
    private ProgressBar progressBar;
    private Button refreshButton;

    private DataService apiService;
    private int currentPage = 1;
    private int totalPages = 1;

    private ExecutorService executorService;
    private Handler mainHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        refreshButton = findViewById(R.id.refreshButton);

        // Setup RefreshButton
        refreshButton.setOnClickListener(v -> {
            currentPage = 1;
            adapter.clearCharacters(); // Clear adapter when refreshing
            loadData();
        });

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CharacterAdapter(this, this);
        recyclerView.setAdapter(adapter);

        // Initialize API service
        apiService = ApiClient.getClient().create(DataService.class);

        // Initialize threading components
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        // Initial load behavior
        if (!isNetworkAvailable(this)) {
            showLoading(true); // Show loading briefly
            mainHandler.postDelayed(() -> {
                showLoading(false);
                showRefreshButton();
                Toast.makeText(MainActivity.this, "Tidak ada koneksi internet.", Toast.LENGTH_LONG).show();
            }, 1500); // Delay for 1.5 seconds to show loading
        } else {
            loadData();
        }
    }

    private void loadData() {
        showLoading(true);
        hideRefreshButton();

        if (!isNetworkAvailable(this)) {
            mainHandler.post(() -> {
                showLoading(false);
                showRefreshButton();
                Toast.makeText(MainActivity.this, "Tidak ada koneksi internet.", Toast.LENGTH_LONG).show();
            });
            return;
        }

        executorService.execute(() -> {
            Call<ApiResponse> call = apiService.getCharacters(currentPage);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    mainHandler.post(() -> {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse apiResponse = response.body();
                            List<Character> characters = apiResponse.getResults();
                            totalPages = apiResponse.getInfo().getPages();

                            if (currentPage == 1) {
                                adapter.clearCharacters();
                            }
                            adapter.addCharacters(characters);

                            boolean hasMorePages = currentPage < totalPages;
                            adapter.setShowLoadMore(hasMorePages);

                            showLoading(false);
                        } else {
                            handleApiError();
                        }
                    });
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    mainHandler.post(() -> {
                        handleApiError();
                        Toast.makeText(MainActivity.this, "Error jaringan: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void handleApiError() {
        showLoading(false);
        showRefreshButton();
        Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
    }

    private void showRefreshButton() {
        refreshButton.setVisibility(View.VISIBLE);
    }

    private void hideRefreshButton() {
        refreshButton.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    @Override
    public void onLoadMoreClick(Context context) {
        if (currentPage < totalPages) {
            if (!isNetworkAvailable(context)){
                Toast.makeText(context, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
            } else {
                currentPage++;
                loadData();
            }
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}