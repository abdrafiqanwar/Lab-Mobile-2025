package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.Adapter.DetailFeedAdapter;
import com.example.tp3.Model.Feed;
import com.example.tp3.Model.User;

import java.util.ArrayList;
import java.util.List;

public class FeedDetailActivity extends AppCompatActivity {

    private RecyclerView rvFeedDetail;
    private List<Feed> feedList;
    private DetailFeedAdapter detailFeedAdapter;
    private ImageButton btnHome, btnBack;
    private List<User> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        rvFeedDetail = findViewById(R.id.rv_feed_detail);
        btnHome = findViewById(R.id.nav_home);
        btnBack = findViewById(R.id.btnBack2);

        feedList = ProfileActivity.currentUserFeedList;

        if (feedList == null || feedList.isEmpty()) {
            Toast.makeText(this, "Tidak ada feed untuk ditampilkan", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        int selectedPosition = getIntent().getIntExtra("SELECTED_POSITION", -1);
        if (selectedPosition != -1 && selectedPosition < feedList.size()) {
            Feed selectedFeed = feedList.get(selectedPosition);
            // Lakukan operasi yang sesuai dengan selectedFeed
        } else {
            Toast.makeText(this, "Feed tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();  // Kembali jika data tidak valid
        }


        rvFeedDetail.setLayoutManager(new LinearLayoutManager(this));
        detailFeedAdapter = new DetailFeedAdapter(this, feedList);
        rvFeedDetail.setAdapter(detailFeedAdapter);
        rvFeedDetail.scrollToPosition(selectedPosition);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(FeedDetailActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        findViewById(R.id.nav_add).setOnClickListener(v -> {
            // Buka halaman AddPostActivity
            Intent intent = new Intent(FeedDetailActivity.this, AddPostActivity.class);
            intent.putExtra("USER_USERNAME", "kimjennie");
            startActivity(intent);
        });

        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            // Kirim username user 0 (user yang sedang login)
            Intent intent = new Intent(FeedDetailActivity.this, ProfileActivity.class);
            intent.putExtra("USER_USERNAME", "kimjennie");
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> finish());
    }
}
