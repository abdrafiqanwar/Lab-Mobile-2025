package com.example.tp6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ApiService apiService;
    private UserAdapter userAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private final List<User> allUsers = new ArrayList<>();
    private final List<Integer> loadedPages = new ArrayList<>();
    private int currentPage = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private Integer pendingPage = null;

    private final BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                unregisterReceiver(this);
            } catch (IllegalArgumentException ignored) {}

            int pageToLoad = (pendingPage != null) ? pendingPage : currentPage;

            loadData(pageToLoad, false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        userAdapter = new UserAdapter(allUsers, () -> {
            if (!isLastPage && !isLoading) {
                int nextPage = currentPage + 1;
                loadData(nextPage, false);
            }
        });

        recyclerView.setAdapter(userAdapter);

        loadData(currentPage, true);
    }

    private void loadData(int page, boolean isInitialLoad) {
        if (isLoading) return;


        if (loadedPages.contains(page)) {
            isLoading = false;
            return;
        }

        isLoading = true;

        if (isInitialLoad) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }

        apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<UserResponse> call = apiService.getCharacters(page);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                isLoading = false;
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    List<User> userList = response.body().getResults();

                    allUsers.addAll(userList);
                    userAdapter.notifyDataSetChanged();

                    isLastPage = (response.body().getInfo().getNext() == null);
                    userAdapter.setShowLoadMore(!isLastPage);

                    currentPage = page;
                    loadedPages.add(page);

                    pendingPage = null; // reset halaman gagal
                } else {

                    Toast.makeText(MainActivity.this, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                isLoading = false;

                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "Tidak ada jaringan. Menunggu koneksi...", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                    pendingPage = page; // simpan halaman yang gagal dimuat
                    registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Kesalahan tidak diketahui", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
