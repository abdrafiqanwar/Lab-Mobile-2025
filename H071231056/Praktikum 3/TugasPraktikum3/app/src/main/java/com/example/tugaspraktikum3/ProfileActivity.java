package com.example.tugaspraktikum3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private FeedHomeAdapter feedHomeAdapter;
    private ArrayList<User> users;
    private ArrayList<User.Feed> allFeeds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        try {
            // Initialize views with error checking
            ImageView imageProfile = findViewById(R.id.imageProfile);
            TextView textUsernameProfile = findViewById(R.id.textUsernameProfile);
            TextView textBio = findViewById(R.id.textBio);
            RecyclerView recyclerHighlight = findViewById(R.id.recyclerHighlight);
            RecyclerView recyclerGridFeed = findViewById(R.id.recyclerGridFeed);
            ImageView fabAddProfilePost = findViewById(R.id.fabAddProfilePost);


            // Check if views exist
            if (imageProfile == null) {
                Log.e(TAG, "imageProfile view not found");
                Toast.makeText(this, "Layout error: imageProfile not found", Toast.LENGTH_SHORT).show();
            }
            if (textUsernameProfile == null) {
                Log.e(TAG, "textUsernameProfile view not found");
            }
            if (textBio == null) {
                Log.e(TAG, "textBio view not found");
            }
            if (recyclerHighlight == null) {
                Log.e(TAG, "recyclerHighlight view not found");
            }
            if (recyclerGridFeed == null) {
                Log.e(TAG, "recyclerGridFeed view not found");
                Toast.makeText(this, "Layout error: recyclerGridFeed not found", Toast.LENGTH_SHORT).show();
            }

            // Get username from intent
            String username = getIntent().getStringExtra("USERNAME");
            if (username == null || username.isEmpty()) {
                Toast.makeText(this, "Username not provided", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            // Find user
            User userProfile = DataDummy.getDummyUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);

            // CEK APAKAH INI PERSONAL PROFILE ATAU BUKAN
            if (fabAddProfilePost != null) {
                if (userProfile.isPersonalProfile()) {
                    fabAddProfilePost.setVisibility(View.VISIBLE);
                    fabAddProfilePost.setOnClickListener(v -> {
                        Intent intent = new Intent(ProfileActivity.this, PostActivity.class);
                        intent.putExtra("USERNAME", userProfile.getUsername());
                        startActivity(intent);
                    });
                } else {
                    fabAddProfilePost.setVisibility(View.GONE);

                }
            }

            if (userProfile == null) {
                Toast.makeText(this, "User not found: " + username, Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            // Set user data to views if they exist
            if (imageProfile != null) {
                Picasso.get()
                        .load(userProfile.getProfileImageUrl())
                        .placeholder(R.drawable.bbq_sauce)
                        .error(R.drawable.bbq_sauce)
                        .into(imageProfile);
            }

            if (textUsernameProfile != null) {
                textUsernameProfile.setText(userProfile.getUsername());
            }

            if (textBio != null) {
                textBio.setText(userProfile.getBio());
            }

            // Setup highlights RecyclerView
            if (recyclerHighlight != null) {
                recyclerHighlight.setLayoutManager(
                        new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                HighlightAdapter highlightAdapter = new HighlightAdapter(userProfile.getHighlights());
                recyclerHighlight.setAdapter(highlightAdapter);
            }

            // Setup feed grid RecyclerView
            if (recyclerGridFeed != null) {
                recyclerGridFeed.setLayoutManager(new GridLayoutManager(this, 3));
                FeedGridAdapter feedGridAdapter = new FeedGridAdapter(userProfile.getFeeds());
                recyclerGridFeed.setAdapter(feedGridAdapter);
                feedGridAdapter.notifyDataSetChanged();  // Ini wajib biar RecyclerView tahu data berubah
                Log.d(TAG, "Grid feed adapter set with " + userProfile.getFeeds().size() + " items");
            }

        } catch (Exception e) {
            Log.e(TAG, "Error in ProfileActivity", e);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        String username = getIntent().getStringExtra("USERNAME");
//        if (username != null) {
//            User userProfile = DataDummy.getDummyUsers().stream()
//                    .filter(user -> user.getUsername().equals(username))
//                    .findFirst()
//                    .orElse(null);
//
//            if (userProfile != null && findViewById(R.id.recyclerGridFeed) != null) {
//                RecyclerView recyclerGridFeed = findViewById(R.id.recyclerGridFeed);
//
//                // Set adapter dengan data yang baru
//                FeedGridAdapter feedGridAdapter = new FeedGridAdapter(userProfile.getFeeds());
//                recyclerGridFeed.setAdapter(feedGridAdapter);
//
//                // Segarkan adapter
//                feedGridAdapter.notifyDataSetChanged();  // Ini wajib biar RecyclerView tahu data berubah
//
//                // Log untuk debugging
//                Log.d(TAG, "Refreshed grid feed adapter with " + userProfile.getFeeds().size() + " items");
//            }
//        }
//    }


}