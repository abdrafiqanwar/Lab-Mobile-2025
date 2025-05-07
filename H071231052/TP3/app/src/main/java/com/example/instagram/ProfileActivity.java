package com.example.instagram;

import static com.example.instagram.NumberFormatter.formatNumber;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.adapter.FeedAdapter;
import com.example.instagram.adapter.ProfileAdapter;
import com.example.instagram.model.FeedModel;
import com.example.instagram.model.PhotoModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    TextView tvUsernameProfile, tvUsernameProfile2, bioProfile, namaHighlight, tvFollowersCount, tvPostinganCount, tvFollowingCount;
    CircleImageView fotoProfil, fotoHighlight;
    RecyclerView recyclerView;
    List<FeedModel> allFeeds;
    List<FeedModel> userFeeds;
    ProfileAdapter profileAdapter;

    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {
            onBackPressed();
        });

        tvFollowersCount = findViewById(R.id.tvFollowersCount);
        tvPostinganCount = findViewById(R.id.tvPostinganCount);
        tvFollowingCount = findViewById(R.id.tvFollowingCount);
        fotoHighlight = findViewById(R.id.fotoHighlight);
        namaHighlight = findViewById(R.id.namaHighlight);
        bioProfile = findViewById(R.id.bioProfile);
        tvUsernameProfile = findViewById(R.id.tvUsernameProfile);
        tvUsernameProfile2 = findViewById(R.id.tvUsernameProfile2);
        fotoProfil = findViewById(R.id.fotoProfil);
        recyclerView = findViewById(R.id.profile_feed);

        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));


        String username = getIntent().getStringExtra("username");
        allFeeds = getIntent().getParcelableArrayListExtra("feedList");

        tvUsernameProfile.setText(username);
        tvUsernameProfile2.setText(username);

        userFeeds = new ArrayList<>();
        for (FeedModel feed : allFeeds) {
            if (feed.getUsername().equals(username)) {
                userFeeds.add(feed);
            }
        }

        for (FeedModel feed : allFeeds) {
            if (feed.getUsername().equals(username)) {
                if (feed.getProfileImageUrl() != null) {
                    Glide.with(this)
                            .load(feed.getProfileImageUrl())
                            .into(fotoProfil);
                } else {
                    fotoProfil.setImageResource(feed.getProfileImage());
                }

                if (feed.getHighlightProfileImageUrl() != null) {
                    Glide.with(this)
                            .load(feed.getHighlightProfileImageUrl())
                            .into(fotoHighlight);
                } else {
                    fotoHighlight.setImageResource(feed.getHighlightImage());
                }
                namaHighlight.setText(feed.getHighlightName());
                tvFollowersCount.setText(String.valueOf(formatNumber(feed.getFollowerCount())));
                tvPostinganCount.setText(String.valueOf(formatNumber(feed.getPostCount())));
                tvFollowingCount.setText(String.valueOf(formatNumber(feed.getFollowingCount())));
                break;
            }
        }

        bioProfile = findViewById(R.id.bioProfile);
        if (!userFeeds.isEmpty()) {
            String bio = userFeeds.get(0).getBio();
            bioProfile.setText(bio);
        }

        fotoHighlight.setOnClickListener(v -> {
            if (!userFeeds.isEmpty()) {
                List<PhotoModel> highlightPhotos = userFeeds.get(0).getHighlightPhotos();

                if (highlightPhotos != null && highlightPhotos.size() >= 3) {
                    List<PhotoModel> selectedPhotos = highlightPhotos.subList(0, 7);

                    Intent intent = new Intent(ProfileActivity.this, highlightActivity.class);
                    intent.putExtra("username", userFeeds.get(0).getUsername());

                    if (userFeeds.get(0).getProfileImageUrl() != null) {
                        intent.putExtra("profileImage", userFeeds.get(0).getProfileImageUrl());
                    } else {
                        intent.putExtra("profileImage", userFeeds.get(0).getProfileImage());
                    }
                    intent.putParcelableArrayListExtra("photos", new ArrayList<>(selectedPhotos));
                    startActivity(intent);
                }
            }
        });

        profileAdapter = new ProfileAdapter(this, userFeeds);
        recyclerView.setAdapter(profileAdapter);
    }

}