package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.Adapter.FeedProfileAdapter;
import com.example.tp3.Adapter.HighlightAdapter;
import com.example.tp3.Model.Feed;
import com.example.tp3.Model.User;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivProfileImage;
    private TextView tvUsername, tvFullName, tvBio, tvSumPost;
    private RecyclerView rvHighlights, rvFeeds;
    private ImageButton btnBack, btnHome;

    private User user;

    // Launcher untuk AddPostActivity
    private final ActivityResultLauncher<Intent> addPostLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    refreshUserData();
                }
            }
    );

    // Di ProfileActivity, tentukan feedList sebagai variabel publik statik
    public static List<Feed> currentUserFeedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Ambil username dari intent
        String username = getIntent().getStringExtra("USER_USERNAME");
        user = DataFeedSource.getUserByUsername(username);

        // Inisialisasi views
        initViews();

        if (user != null) {
            displayUserData(user);
            setupHighlights(user);
            setupFeeds(user);
        }

        // Tombol Add Post
        findViewById(R.id.nav_add).setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AddPostActivity.class);
            intent.putExtra("USER_USERNAME", user.getUsername());
            addPostLauncher.launch(intent); // Pakai launcher
        });


        // Tombol Back
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // Tombol Home
        btnHome = findViewById(R.id.nav_home);
        btnHome.setOnClickListener(v -> {

            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });


        String currentUsername = getIntent().getStringExtra("USER_USERNAME");
        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            if (!currentUsername.equals("kimjennie")) {
                // Kalau bukan di profile user 0, pindah ke profile user 0
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                intent.putExtra("USER_USERNAME", "kimjennie");
                startActivity(intent);
                finish(); // Optional, kalau mau nutup activity sekarang biar ga numpuk
            } else {
                // Kalau sudah di profile user 0, tinggal refresh
                refreshUserData();
            }
        });

    }

    private void initViews() {
        ivProfileImage = findViewById(R.id.iv_profile_image);
        tvUsername = findViewById(R.id.tv_username);
        tvFullName = findViewById(R.id.tv_full_name);
        tvBio = findViewById(R.id.tvBio);
        tvSumPost = findViewById(R.id.tvPostsCount);
        rvHighlights = findViewById(R.id.rv_highlights);
        rvFeeds = findViewById(R.id.rv_feeds);
    }

    private void displayUserData(User user) {
        ivProfileImage.setImageResource(user.getProfileImage());
        tvUsername.setText(user.getUsername());
        tvFullName.setText(user.getName());
        tvBio.setText(user.getBio());
        tvSumPost.setText(String.valueOf(user.getFeedList().size()));
    }

    private void setupHighlights(User user) {
        HighlightAdapter highlightAdapter = new HighlightAdapter(this, user.getHighlightList());
        rvHighlights.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvHighlights.setAdapter(highlightAdapter);
    }

    private void setupFeeds(User user) {
        currentUserFeedList = user.getFeedList();
        if (currentUserFeedList != null && !currentUserFeedList.isEmpty()) {
            FeedProfileAdapter feedAdapter = new FeedProfileAdapter(this, currentUserFeedList);
            rvFeeds.setLayoutManager(new GridLayoutManager(this, 3)); // 3 kolom
            rvFeeds.setAdapter(feedAdapter);
        } else {
            rvFeeds.setAdapter(null); // clear adapter
            Toast.makeText(this, "Tidak ada feed untuk ditampilkan", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshUserData() {
        // Refresh user object dari DataFeedSource
        user = DataFeedSource.getUserByUsername(user.getUsername());
        if (user != null) {
            displayUserData(user);
            setupFeeds(user);
        }
    }
}
