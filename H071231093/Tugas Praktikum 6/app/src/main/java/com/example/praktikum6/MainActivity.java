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
    private RecyclerView rvCharacters;
    private CharacterAdapter adapter;
    private ProgressBar progressBar;
    private Button loadMoreButton;
    private LinearLayout noInternetLayout;
    private Button retryButton;
    private int currentPage = 1;
    private boolean isLoading = false;
    private List<Characters> allCharacters = new ArrayList<>();
    private boolean isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCharacters = findViewById(R.id.rv_characters);
        progressBar = findViewById(R.id.progressBar);
        loadMoreButton = findViewById(R.id.loadMoreButton);
        noInternetLayout = findViewById(R.id.noInternetLayout);
        retryButton = findViewById(R.id.retryButton);

        adapter = new CharacterAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCharacters.setLayoutManager(layoutManager);
        rvCharacters.setAdapter(adapter);

        loadMoreButton.setOnClickListener(v -> {
            if (!isLoading && !isLastPage) {
                currentPage++;
                getCharacters(currentPage);
                loadMoreButton.setVisibility(View.GONE);
            }
        });

        retryButton.setOnClickListener(v -> {
            hideErrorView();
            getCharacters(currentPage);
        });

        rvCharacters.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0 && totalItemCount > 0) {
                        loadMoreButton.setVisibility(View.VISIBLE);
                    } else {
                        loadMoreButton.setVisibility(View.GONE);
                    }
                }
            }
        });

        getCharacters(currentPage);
    }

    private void showErrorView() {
        rvCharacters.setVisibility(View.GONE);
        loadMoreButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
    }

    private void hideErrorView() {
        noInternetLayout.setVisibility(View.GONE);
        rvCharacters.setVisibility(View.VISIBLE);
    }

    private void getCharacters(int page) {
        showLoading(true);
        isLoading = true;
        loadMoreButton.setVisibility(View.GONE);

        ApiService apiService = ApiConfig.getApiService();
        Call<CharacterResponse> call = apiService.getCharacters(page);

        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                showLoading(false);
                isLoading = false;

                if (response.isSuccessful() && response.body() != null) {
                    hideErrorView();
                    List<Characters> newCharacters = response.body().getResults();

                    allCharacters.addAll(newCharacters);

                    adapter.setCharactersList(allCharacters);

                    if (response.body().getInfo() != null &&
                            response.body().getInfo().getNext() == null) {
                        isLastPage = true;
                        loadMoreButton.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    showErrorView();
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                showLoading(false);
                isLoading = false;
                showErrorView();
            }
        });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}