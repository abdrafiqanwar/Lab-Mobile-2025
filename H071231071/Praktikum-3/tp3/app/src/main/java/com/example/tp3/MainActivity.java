package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3.Adapter.FeedHomeAdapter;
import com.example.tp3.Model.Feed;
import com.example.tp3.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvFeed;
    private List<Feed> allFeeds = new ArrayList<>();
    private List<User> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        rvFeed = findViewById(R.id.rvFeed);

        // Ambil data pengguna dan feed dari sumber
        users = DataFeedSource.getUsers();

        // Gabungkan semua feed dari semua user
// Get only one feed per user
        for (User user : users) {
            List<Feed> userFeeds = user.getFeedList();
            if (!userFeeds.isEmpty()) {
                allFeeds.addAll(userFeeds); // Add only the first feed from each user
            }
        }



        // Di MainActivity, dalam onCreate()
        findViewById(R.id.nav_profile).setOnClickListener(v -> {
            // Kirim username user 0 (user yang sedang login)
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("USER_USERNAME", users.get(0).getUsername());
            startActivity(intent);
        });

        // Di MainActivity dalam onCreate()
        findViewById(R.id.nav_add).setOnClickListener(v -> {
            // Buka halaman AddPostActivity
            Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
            intent.putExtra("USER_USERNAME", users.get(0).getUsername());
            startActivity(intent);
        });



        // Di MainActivity, dalam adapter:
        FeedHomeAdapter adapter = new FeedHomeAdapter(this, allFeeds, users, user -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("USER_USERNAME", user.getUsername());  // Kirim username saja
            startActivity(intent);
        });




        rvFeed.setLayoutManager(new LinearLayoutManager(this));
        rvFeed.setAdapter(adapter);
    }
}
