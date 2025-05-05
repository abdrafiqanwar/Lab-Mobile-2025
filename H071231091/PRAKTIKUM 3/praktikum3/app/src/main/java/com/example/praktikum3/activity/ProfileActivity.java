package com.example.praktikum3.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum3.jclass.Feed;
import com.example.praktikum3.data.FeedData;
import com.example.praktikum3.R;
import com.example.praktikum3.jclass.Story;
import com.example.praktikum3.data.StoryData;
import com.example.praktikum3.adapter.StoryHighlightAdapter;
import com.example.praktikum3.adapter.UserFeedAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView rvHighlight, rvProfileFeed;
    private ArrayList<Feed> feedList;
    private ArrayList<Story> storyList;
    private UserFeedAdapter userFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Mengatur layout untuk activity ini

        // Inisialisasi RecyclerView
        rvHighlight = findViewById(R.id.rv_highlight);
        rvProfileFeed = findViewById(R.id.rv_profile_feed);

        // Mengambil data
        feedList = FeedData.getFeedList();
        storyList = StoryData.getStoryList();

        // Menyiapkan RecyclerView untuk highlight dan feed
        setupHighlightRecyclerView();
        setupProfileFeedRecyclerView();


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_post) {
                startActivity(new Intent(ProfileActivity.this, PostActivity.class));
                return true;
            }
            return false;
        });
    }

    // Menyiapkan RecyclerView untuk highlight story
    private void setupHighlightRecyclerView() {
        StoryHighlightAdapter highlightAdapter = new StoryHighlightAdapter(storyList, story -> {
            Intent intent = new Intent(ProfileActivity.this, StoryDetailActivity.class);
            intent.putExtra("story_title", story.getTitle()); // Mengirim judul story
            intent.putExtra("story_image", story.getImageRes()); // Mengirim gambar story
            startActivity(intent);
        });

        // Mengatur layout manager horizontal untuk highlight
        rvHighlight.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvHighlight.setAdapter(highlightAdapter); // Mengatur adapter untuk RecyclerView
    }

    // Menyiapkan RecyclerView untuk feed profil
    private void setupProfileFeedRecyclerView() {
        userFeedAdapter = new UserFeedAdapter(feedList, feed -> {
            // Intent untuk membuka detail postingan
            Intent intent = new Intent(ProfileActivity.this, PostDetailActivity.class);
            intent.putExtra("feed_caption", feed.getCaption()); // Mengirim caption postingan
            if (feed.getImageRes() != -1) {
                intent.putExtra("feed_image", feed.getImageRes()); // Mengirim gambar dari resource
            } else {
                intent.putExtra("feed_image_uri", feed.getImageUri().toString()); // Mengirim URI gambar
            }
            intent.putExtra("username", "Fdznalw"); // Mengirim username
            intent.putExtra("user_profile_image", R.drawable.profile); // Mengirim gambar profil
            startActivity(intent);
        });

        // Mengatur layout manager grid untuk feed
        rvProfileFeed.setLayoutManager(new GridLayoutManager(this, 3));
        rvProfileFeed.setAdapter(userFeedAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userFeedAdapter.notifyDataSetChanged(); // Memperbarui data feed saat activity dilanjutkan
    }
}