package com.example.tugaspraktikum3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    // 👉 Ini kita pindah ke atas sebagai field global
    private FeedHomeAdapter feedHomeAdapter;

    private static final int REQUEST_STORAGE_PERMISSION = 100;
    private ArrayList<User> users;
    private ArrayList<User.Feed> allFeeds;

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



        // INISIALISASI VIEW
        CircleImageView myprofile = findViewById(R.id.myprofile);

        RecyclerView recyclerViewFeed = findViewById(R.id.recyclerFeed);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFeed.setHasFixedSize(true);

        // Ambil dummy data dari DataDummy
        users = DataDummy.getDummyUsers();
        allFeeds = new ArrayList<>();

        for (User user : users) {
            for (User.Feed feed : user.getFeeds()) {
                allFeeds.add(feed);
            }
        }

        // Setup RecyclerView
        feedHomeAdapter = new FeedHomeAdapter(allFeeds);
        recyclerViewFeed.setAdapter(feedHomeAdapter);

        // Tambahkan listener untuk My Profile
        User personalUser = users.stream().filter(User::isPersonalProfile).findFirst().orElse(null);

        if (personalUser != null) {
            myprofile.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("USERNAME", personalUser.getUsername());
                startActivity(intent);
            });
        }

        // Periksa izin penyimpanan
        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh data if needed
        if (getIntent().getBooleanExtra("REFRESH_FEED", false)) {
            // Re-fetch all users and rebuild the feed list
            users = DataDummy.getDummyUsers();
            allFeeds.clear();

            for (User user : users) {
                allFeeds.addAll(user.getFeeds());
            }

            // Notify adapter of changes
            if (feedHomeAdapter != null) {
                feedHomeAdapter.notifyDataSetChanged();
                Log.d("MainActivity", "Refreshed feed with " + allFeeds.size() + " items");
            }

            // Remove the flag
            getIntent().removeExtra("REFRESH_FEED");
        }
    }

    private void checkPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION
                );
            }
        }
    }

}
