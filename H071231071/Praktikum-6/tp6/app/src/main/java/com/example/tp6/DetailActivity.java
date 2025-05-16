//package com.example.tp6;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.squareup.picasso.Picasso;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//public class DetailActivity extends AppCompatActivity {
//
//    private ImageView ivImage;
//    private TextView tvName, tvStatus, tvSpecies, tvGender;
//    private ProgressBar progressBar;
//
//    private final Executor executor = Executors.newSingleThreadExecutor();
//    private final Handler handler = new Handler(Looper.getMainLooper());
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_detail);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        ivImage = findViewById(R.id.ivImage);
//        tvName = findViewById(R.id.tvName);
//        tvStatus = findViewById(R.id.tvStatus);
//        tvSpecies = findViewById(R.id.tvSpecies);
//        tvGender = findViewById(R.id.tvGender);
//        progressBar = findViewById(R.id.progressBar);
//        CardView card = findViewById(R.id.cardView); // PERBAIKAN DI SINI
//
//
//        progressBar.setVisibility(View.VISIBLE);
//        // Sembunyikan tampilan dulu
//        card.setVisibility(View.GONE);
//        ivImage.setVisibility(View.GONE);
//        tvName.setVisibility(View.GONE);
//        tvStatus.setVisibility(View.GONE);
//        tvSpecies.setVisibility(View.GONE);
//        tvGender.setVisibility(View.GONE);
//
//        // Ambil data dari intent di background thread
//        executor.execute(() -> {
//
//            try {
//                Thread.sleep(2000); // Simulasi loading (misalnya ambil data)
//
//                User user = getIntent().getParcelableExtra("user");
//
//                // Kembali ke main thread untuk update UI
//                handler.post(() -> {
//                    progressBar.setVisibility(View.GONE);
//
//                    if (user != null) {
//                        tvName.setText(user.getName());
//                        tvStatus.setText(user.getStatus());
//                        tvSpecies.setText(user.getSpecies());
//                        tvGender.setText(user.getGender());
//
//                        Picasso.get()
//                                .load(user.getImage())
//                                .placeholder(R.drawable.ic_launcher_background)
//                                .error(R.drawable.ic_launcher_background)
//                                .into(ivImage);
//                    }
//
//                    card.setVisibility(View.VISIBLE);
//                    ivImage.setVisibility(View.VISIBLE);
//                    tvName.setVisibility(View.VISIBLE);
//                    tvStatus.setVisibility(View.VISIBLE);
//                    tvSpecies.setVisibility(View.VISIBLE);
//                    tvGender.setVisibility(View.VISIBLE);
//                });
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//}

package com.example.tp6;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivImage;
    private TextView tvName, tvStatus, tvSpecies, tvGender, tvError;
    private ProgressBar progressBar;
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ivImage = findViewById(R.id.ivImage);
        tvName = findViewById(R.id.tvName);
        tvStatus = findViewById(R.id.tvStatus);
        tvSpecies = findViewById(R.id.tvSpecies);
        tvGender = findViewById(R.id.tvGender);
        progressBar = findViewById(R.id.progressBar);
        CardView card = findViewById(R.id.cardView);
        tvError = findViewById(R.id.tvError);
        btnRefresh = findViewById(R.id.btnRefresh);

        progressBar.setVisibility(View.VISIBLE);
        card.setVisibility(View.GONE);
        ivImage.setVisibility(View.GONE);
        tvName.setVisibility(View.GONE);
        tvStatus.setVisibility(View.GONE);
        tvSpecies.setVisibility(View.GONE);
        tvGender.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.GONE);

        // Ambil ID user dari Intent
        int userId = getIntent().getIntExtra("user_id", -1); // Pastikan mengirim ID dari Activity sebelumnya

        if (userId != -1) {
            loadUserData(userId);
        }

        // Set listener untuk tombol refresh
        btnRefresh.setOnClickListener(v -> {
            tvError.setVisibility(View.GONE); // Sembunyikan pesan error
            progressBar.setVisibility(View.VISIBLE);
            loadUserData(userId); // Coba lagi untuk load data
        });
    }

    private void loadUserData(int userId) {
        // Ambil data karakter berdasarkan ID
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<User> call = apiService.getUserDetail(userId);

        // Melakukan request API di background thread
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();

                    // Update UI dengan data yang didapat
                    tvName.setText(user.getName());
                    tvStatus.setText(user.getStatus());
                    tvSpecies.setText(user.getSpecies());
                    tvGender.setText(user.getGender());

                    Picasso.get()
                            .load(user.getImage())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(ivImage);


                    // Tambahkan delay agar ProgressBar terlihat
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        progressBar.setVisibility(View.GONE);
                        CardView card = findViewById(R.id.cardView);
                        card.setVisibility(View.VISIBLE);
                        ivImage.setVisibility(View.VISIBLE);
                        tvName.setVisibility(View.VISIBLE);
                        tvStatus.setVisibility(View.VISIBLE);
                        tvSpecies.setVisibility(View.VISIBLE);
                        tvGender.setVisibility(View.VISIBLE);
                        btnRefresh.setVisibility(View.GONE);
                    }, 2000); // 1 detik delay

                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showError();
            }
        });
    }

    private void showError() {
        tvError.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.VISIBLE); // Tampilkan tombol refresh jika terjadi error
    }
}
